/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;
import miage.dao.TestHibernate;
import miage.metier.Categorie;
import miage.metier.Client;
import miage.metier.Ingredient;
import miage.metier.ListeCourse;
import miage.metier.Produit;
import miage.metier.Rayon;
import miage.metier.Recette;

/**
 *
 * @author ymfig
 */
public class ServletListeCourses extends HttpServlet {


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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        switch(request.getParameter("method")){
            case "afficherListeCourses":
                afficherListeCourses(request, response);
                break;
            case "AjouterListeCourses":
                System.out.println("jin case le");
                AjouterListeCourses(request, response);                
                break;
        }   
    }
    
    protected void afficherListeCourses(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            Client client = (Client)s.getAttribute("client");
            out.println("<client>"+client.getEmailCli()+"</client>");
            /*----- Lecture de liste de mots dans la BD -----*/

            List<ListeCourse> lListeCourse = miage.dao.TestHibernate.chercherListeCourseClient(client.getIdCli());  
            
            for (ListeCourse lc : lListeCourse){
                out.println("<lcLib>" + lc.getLibelleListe() +"</lcLib><idLc>" + lc.getIdListe() +"</idLc>");                
                System.out.println("<lcLib>" + lc.getLibelleListe() +"</lcLib><idLc>" + lc.getIdListe() +"</idLc>");
            }
           
            out.println("</liste_produit>");
        }
    }
    
    protected void AjouterListeCourses(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         String nomLst = (String)request.getParameter("nomLst");
        System.out.println("____________"+nomLst);
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<Liste>");
            
             /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            if(s.getAttribute("client")!=null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
                TestHibernate.insertListeCoursesClient(client.getIdCli(),nomLst);
                System.out.println("reussi222222222222");
         
            }
            //obtenir liste ingredient
            List<Ingredient> lstIng = TestHibernate.obtenirIngredient();
            for(Ingredient ing : lstIng){
                out.println("<libIng>"+ing.getLibelleIng()+"</libIng>"+
                            "<idIng>"+ing.getIdIng()+"</idIng>");
                System.out.println("lib ing "+ing.getLibelleIng());
            }
            out.println("</Liste>");
            
        }
    }
    
    protected void ObtenirPostIts(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<Ingredient> lstIng = TestHibernate.obtenirIngredient();
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<Liste_ing>");
            /*----- Inserer une liste de course dans la BD -----*/
            for(Ingredient ing : lstIng){
                out.println("<libIng>"+ing.getLibelleIng()+"</libIng>");
                System.out.println("lib ing "+ing.getLibelleIng());
            }
            
            out.println("</Liste_ing>");   
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
