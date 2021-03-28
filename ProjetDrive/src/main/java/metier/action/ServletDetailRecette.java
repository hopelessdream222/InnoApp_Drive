/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //nt idRecette = Integer.parseInt(request.getParameter("idRecette"));
        HttpSession s = request.getSession();
        int idRecette = (Integer)s.getAttribute("recette");
        
        //Appeler methode
        Recette recette = miage.dao.TestHibernate.loadRecette(idRecette);
        System.out.println(recette.getLibelleRect()+"-------");
        //System.out.println("Lib"+recette.getLibelleRect());
        Map<Ingredient, Necessiter> m = recette.getNecessiters();
        System.out.println(m.size()+"-------");
        
        /*----- Type de la rÃ©ponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            //Si on trouve pas de client, on fait une response de "echec"
            out.println("<?xml version=\"1.0\"?>");
            out.println("<responseRecette><recetteLib>"+recette.getLibelleRect()+"</recetteLib>");
            System.out.println("idididididid"+idRecette);
            for(Ingredient ing : m.keySet()){
                System.out.println("idididididid"+idRecette);
                out.println("<ingLib>"+ing.getLibelleIng()+"</ingLib>");
                System.out.println(ing.getLibelleIng()+"=========");
                out.println("<qte>"+m.get(ing).getQteRI()+"</qte>");
                System.out.println(m.get(ing).getQteRI()+"=========");
                List<Produit> lProduits = miage.dao.TestHibernate.chercherProduitRecommenter(idRecette,ing.getIdIng());
                out.println("<prodLib>"+lProduits.get(0).getLibelleP()+"</prodLib>");
                System.out.println(lProduits.get(0).getLibelleP()+"=========");
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
