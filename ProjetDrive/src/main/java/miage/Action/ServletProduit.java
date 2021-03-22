package miage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Cette servlet retourne un flux XML.
 */
public class ServletProduit extends HttpServlet
{
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
			out.println("<liste_produit>");
			/*----- Récupération des paramètres -----*/
			//String nom = request.getParameter("nomauteur");
			try {
				/*----- Lecture de liste de mots dans la BD -----*/
				List<miage.metier.Produit>  listeP = miage.dao.TestHibernate.chercherCinqProduits();
				for (miage.metier.Produit p: listeP)
                                      out.println("<libelleP>" + p.getLibelleP() + "</libelleP>");
                                //out.println("<libelleP>" + p.getLibelleP() + "</libelleP> <photoP>" + p.getPhotoP() + "</photoP>");
				}
			catch (Exception ex){
				out.println("<libelle>Erreur - " + ex.getMessage() + "</libelle>");
				}
			out.println("</liste_produit>");
			}
		}

	@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }

} /*----- Fin de la servlet ServletAuteur -----*/
