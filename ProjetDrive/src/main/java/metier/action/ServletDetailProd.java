
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static miage.dao.TestHibernate.chercherCinqProduits;
import miage.metier.Client;
import miage.metier.Produit;

public class ServletDetailProd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");

            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            
            
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Produit> lProduits = chercherCinqProduits();
                    
            for (Produit produit : lProduits){
                out.println("<src>image/" + produit.getIdP() +".jpg</src><idProd>"+ produit.getIdP() +"</idProd><libProd>"+produit.toString()+"</libProd>");                
            }
            
            if(s.getAttribute("client")!=null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
                //System.out.println("****************"+client.getNomCli());
            }else{
                //System.out.println("-------");
                //System.out.println("****************"+client.getNomCli());
            }
            
            out.println("</liste_produit>");
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
       doGet(request, response);
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
