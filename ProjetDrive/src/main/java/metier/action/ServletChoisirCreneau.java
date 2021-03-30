/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.dao.TestHibernate;
import static miage.dao.TestHibernate.chercherCreneaux;
import miage.metier.Client;
import miage.metier.Comporter;
import miage.metier.Creneau;
import miage.metier.Disponibilite;

/**
 *
 * @author 11218
 */
public class ServletChoisirCreneau extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

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
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_creneau>");

            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            Client client = (Client)s.getAttribute("client");
            int idMag = (Integer)s.getAttribute("idMag");
            /*----- Lecture de liste de mots dans la BD -----*/
            List<Disponibilite> lCreneau = TestHibernate.chercherCreneaux(idMag);

            for (Disponibilite d : lCreneau) {
                out.println("<idCre>" + d.getCreneaux().getIdCren() + "</idCre><dureeCreneau>" + d.getCreneaux().getDureeCren() + "</dureeCreneau><nbPlaceCreneau>" + d.getNbPlaceRest() + "</nbPlaceCreneau><emailCli>" + client.getEmailCli() + "</emailCli>");
            }

            out.println("</liste_creneau>");
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
        processRequest(request, response);
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
