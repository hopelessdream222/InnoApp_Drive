/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.dao.HibernateUtil;
import miage.dao.TestHibernate;
import miage.metier.Client;
import miage.metier.Magasin;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 11218
 */
public class ServletLirePanierConfirmer extends HttpServlet {

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
        /*----- Type de la reponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_panierconfirmer>");

            /*----- Recuperation le session de client -----*/
            HttpSession s = request.getSession();
            Client client = (Client) s.getAttribute("client");
            System.out.println("cccccccc"+client.getIdCli());
            int idMag = (Integer) s.getAttribute("idMag");
            System.out.println("mmmmmmmmmmmmm"+idMag);
            
            // Calculer le point fidelite utilisable pour une commande
            int pointfi = TestHibernate.chercherPointfideliteUtilisableClient(client.getIdCli());
            pointfi = pointfi * 10;
            System.out.println("ppppppppppp"+pointfi);
            // Calculer l'economie totale d'une commande
            float economie = TestHibernate.calculerEconomiePromotionClient(client.getIdCli());
            System.out.println("eeeeeeeeeeeeeeeeeee"+economie);
            /*----- Lecture de liste de produit dans le panier de client dans la BD -----*/
            for (miage.metier.Comporter comporter : TestHibernate.chercherPanierClient(client.getIdCli())) {
                String res = " ";
                
                try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                    Transaction t = session.beginTransaction();
                    
                    int libelleProm = comporter.getProduits().getProm().getLibelleProm();
                    float pourcentage = comporter.getProduits().getProm().getPourcentageProm();
                    // Afficher les infos de produit selon son type de promotion
                    if (libelleProm == 0) {
//                        res = " ";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> " + res + "</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + res +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    } else if (libelleProm == 1) {
                        res = "- " + pourcentage * 100 + " % sur premierAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    } else if (libelleProm == 2) {
                        res = "- "+pourcentage * 100 + " % sur deuxiemeAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    } else if (libelleProm == 3) {
                        res = "- " + pourcentage * 100 + " % sur troisiemeAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    }
                } 
                
                double nouveauprix = TestHibernate.calculerEconomiePromProduitPanierClient(client.getIdCli(),comporter.getProduits().getIdP()); 
                out.println("<nouveauprix>"+nouveauprix+"</nouveauprix>");
                
                Map map = new HashMap();
                map = TestHibernate.comparerQtePanierQteStockClient(client.getIdCli(), comporter.getProduits().getIdP(), idMag);
                out.println("<disponibilite>"+map.get(comporter.getProduits())+"</disponibilite>");

            }

            out.println("</liste_panierconfirmer>");
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
