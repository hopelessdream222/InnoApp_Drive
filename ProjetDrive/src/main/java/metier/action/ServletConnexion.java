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
import miage.metier.Client;
import miage.metier.Produit;

/**
 *
 * @author ymfig
 */
public class ServletConnexion extends HttpServlet {


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
        String id = request.getParameter("id");
        String mdp = request.getParameter("mdp");
        //Appeler la method avec id et mdp
        Client client= miage.dao.TestHibernate.clientConnecter(id,mdp);
        System.out.println("---------"+client);
        if(client.getEmailCli()==null){
            /*----- Type de la réponse -----*/
            response.setContentType("application/xml;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()){
                /*----- Ecriture de la page XML -----*/
                //Si on trouve pas de client, on fait une response de "echec"
                out.println("<?xml version=\"1.0\"?>");
                out.println("<responseConnection>");
                out.println("<res>echec</res>");
                out.println("</responseConnection>");
                System.out.println("---echec---");
            }
        }else{
            System.out.println("---reussi---");
            HttpSession s = request.getSession();
            s.setAttribute("client", client);
            /*----- Type de la réponse -----*/
            response.setContentType("application/xml;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()){
                /*----- Ecriture de la page XML -----*/
                //Si on trouve pas de client, on fait une response de "echec"
                out.println("<?xml version=\"1.0\"?>");
                out.println("<responseConnection><res>reussi</res></responseConnection>");
                
            }
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

    private Client clientConnecter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
