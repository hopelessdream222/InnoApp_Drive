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
import miage.dao.TestHibernate;
import miage.metier.Client;
import miage.metier.Comporter;

/**
 *
 * @author 11218
 */
public class ServletLirePanier extends HttpServlet {
@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{		
		/*----- Type de la réponse -----*/
		response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
			out.println("<liste_comporter>");

                        /*----- Récupération le session de client -----*/
                        HttpSession s = request.getSession();
                        Client client = (Client)s.getAttribute("client");
                        int idCli = client.getIdCli();
                        /*----- Lecture de liste de mots dans la BD -----*/
                        
                        List<Comporter> lComporter = TestHibernate.chercherPanierClient(idCli);

                        for (Comporter comporter : lComporter)
                            out.println("<src>image/" + comporter.getProduits().getIdP() +".jpg</src><libelleP>"+comporter.getProduits().getLibelleP()+"</libelleP><PrixUnitaireP>"+comporter.getProduits().getPrixUnitaireP()+"</PrixUnitaireP><Qte>"+comporter.getQtePP()+"</Qte><emailCli>"+client.getEmailCli()+"</emailCli><idP>"+comporter.getProduits().getIdP()+"</idP>");			
                        
                        out.println("</liste_comporter>");	
			}
		}

	@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }

} /*----- Fin de la servlet ServletCitation -----*/
