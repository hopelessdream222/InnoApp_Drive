/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.metier.Client;
import miage.metier.Ingredient;
import miage.metier.Necessiter;
import miage.metier.Produit;
import miage.metier.Recette;
import org.hibernate.Hibernate;

/**
 *
 * @author 75835
 */
public class ServletDetailRecette extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        switch(request.getParameter("method")){
            case "detailRecette":
                plusDetailRe(request,response);
                break;
            case "afficherNbPanier":
                afficherNbPanier(request,response);
                break;        
        }
    }    
         
       protected void plusDetailRe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                    //nt idRecette = Integer.parseInt(request.getParameter("idRecette"));
        HttpSession s = request.getSession();
        int idRecette = (Integer)s.getAttribute("recette");
        //System.out.println("servlet detail recette id:"+idRecette);
        
        //Appeler methode
        Recette recette = miage.dao.TestHibernate.loadRecette(idRecette);
        //System.out.println(recette.getLibelleRect()+"-------");
        //System.out.println("Lib"+recette.getLibelleRect());
        List<Necessiter> lstN=miage.dao.TestHibernate.chercherIngRecette(idRecette);
        
        /*----- Type de la rÃ©ponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            //Si on trouve pas de client, on fait une response de "echec"
            System.out.println("jin try le");
            out.println("<?xml version=\"1.0\"?>");
            out.println("<responseRecette><recetteLib>"+recette.getLibelleRect()+"</recetteLib>");
            out.println("<reSrc>image/recettes/"+idRecette+".jpg</reSrc>");
            
            //System.out.println("idididididid"+idRecette);
            for(Necessiter n : lstN){
                System.out.println("ing: "+n.getIngredient().getIdIng()+n.getIngredient().getLibelleIng());
                List<Produit> lProduits = miage.dao.TestHibernate.chercherProduitRecommenter(idRecette,n.getIngredient().getIdIng());
                System.out.println("prodCommenter----- " +lProduits.get(0).getLibelleP());
                out.println("<ingLib>"+n.getIngredient().getLibelleIng()+"</ingLib>"+
                            "<qte>"+n.getQteRI()+"</qte>"+
                            "<mesure>"+n.getIngredient().getUnitedMesureIng()+"</mesure>"+        
                            "<prodId>"+lProduits.get(0).getIdP()+"</prodId>"+
                            "<prodLib>"+lProduits.get(0).getLibelleP()+"</prodLib>");
                
            }
            /*----- Récupération le session de client -----*/
            HttpSession sClient = request.getSession();
            if(sClient.getAttribute("client")!=null){
                Client client = (Client)sClient.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
            }else{
                out.println("<client>horsConnection</client>");
            }
            out.println("</responseRecette>");
        }
}       
       protected void afficherNbPanier(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Récupération le session de client -----*/
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
