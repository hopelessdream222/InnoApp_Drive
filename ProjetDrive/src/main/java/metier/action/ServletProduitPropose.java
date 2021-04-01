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
import miage.dao.TestHibernate;
import miage.metier.Client;
import miage.metier.Produit;

/**
 *
 * @author ymfig
 */
public class ServletProduitPropose extends HttpServlet {


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
            case "afficherProduits":
                System.out.println("jin case le");
                afficherProduits(request, response);
                break;
            case "ajouterProduit":
                //System.out.println("jin case le");
                ajouterProduit(request, response);
                break;
            }
        
    }
    
    protected void afficherProduits(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession s1 = request.getSession();
        int idLc = (Integer)s1.getAttribute("Liste");
        Client cli = (Client)s1.getAttribute("client");
        int idCli = cli.getIdCli();
        int idIng = (Integer)s1.getAttribute("Ingredient");
        System.out.println("500"+idLc); 
        System.out.println("500"+idCli); 
        System.out.println("500"+idIng); 
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            Client client = (Client)s.getAttribute("client");
            out.println("<client>"+client.getEmailCli()+"</client>");
            
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            //Remplacer------------------------------------------------------------------
            List<Produit> lProduits = miage.dao.TestHibernate.chercherProduitProposePostit(idCli,idLc,idIng);
            System.out.println("500"+lProduits.get(0));        
            for (Produit produit : lProduits){
                out.println(creerModuleProduit(produit));
                
            }
                   
            out.println("</liste_produit>");
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
        
        //information de promotion et prix après la promo 
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
        
        //composition, taille de référence et conditionnement
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
            for (String srcLabel : labels){
                if(!srcLabel.isEmpty()){
                    infoProd = infoProd + "<label><srcLabel>image/labelscore/" + srcLabel +".jpg</srcLabel></label>";    
                }
            }    
        }else{
            infoProd = infoProd + "<label><srcLabel>nonlabel</srcLabel></label>";
        }           
        
        return infoProd;
    }

    protected void ajouterProduit(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        int idPro = Integer.parseInt(request.getParameter("idPro"));
        HttpSession s1 = request.getSession();
        int idLc = (Integer)s1.getAttribute("Liste");
        TestHibernate.inserProduitListeCourses(idLc,idPro);
       
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
