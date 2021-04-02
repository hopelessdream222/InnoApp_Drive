/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.dao.HibernateUtil;
import miage.dao.TestHibernate;
import miage.metier.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ccc
 */
public class ServletLireFinalPanier extends HttpServlet {

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
            out.println("<liste_comporterfinal>");

            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            Client client = (Client) s.getAttribute("client");
            int idCli = client.getIdCli();

            /*----- Lecture de liste de mots dans la BD -----*/
            for (miage.metier.Comporter comporter : TestHibernate.chercherPanierClient(idCli)) {
                String res = "";
                try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                    Transaction t = session.beginTransaction();
                    int libelleProm = comporter.getProduits().getProm().getLibelleProm();
                    float pourcentage = comporter.getProduits().getProm().getPourcentageProm();
                    if (libelleProm == 0) {
                        res = " ";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> " + res + "</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP>");

                    } else if (libelleProm == 1) {
                        res = "-" + pourcentage * 100 + " % sur premierAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP>");

                    } else if (libelleProm == 2) {
                        res = "-" + pourcentage * 100 + " % sur deuxiemeAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP>");

                    } else if (libelleProm == 3) {
                        res = "-" + pourcentage * 100 + " % sur troisiemeAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP>");

                    }
                } 
            }
            out.println("</liste_comporterfinal>");
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
