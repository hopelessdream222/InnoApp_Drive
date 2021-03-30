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
            case "choisirRecette":
                choisirRecette(request,response);
                break;
            case "rechercherCate":
                rechercherCate(request,response);
                break;
            case "afficherNbPanier":
                afficherNbPanier(request,response);
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
            for (Produit produit : lProduits){
               Float economie = miage.dao.TestHibernate.calculerEconomiePromotionClientUnProd(produit.getIdP());
  
                String prixPromo = "nonPrixPromo";
                String infoPromo = "nonpromotion";
                if(economie != 0){
                    
                    System.out.println("bu deng yu 0");
                    Float prixPromoLong = produit.getPrixUnitaireP()-economie;
                    DecimalFormat df= new  DecimalFormat( "0.00" ); 

                    prixPromo = df.format(prixPromoLong);
                    infoPromo = miage.dao.TestHibernate.chercherProduitPromotion(produit.getIdP());
                }
                
                out.println("<src>image/" + produit.getIdP() +".jpg</src><idProd>"+ produit.getIdP() +
                        "</idProd><libProd>"+produit.getLibelleP()+"</libProd>"+
                        "<formatProd>"+produit.getFormatP()+"</formatProd>"+
                        "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                        "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>"+ 
                        "<prixPromo>"+prixPromo+"</prixPromo>"+
                        "<promotionProd>"+infoPromo+"</promotionProd>");
            }
            
            List<Rayon> lRayons = miage.dao.TestHibernate.obtenirRayons();  
            
            for (Rayon rayon : lRayons){
                out.println("<rayonProd>" + rayon.getLibelleRay() +"</rayonProd><rayonId>" + rayon.getIdRay() +"</rayonId>");                
            }
            /*----- RÈcupÈration le session de client -----*/
            HttpSession s = request.getSession();
            if(s.getAttribute("client")!=null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
                //System.out.println("****************"+client.getNomCli());
            }else{
                //System.out.println("-------");
                //System.out.println("****************"+client.getNomCli());
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
 
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Produit> lProduits = miage.dao.TestHibernate.obtenirProduits(idCate);
                    
            for (Produit produit : lProduits){
                
                Float economie = miage.dao.TestHibernate.calculerEconomiePromotionClientUnProd(produit.getIdP());
  
                String prixPromo = "nonPrixPromo";
                String infoPromo = "nonpromotion";
                if(economie != 0){
                    
                    System.out.println("bu deng yu 0");
                    Float prixPromoLong = produit.getPrixUnitaireP()-economie;
                    DecimalFormat df= new  DecimalFormat( "0.00" ); 

                    prixPromo = df.format(prixPromoLong);
                    infoPromo = miage.dao.TestHibernate.chercherProduitPromotion(produit.getIdP());
                }
                
                out.println("<src>image/" + produit.getIdP() +".jpg</src><idProd>"+ produit.getIdP() +
                        "</idProd><libProd>"+produit.getLibelleP()+"</libProd>"+
                        "<formatProd>"+produit.getFormatP()+"</formatProd>"+
                        "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                        "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>"+ 
                        "<prixPromo>"+prixPromo+"</prixPromo>"+
                        "<promotionProd>"+infoPromo+"</promotionProd>");
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
                   Float economie = miage.dao.TestHibernate.calculerEconomiePromotionClientUnProd(produit.getIdP());
  
                    String prixPromo = "nonPrixPromo";
                    String infoPromo = "nonpromotion";
                    if(economie != 0){

                        System.out.println("bu deng yu 0");
                        Float prixPromoLong = produit.getPrixUnitaireP()-economie;
                        DecimalFormat df= new  DecimalFormat( "0.00" ); 

                        prixPromo = df.format(prixPromoLong);
                        infoPromo = miage.dao.TestHibernate.chercherProduitPromotion(produit.getIdP());
                    }
                
                    out.println("<src>image/" + produit.getIdP() +".jpg</src><idProd>"+ produit.getIdP() +
                            "</idProd><libProd>"+produit.getLibelleP()+"</libProd>"+
                            "<formatProd>"+produit.getFormatP()+"</formatProd>"+
                            "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                            "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>"+ 
                            "<prixPromo>"+prixPromo+"</prixPromo>"+
                            "<promotionProd>"+infoPromo+"</promotionProd>");
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
    
    protected void afficherNbPanier(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
                
                int quantitePanier = miage.dao.TestHibernate.chercherQuantitePanierClient(client.getIdCli());
                out.println("<client>"+client.getEmailCli()+"</client><quantitePanier>"+quantitePanier+"</quantitePanier>");
                System.out.println("****************"+quantitePanier);

                
            }else{
                //System.out.println("-------");
                //System.out.println("****************"+client.getNomCli());
                out.println("<client>horsConnection</client>");
            }
            
       
            
            out.println("</liste_produit>");
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
            out.println("<src>image/" + produit.getIdP() +".jpg</src>"+
                        "<idProd>"+ produit.getIdP() +"</idProd>"+
                        "<libProd>"+produit.getLibelleP()+"</libProd>"+
                        "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                        "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>");
            //composition
            System.out.println("composition:"+produit.getCompositionP());
            if(produit.getCompositionP() == null){
                out.println("<compositionProd>noncomposition</compositionProd>");
                System.out.println("compo null");
            }else{
                out.println("<compositionProd>"+produit.getCompositionP()+"</compositionProd>");
            }
            //taille
            System.out.println("taille:"+produit.getTailleReferenceP());
            if(produit.getTailleReferenceP() == null){
                out.println("<tailleProd>nontaille</tailleProd>");
            }else{
                out.println("<tailleProd>"+produit.getTailleReferenceP()+"</tailleProd>");
            }
            //promotion
            //System.out.println("promo:"+produit.getProm().toString());
            Float economie = miage.dao.TestHibernate.calculerEconomiePromotionClientUnProd(produit.getIdP());
            Float prixPromoLong = produit.getPrixUnitaireP()-economie;
            DecimalFormat df= new  DecimalFormat( "0.00" ); 

            String prixPromo = df.format(prixPromoLong);
            if(produit.getProm() == null){
                out.println("<promotionProd>nonpromotion</promotionProd>");
            }else{
                out.println("<promotionProd>"+miage.dao.TestHibernate.chercherProduitPromotion(idProd)+"</promotionProd>");
            }
            out.println("<prixPromo>"+prixPromo+"</prixPromo>");

            //Conditionnement
            System.out.println("cond:"+produit.getConditionnementP());
            if(produit.getConditionnementP() == null){
                out.println("<condProd>noncoditionnement</condProd>");
            }else{
                out.println("<condProd>"+produit.getConditionnementP()+"</condProd>");
            }
            //format
            if(produit.getFormatP() == null){
                out.println("<formatProd>nonformat</formatProd>");
            }else{
                out.println("<formatProd>"+produit.getFormatP()+"</formatProd>");
            }
            //label
            System.out.println("lst size"+labels.size());
            if(labels.size() == 0){
                out.println("<srcLabel>nonlabel</srcLabel>"); 
                System.out.println("mei you label");
            }else{
                for (String label : labels){
                    System.out.println("lab "+label);
                    out.println("<srcLabel>image/labelscore/" + label +".jpg</srcLabel>");    
                    System.out.println("<srcLabel>image/labelscore/" + label +".jpg</srcLabel>");
                }
            }
            //NutriScore
            if(produit.getNutriScoreP().isEmpty()){
                out.println("<srcNutriScore>nonNS</srcNutriScore>");
            }else{
                out.println("<srcNutriScore>image/labelscore/" + produit.getNutriScoreP() +".jpg</srcNutriScore>");
            }
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
