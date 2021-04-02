/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.dao.PDFEnsemble;
import static miage.dao.PDFEnsemble.imprimerFacturation;
import miage.dao.TestHibernate;
import miage.metier.Client;
import miage.metier.Commande;
import miage.metier.Comporter;
import miage.metier.Disponibilite;

/**
 *
 * @author 11218
 */
public class ServletGenererCommande extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
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

//        try (PrintWriter out = response.getWriter()) {
        /*----- Récupération le session de client -----*/
        HttpSession s = request.getSession();
        Client client = (Client) s.getAttribute("client");
        int idMag = (Integer) s.getAttribute("idMag");
        int idCren = (Integer) s.getAttribute("idCre");
        String dateR = (String) s.getAttribute("date");
        float economie = Float.parseFloat(request.getParameter("economie"));
        int pointfidelite = Integer.parseInt(request.getParameter("pointfidelite"));
        int idCmd;
        try {
            idCmd = TestHibernate.enregistrerCmd(client.getIdCli(), idMag, idCren, dateR, economie, pointfidelite);
            imprimerFacturation(client.getIdCli(), idCmd);
        } catch (ParseException ex) {
            Logger.getLogger(ServletGenererCommande.class.getName()).log(Level.SEVERE, null, ex);
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
