/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
public class ServletSupprimerProdPanier extends HttpServlet {
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
		//try (PrintWriter out = response.getWriter()){
                /*----- Récupération des paramètres -----*/
                int idP = Integer.parseInt(request.getParameter("idP"));

                /*----- Récupération le session de client -----*/
                HttpSession s = request.getSession();
                if(s.getAttribute("client")!=null){
                    Client client = (Client)s.getAttribute("client");
                    out.println("<client>"+client.getEmailCli()+"</client>");
                    //System.out.println("****************"+client.getNomCli());
                    TestHibernate.supprimerProduitPanier(client.getIdCli(),idP);
                }else{
                    //System.out.println("-------");
                    //System.out.println("****************"+client.getNomCli());
                    out.println("<client>horsConnection</client>");
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
