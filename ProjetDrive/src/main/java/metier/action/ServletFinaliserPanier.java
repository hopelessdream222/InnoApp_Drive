/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.metier.Client;
import miage.metier.Produit;

/**
 *
 * @author ymfig
 */
public class ServletFinaliserPanier extends HttpServlet {
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

        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*Récupérer l'attribut "idMag" qui est transmis par le fichier JS*/
            int idMag = Integer.parseInt(request.getParameter("idMag"));
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");

            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            s.setAttribute("idMag", idMag);
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            //List<Produit> lProduits = obtenirProduitPanier();
            List<Produit> lProduits = new ArrayList<Produit>();
            for (Produit produit : lProduits){
                out.println("<idProd>"+ produit.getIdP() +"</idProd>");                
            }
            
            if(s.getAttribute("client") != null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getIdCli()+"</client>");
                
            }            
            out.println("</liste_produit>");
        }

        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
