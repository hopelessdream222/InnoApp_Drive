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
import miage.dao.HibernateUtil;
import miage.dao.TestHibernate;
import miage.metier.Client;
import miage.metier.Disponibilite;
import miage.metier.Magasin;
import org.hibernate.Session;

/**
 *
 * @author 11218
 */
public class ServletChoisirCreneau extends HttpServlet {


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
//            int idMag = Integer.parseInt(request.getParameter("idMag"));
//            String nomMag = request.getParameter("nomMag")
//            String date = request.getParameter("date");
            /*----- Récupération le session de client -----*/
            HttpSession s1 = request.getSession();
            int idMag = (Integer)s1.getAttribute("idMag");
            String dater = (String)s1.getAttribute("date");
//            HttpSession s2 = request.getSession();
//            String nomMag = (String)s2.getAttribute("nomMag");
//            HttpSession s3 = request.getSession();
//            Client client = (Client) s3.getAttribute("client");
//            out.println("<emailCli>" + client.getEmailCli() + "</emailCli><nomMag>"+nomMag+"</nomMag>");
////            out.println("<emailCli>" + client.getEmailCli() + "</emailCli><nomMag>" + nomMag + "</nomMag><idMag>"+idMag+"</idMag>");
//
////           
            /*----- Lecture de liste de mots dans la BD -----*/           
            List<Disponibilite> lCreneau = TestHibernate.chercherCreneaux(idMag,dater);

            for (Disponibilite d : lCreneau) {
                out.println("<idCre>" + d.getCreneaux().getIdCren() + "</idCre><dureeCreneau>"+d.getCreneaux().getDureeCren()+"</dureeCreneau><nbPlaceCreneau>"+d.getNbPlaceRest()+"</nbPlaceCreneau>");
            }

            out.println("</liste_creneau>");
        } catch (ParseException ex) {
            Logger.getLogger(ServletChoisirCreneau.class.getName()).log(Level.SEVERE, null, ex);
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
