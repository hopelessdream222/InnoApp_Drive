/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;
import miage.dao.TestHibernate;
import miage.metier.Categorie;
import miage.metier.Client;
import miage.metier.Produit;
import miage.metier.Rayon;
import miage.metier.Recette;

/**
 *
 * @author ymfig
 */
public class ServletAccueil extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        switch(request.getParameter("method")){
            case "afficherAccueil":
                afficherAccueil(request, response);
                break;
            case "rechercherProdParCate":
                rechercherProdParCate(request, response);
                break;
            case "afficherProdParRecherche":
                afficherProdParRecherche(request, response);
                break;
            case "ChoisirRecette":
                choisirRecette(request,response);
                break;
            case "rechercherCate":
                rechercherCate(request,response);
                break;
            case "ajouterPanier":
                ajouterPanier(request,response);
                break;
            case "plusDetail":
                plusDetail(request,response);
                break;
                    
        }
        
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected String creerModuleProduit(Produit produit){
        String infoProd = "";
        //id, reporatoire de l'image,libelle, format, prix unitaire, prix kg
        infoProd = "<idProd>"+produit.getIdP()+"</idProd>"+
                "<libProd>"+produit.getLibelleP()+"</libProd>"+
                "<src>image/produits/" + produit.getIdP() +".jpg</src>"+
                "<formatProd>"+produit.getFormatP()+"</formatProd>"+
                "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>";
        
        //information de promotion et prix aprËs la promo 
        Float economie = miage.dao.TestHibernate.calculerEconomiePromotionClientUnProd(produit.getIdP());
        System.out.println(produit.getIdP()+"- economie"+economie);
        String prixPromo = "nonPrixPromo";
        String infoPromo = "nonpromotion";
        //si un produit a une promotion
        if(economie != 0){
            Float prixPromoLong = produit.getPrixUnitaireP()-economie;
            DecimalFormat df= new  DecimalFormat( "0.00" ); 

            prixPromo = df.format(prixPromoLong);
            infoPromo = miage.dao.TestHibernate.chercherProduitPromotion(produit.getIdP());
        }
        infoProd = infoProd + "<prixPromo>"+prixPromo+"</prixPromo>"+
                            "<promotionProd>"+infoPromo+"</promotionProd>";
        
        //nutriScore d'un produit
       if(!produit.getNutriScoreP().isEmpty()){
            infoProd = infoProd + "<srcNutriScore>image/labelscore/" + produit.getNutriScoreP() +".jpg</srcNutriScore>";
        }else{
           infoProd = infoProd + "<srcNutriScore>nonNS</srcNutriScore>";
        }
        
        //composition, taille de rÈfÈrence et conditionnement
        String compo = "noncomposition";
        String tailleRef = "nontaille";
        String cond = "noncoditionnement";
        
        if(produit.getCompositionP() != null){
            compo = produit.getCompositionP();
        }
        if(produit.getTailleReferenceP() != null){
            tailleRef = produit.getTailleReferenceP();
        }
        if(produit.getConditionnementP() != null){
            cond = produit.getConditionnementP();
        }
        infoProd = infoProd +"<compositionProd>"+compo+"</compositionProd>"+
                              "<tailleProd>"+tailleRef+"</tailleProd>"+
                              "<condProd>"+cond+"</condProd>";        
        
        //labels d'un produit
        List<String> labels = miage.dao.TestHibernate.afficherLabels(produit.getLabelP());
        System.out.println("label:"+labels);
        if(labels.size() != 0){
            infoProd = infoProd + "<label>";
            for (String srcLabel : labels){
                if(!srcLabel.isEmpty()){ 
                    infoProd = infoProd + "<srcLabel>image/labelscore/" + srcLabel +".jpg</srcLabel>";    
                }
            }
            infoProd = infoProd + "</label>";
        }else{
            infoProd = infoProd + "<label><srcLabel>nonlabel</srcLabel></label>";
        }           
        
        return infoProd;
    }
    protected void afficherAccueil(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
       /*----- Type de la rÈponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Produit> lProduits = miage.dao.TestHibernate.chercherPromsProduits();
            System.out.println("lstp:"+lProduits);
            String prods = "";
            for (Produit produit : lProduits){
                out.println(creerModuleProduit(produit));
                // prods = prods +creerModuleProduit(produit);
               
            }
            //out.println(prods);
            List<Rayon> lRayons = miage.dao.TestHibernate.obtenirRayons();  
            
            for (Rayon rayon : lRayons){
                out.println("<rayonProd>" + rayon.getLibelleRay() +"</rayonProd><rayonId>" + rayon.getIdRay() +"</rayonId>");                
            }
            /*----- RÈcupÈration le session de client -----*/
            HttpSession s = request.getSession();
            if(s.getAttribute("client")!=null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
            }else{
                out.println("<client>horsConnection</client>");
            }
            
            List<Recette> lRecettes = miage.dao.TestHibernate.obtenirRecettes();  
            
            for (Recette recette : lRecettes){
                out.println("<recetteId>" + recette.getIdRect() +"</recetteId><recetteNom>" + recette.getLibelleRect() +"</recetteNom><recetteSrc>image/recettes/" + recette.getIdRect() +".jpg</recetteSrc>");                
                System.out.println("recette"+recette.getIdRect());
            }
           
            out.println("</liste_produit>");
        }
    }
    
    protected void rechercherProdParCate(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int idCate = Integer.parseInt(request.getParameter("cateId"));
        System.out.println("cate id:"+idCate);
        /*----- Type de la rÈponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
            
            /*----- RÈcupÈration le session de client -----*/
            HttpSession s = request.getSession();
            if(s.getAttribute("client")!=null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
            }else{
                out.println("<client>horsConnection</client>");
            }
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Produit> lProduits = miage.dao.TestHibernate.obtenirProduits(idCate);
                    
            for (Produit produit : lProduits){
                out.println(creerModuleProduit(produit));
            }
                   
            out.println("</liste_produit>");
        }
    }
   
    protected void afficherProdParRecherche(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String nomProd = request.getParameter("nomProd");

        //nomProd fangjinqu
        List<Produit> lProduits = miage.dao.TestHibernate.searchProduits(nomProd);

        if(lProduits.size()==0){
            /*----- Type de la rÈponse -----*/
            response.setContentType("application/xml;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()){
                /*----- Ecriture de la page XML -----*/
                //Si on trouve pas de client, on fait une response de "echec"
                out.println("<?xml version=\"1.0\"?>");
                out.println("<responseRecherche>");
                out.println("<res>echec</res>");
                
                System.out.println("---echec---");
                out.println("</responseRecherche>");
            }
        }else{
            System.out.println("---reussi---");
            
            /*----- Type de la rÈponse -----*/
            response.setContentType("application/xml;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()){
                /*----- Ecriture de la page XML -----*/
                //Si on trouve pas de client, on fait une response de "echec"
                out.println("<?xml version=\"1.0\"?>");
                out.println("<responseRecherche><res>reussi</res>");
                for (Produit produit : lProduits){
                    out.println(creerModuleProduit(produit));
                }
                out.println("</responseRecherche>");
                
            }
        }
    }
    
    protected void choisirRecette(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int idRecette = Integer.parseInt(request.getParameter("idRecette"));
        System.out.println("--idre --"+idRecette);
        HttpSession s = request.getSession();
        s.setAttribute("recette", idRecette);
    }
    
    protected void rechercherCate(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int rayon = Integer.parseInt(request.getParameter("rayon"));
        //nomProd fangjinqu
        List<Categorie> lCategories = miage.dao.TestHibernate.obtenirCategories(rayon);

        /*----- Type de la rÈponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            //Si on trouve pas de client, on fait une response de "echec"
            out.println("<?xml version=\"1.0\"?>");
            out.println("<responseRecherche><res>reussi</res>");
            for (Categorie categorie : lCategories){
                out.println("<categorie>" + categorie.getLibelleCat() +"</categorie><categorieId>" + categorie.getIdCat() +"</categorieId>");                
            }
            out.println("</responseRecherche>");

        }
    }
    
    protected void ajouterPanier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la r√ponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //try (PrintWriter out = response.getWriter()){
        /*----- R√cup√ration des param√®tres -----*/
        int idP = Integer.parseInt(request.getParameter("idP"));
        int qte = Integer.parseInt(request.getParameter("qte"));
        System.out.println(qte+"‡‡‡‡‡‡‡‡‡‡‡‡‡");
        /*----- R√cup√ration le session de client -----*/
        HttpSession s = request.getSession();
        if (s.getAttribute("client") != null) {
            Client client = (Client) s.getAttribute("client");
            TestHibernate.insertProduitPanier(client.getIdCli(), idP, qte);
            //out.println("<client>"+client.getEmailCli()+"</client>");
            //System.out.println("****************"+client.getNomCli());
        } else {
            //System.out.println("-------");
            //System.out.println("****************"+client.getNomCli());
        }

    }
    
    protected void plusDetail(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        int idProd = Integer.parseInt(request.getParameter("idProd"));
        //nomProd fangjinqu
        Produit produit = miage.dao.TestHibernate.loadProduit(idProd);
        //System.out.println(lProduits.get(0).getLibelleP());
        /*----- Type de la rÈponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<String> labels = miage.dao.TestHibernate.afficherLabels(produit.getLabelP());
        
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            //Si on trouve pas de client, on fait une response de "echec"
            out.println("<?xml version=\"1.0\"?>");
            out.println("<responseRecherche>");
            out.println(creerModuleProduit(produit));
            out.println("</responseRecherche>");                 
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
