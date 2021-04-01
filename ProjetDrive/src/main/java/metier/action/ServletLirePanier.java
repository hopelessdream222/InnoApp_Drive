package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.dao.HibernateUtil;
import miage.dao.TestHibernate;
import static miage.dao.TestHibernate.chercherPointfideliteUtilisableClient;
import miage.metier.Client;
import miage.metier.Comporter;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 11218
 */
public class ServletLirePanier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_comporter>");

            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            Client client = (Client) s.getAttribute("client");

            int idCli = client.getIdCli();
            int pointfi = TestHibernate.chercherPointfideliteUtilisableClient(idCli);
            pointfi = pointfi * 10;
            float economie = TestHibernate.calculerEconomiePromotionClient(idCli);
            /*----- Lecture de liste de mots dans la BD -----*/
            for (miage.metier.Comporter comporter : TestHibernate.chercherPanierClient(idCli)) {
                String res = "";
                try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                    Transaction t = session.beginTransaction();
                    int libelleProm = comporter.getProduits().getProm().getLibelleProm();
                    float pourcentage = comporter.getProduits().getProm().getPourcentageProm();
                    if (libelleProm == 1) {
                        res = pourcentage * 100 + " % sur premierAchete";
                        out.println("<src>image/produits/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    } else if (libelleProm == 2) {
                        res = pourcentage * 100 + " % sur deuxiemeAchete";
                        out.println("<src>image/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    } else if (libelleProm == 3) {
                        res = pourcentage * 100 + " % sur troisiemeAchete";
                        out.println("<src>image/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                                + comporter.getProduits().getLibelleP() 
                                + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                                + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                                + comporter.getQtePP() + "</Qte><emailCli>" 
                                + client.getEmailCli() + "</emailCli><idP>" 
                                + comporter.getProduits().getIdP() + "</idP><economie>" 
                                + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");

                    }
                } catch (NullPointerException npe) {
                    res = "Pas de promotion";
                    out.println("<src>image/" + comporter.getProduits().getIdP() + ".jpg</src><libelleP>" 
                            + comporter.getProduits().getLibelleP() 
                            + "</libelleP><promotion> (" + res + ")</promotion><PrixUnitaireP>" 
                            + comporter.getProduits().getPrixUnitaireP() + "</PrixUnitaireP><Qte>" 
                            + comporter.getQtePP() + "</Qte><emailCli>" 
                            + client.getEmailCli() + "</emailCli><idP>" 
                            + comporter.getProduits().getIdP() + "</idP><economie>" 
                            + economie +"</economie><pointfi>"+ pointfi +"</pointfi>");
                }
            }

            out.println("</liste_comporter>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
/*----- Fin de la servlet ServletCitation -----*/
