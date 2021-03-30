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
import miage.metier.Produit;
import miage.metier.Rayon;

/**
 *
 * @author lenovo
 */
public class ServletRechercheProdUnCate extends HttpServlet {

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
        int idCate = Integer.parseInt(request.getParameter("cateId"));
        System.out.println("cate id:"+idCate);
        /*----- Type de la réponse -----*/
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
