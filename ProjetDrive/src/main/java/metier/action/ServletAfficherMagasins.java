/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import miage.metier.Magasin;

/**
 *
 * @author ymfig
 */
public class ServletAfficherMagasins extends HttpServlet {

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
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_magasin>");

            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Magasin> lstMagasin = miage.dao.TestHibernate.obtenirMagasins();
            System.out.println("66666"+lstMagasin);
            for(Magasin mag : lstMagasin){
                out.println("<idMag>"+mag.getIdMag()+"</idMag><nomMag>"+mag.getNomMag()+"</nomMag><adrMag>"+
                   mag.getAdresseMag()+"</adrMag><cpMag>"+mag.getCpMagasin()+","+mag.getVilleMag()+
                   "</cpMag><telMag>"+mag.getTelMag()+"</telMag>");

            }
            out.println("</liste_magasin>");
        }
    }


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
