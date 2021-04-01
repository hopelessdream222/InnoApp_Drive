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
import miage.metier.Client;
import miage.metier.Produit;

/**
 *
 * @author 11218
 */
public class ServletRemplacerProd extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Type de la réponse
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {

            // Ecriture de la page XML
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_remplacerproduit>");

            // Récupération le session de client
            HttpSession s = request.getSession();
            Client client = (Client) s.getAttribute("client");
            int idCli = client.getIdCli();
            int idMag=(int) s.getAttribute("idMag");
            if (s.getAttribute("client") != null) {
                out.println("<client>" + client.getEmailCli() + "</client>");
            } else {
                out.println("<client>horsConnection</client>");
            }

//            String lidp = "5,8";
            String lidp = (String) s.getAttribute("lidp");

            if (lidp.indexOf(",") > 0) {
                String str[] = lidp.split(",");
                int idp[] = new int[str.length];
                for (int i = 0; i < str.length; i++) {
                    idp[i] = Integer.parseInt(str[i]);
                }
                for (int j = 0; j < idp.length; j++) {
                    List<Produit> lProduits = miage.dao.TestHibernate.chercherProduitRemplacementClient(client.getIdCli(), idp[j],idMag);
                    for (Produit produit : lProduits) {
                        System.out.println("for :" + produit.toString());
                        out.println(creerModuleProduit(produit));
//                        out.println("<src>image/produits/" + produit.getIdP() + ".jpg</src><idProd>" + produit.getIdP()
//                                + "</idProd><libProd>" + produit.getLibelleP() + "</libProd>"
//                                + "<formatProd>" + produit.getFormatP() + "</formatProd>"
//                                + "<prixKGProd>" + produit.getPrixKGP() + "</prixKGProd>"
//                                + "<prixUniteProd>" + produit.getPrixUnitaireP() + "</prixUniteProd>");
//                        // Promotion
//                    if (produit.getProm().getIdProm() == 0) {
//                        out.println("<promotionProd> </promotionProd>");
//                    } else {
//                        out.println("<promotionProd>" + produit.getProm().getPourcentageProm() + "</promotionProd>");
//                    }
                    }
                    miage.dao.TestHibernate.supprimerProduitPanier(idCli,idp[j]);
                }
            } else {
                int idp = Integer.parseInt(lidp);
                List<Produit> lProduits = miage.dao.TestHibernate.chercherProduitRemplacementClient(client.getIdCli(), idp,idMag);
                for (Produit produit : lProduits) {
                    out.println(creerModuleProduit(produit));
//                    System.out.println("for :" + produit.toString());
//                    out.println("<src>image/produits/" + produit.getIdP() + ".jpg</src><idProd>" + produit.getIdP()
//                            + "</idProd><libProd>" + produit.getLibelleP() + "</libProd>"
//                            + "<formatProd>" + produit.getFormatP() + "</formatProd>"
//                            + "<prixKGProd>" + produit.getPrixKGP() + "</prixKGProd>"
//                            + "<prixUniteProd>" + produit.getPrixUnitaireP() + "</prixUniteProd>");
//                    // Promotion
//                    if (produit.getProm().getIdProm() == 0) {
//                        out.println("<promotionProd> </promotionProd>");
//                    } else {
//                        out.println("<promotionProd>" + produit.getProm().getPourcentageProm() + "</promotionProd>");
//                    }
                }
                miage.dao.TestHibernate.supprimerProduitPanier(idCli,idp);
            }
            out.println("</liste_remplacerproduit>");
        
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
