/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.metier.Client;
import miage.metier.Produit;
import miage.metier.Rayon;

/**
 *
 * @author lenovo
 */
public class ServletDetailProd extends HttpServlet {

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
        /*----- Type de la r�ponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Produit> lProduits = miage.dao.TestHibernate.chercherNeufProduits();
            System.out.println("lstp:"+lProduits);        
            for (Produit produit : lProduits){
                System.out.println("for :"+produit.toString());
               out.println("<src>image/" + produit.getIdP() +".jpg</src><idProd>"+ produit.getIdP() +
                        "</idProd><libProd>"+produit.getLibelleP()+"</libProd>"+
                        "<formatProd>"+produit.getFormatP()+"</formatProd>"+
                        "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                        "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>");                
            }
            List<Rayon> lRayons = miage.dao.TestHibernate.obtenirRayons();  
            
            for (Rayon rayon : lRayons){
                out.println("<rayonProd>" + rayon.getLibelleRay() +"</rayonProd><rayonId>" + rayon.getIdRay() +"</rayonId>");                
            }
            /*----- R�cup�ration le session de client -----*/
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
       doGet(request, response);
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
