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
public class ServletListeCourses extends HttpServlet {


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
            case "afficherListeCourses":
                afficherListeCourses(request, response);
                break;
            case "AjouterListeCourses":
                AjouterListeCourses(request, response);
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
                "<src>image/" + produit.getIdP() +".jpg</src>"+
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
    protected void afficherListeCourses(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
       
    }
    
    protected void AjouterListeCourses(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
       
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
