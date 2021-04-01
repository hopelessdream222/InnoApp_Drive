/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
                //System.out.println("jin case le");
                ajouterListeCourses(request, response);
                break;
            case "AfficherPostIt":
                //System.out.println("jin case le");
                afficherPostIt(request, response);
                break;
            case "AfficherProduit":
                //System.out.println("jin case le");
                afficherProduit(request, response);
                break;    
            case "AjouterPostIt":
                //System.out.println("jin case le");
                ajouterPostIt(request, response);
                break;     
            case "SaisirSession":
                //System.out.println("jin case le");
                saisirSession(request, response);
                break;
            case "SaisirSessionIng":
                //System.out.println("jin case le");
                SaisirSessionIng(request, response);
                break;
            case "afficherNomListe":
                //System.out.println("jin case le");
                afficherNomListe(request, response);
                break;
        }
    }
    
    protected void SaisirSessionIng(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        int idIng = Integer.parseInt(request.getParameter("idIng"));        
        HttpSession s = request.getSession();
        s.setAttribute("Ingredient", idIng);     
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
    
    protected void ajouterListeCourses(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         String nomLst = (String)request.getParameter("nomLst");
        System.out.println("____________"+nomLst);
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            
             /*----- Récupération le session de client -----*/
            HttpSession s = request.getSession();
            if(s.getAttribute("client")!=null){
                Client client = (Client)s.getAttribute("client");
                out.println("<client>"+client.getEmailCli()+"</client>");
                TestHibernate.insertListeCoursesClient(client.getIdCli(),nomLst);
                System.out.println("reussi222222222222");
            }            
        }
    }
    
    protected void saisirSession(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession s = request.getSession();
        Client client = (Client)s.getAttribute("client");

        List<ListeCourse> lstC = miage.dao.TestHibernate.chercherListeCourseClient(client.getIdCli()); 
        
        int idLst = Integer.parseInt(request.getParameter("idLst"));       
        s.setAttribute("Liste", idLst);
        
        try (PrintWriter out = response.getWriter()) {
            String libLst = "nonLib";
            for (ListeCourse l : lstC) {
                if (l.getIdListe() == idLst) {
                    libLst = l.getLibelleListe();
                    break;
                }
            }
            /*----- Ecriture de la page XML -----*/
            out.println("<libListe>" + libLst + "</libListe>");
        }
    }
    
    protected void afficherNomListe(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession s1 = request.getSession();
        Client client = (Client)s1.getAttribute("client");
        int idLc = (Integer)s1.getAttribute("Liste");
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<ListeCourse> lstC = miage.dao.TestHibernate.chercherListeCourseClient(client.getIdCli());

            String libLst = "Veuillez Choisir Liste";
            for (ListeCourse l : lstC) {
                if (l.getIdListe() == idLc) {
                    libLst = l.getLibelleListe();
                    break;
                }
            }
            /*----- Ecriture de la page XML -----*/
            out.println("<libListe>" + libLst + "</libListe>");
        
            out.println("</liste_produit>");
        }
    }
   
    protected void afficherProduit(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession s1 = request.getSession();
        int idLc = (Integer)s1.getAttribute("Liste");
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Lecture de liste de mots dans la BD -----*/
            //Appeler la fonction dans DAO
            List<Produit> lProduits = miage.dao.TestHibernate.obtenirProduitListeCourse(idLc);
                    
            for (Produit produit : lProduits){
                out.println(creerModuleProduit(produit));
            }
                   
            out.println("</liste_produit>");
        }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected String creerModuleProduit(Produit produit){
        String infoProd = "";
        //id, reporatoire de l'image,libelle, format, prix unitaire, prix kg
        infoProd = "<idProd>"+produit.getIdP()+"</idProd>"+
                "<libProd>"+produit.getLibelleP()+"</libProd>"+
                "<src>image/produits/" + produit.getIdP() +".jpg</src>"+
                "<formatProd>"+produit.getFormatP()+"</formatProd>"+
                "<prixKGProd>"+produit.getPrixKGP()+"</prixKGProd>"+
                "<prixUniteProd>"+produit.getPrixUnitaireP()+"</prixUniteProd>";
        
        //information de promotion et prix après la promo 
        Float economie = miage.dao.TestHibernate.calculerEconomiePromotionClientUnProd(produit.getIdP());
        System.out.println(produit.getIdP()+"- economie"+economie);
        String prixPromo = "nonPrixPromo";
        String infoPromo = "nonpromotion";
        //si un produit a une promotion
        if(economie != 0){
            Float prixPromoLong = produit.getPrixUnitaireP()-economie;
            DecimalFormat df= new  DecimalFormat( "0.00" ); 

            prixPromo = df.format(prixPromoLong);
            infoPromo = miage.dao.TestHibernate.chercherProduitPromotion(produit.getIdP());
        }
        infoProd = infoProd + "<prixPromo>"+prixPromo+"</prixPromo>"+
                            "<promotionProd>"+infoPromo+"</promotionProd>";
        
        //nutriScore d'un produit
       if(!produit.getNutriScoreP().isEmpty()){
            infoProd = infoProd + "<srcNutriScore>image/labelscore/" + produit.getNutriScoreP() +".jpg</srcNutriScore>";
        }else{
           infoProd = infoProd + "<srcNutriScore>nonNS</srcNutriScore>";
        }
        
        //composition, taille de référence et conditionnement
        String compo = "noncomposition";
        String tailleRef = "nontaille";
        String cond = "noncoditionnement";
        
        if(produit.getCompositionP() != null){
            compo = produit.getCompositionP();
        }
        if(produit.getTailleReferenceP() != null){
            tailleRef = produit.getTailleReferenceP();
        }
        if(produit.getConditionnementP() != null){
            cond = produit.getConditionnementP();
        }
        infoProd = infoProd +"<compositionProd>"+compo+"</compositionProd>"+
                              "<tailleProd>"+tailleRef+"</tailleProd>"+
                              "<condProd>"+cond+"</condProd>";        
        
        //labels d'un produit
        List<String> labels = miage.dao.TestHibernate.afficherLabels(produit.getLabelP());
        System.out.println("label:"+labels);
        if(labels.size() != 0){
            for (String srcLabel : labels){
                if(!srcLabel.isEmpty()){
                    infoProd = infoProd + "<label><srcLabel>image/labelscore/" + srcLabel +".jpg</srcLabel></label>";    
                }
            }    
        }else{
            infoProd = infoProd + "<label><srcLabel>nonlabel</srcLabel></label>";
        }           
        
        return infoProd;
    }
    protected void afficherPostIt(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        HttpSession s1 = request.getSession();
        int idLc = (Integer)s1.getAttribute("Liste");
        
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_produit>");
 
            /*----- Lecture de liste de mots dans la BD -----*/

            List<Ingredient> lIngredients = miage.dao.TestHibernate.obtenirPostitListeCourse(idLc);  
            
            for (Ingredient ing : lIngredients){
                out.println("<ingLib>" + ing.getLibelleIng() +"</ingLib><idIng>" + ing.getIdIng()+"</idIng>"); 
                System.out.println("<ingLib>" + ing.getLibelleIng() +"</ingLib><idIng>" + ing.getIdIng()+"</idIng>");
            }
            //obtenir liste ingredient
            List<Ingredient> lstIng = TestHibernate.obtenirIngredient();
            out.println("<tousIng>");
            for(Ingredient ing : lstIng){
                out.println("<TlibIng>"+ing.getLibelleIng()+"</TlibIng>"+
                            "<TidIng>"+ing.getIdIng()+"</TidIng>");
            }
            out.println("</tousIng>");
            
            out.println("</liste_produit>");
        }
    }
    protected void ajouterPostIt(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        int idPostit = Integer.parseInt(request.getParameter("idPostIt"));
        HttpSession s1 = request.getSession();
        int idLc = (Integer)s1.getAttribute("Liste");
                
                
        //System.out.println("____________"+nomLst);
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()){
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<Liste>");
 
                       
            //Ajouter post it
            String res = TestHibernate.inserPostitListeCourses(idLc,idPostit);
            /*----- Inserer une liste de course dans la BD -----*/
            
            out.println("<res>"+res+"</res>");
                        
            out.println("</Liste>");
            
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
