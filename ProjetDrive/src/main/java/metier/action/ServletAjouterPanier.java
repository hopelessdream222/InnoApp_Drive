package metier.action;

import java.io.IOException;


//import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import miage.dao.TestHibernate;
import miage.metier.Client;

/**
 *
 * @author 11218
 */
public class ServletAjouterPanier extends HttpServlet {
@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		/*----- Type de la réponse -----*/
		response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		//try (PrintWriter out = response.getWriter()){
			/*----- Récupération des paramètres -----*/
			int idP = Integer.parseInt(request.getParameter("idP"));
                        
                        /*----- Récupération le session de client -----*/
                HttpSession s = request.getSession();
                if(s.getAttribute("client")!=null){
                    Client client = (Client)s.getAttribute("client");
                    TestHibernate.insertProduitPanier(client.getIdCli(), idP);
                    //out.println("<client>"+client.getEmailCli()+"</client>");
                    //System.out.println("****************"+client.getNomCli());
                }else{
                    //System.out.println("-------");
                    //System.out.println("****************"+client.getNomCli());
                }
                     
		}

	@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }

} /*----- Fin de la servlet ServletCitation -----*/
