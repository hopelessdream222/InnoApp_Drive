package miage.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.reflect.Array.set;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import miage.metier.Categorie;
import miage.metier.Client;
import miage.metier.Comporter;
import miage.metier.ComporterId;
import miage.metier.Magasin;
import miage.metier.Panier;
import miage.metier.Produit;
import miage.metier.Rayon;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


/**
 * Hibernate.
 */
public class TestHibernate
{
	/**
	 * Constante.
	 */

	/*----- Format de date -----*/
	private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");


	/**
	 * Création, enregistrement et lecture d'objets.
	 */

	/*----- Création et enregistrement d'employés -----*/
    public static List<Produit> chercherNeufProduits() {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            //List<Produit> liste = session.createQuery("select new miage.metier.Produit(libelleP,prixUnitaireP,prixKGP,nutriScoreP,photoP,labelP,formatP,conditionnementP,categorieP) from Produit where idP<=5").list();
            List<Produit> liste = session.createQuery("from Produit where idP in (1,2,3,65,79,18,36,49,28)").list();
            //for(Produit p:liste)
            //System.out.println("Produit: "+p.getLibelleP()+"photo:"+p.getPhotoP());
            // t.commit(); // Commit et flush automatique de la session.
            return liste;
        }
    }
      public static List<Produit> searchProduits(String mot){
            /*----- Ouverture de la session -----*/
            try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                /*----- Ouverture d'une transaction -----*/
                Transaction t = session.beginTransaction();
                Query query = session.createQuery("from Produit where libelleP LIKE :m");
                query.setParameter("m","%"+ mot + "%");
                List<Produit> lp = query.list();
                return lp;
            }
        }
    public static void loadPhotos() throws FileNotFoundException, IOException, SQLException {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Produit> liste = session.createQuery("from Produit").list();
            for (Produit p : liste) {
                InputStream inputStream = p.getPhotoP().getBinaryStream();
                FileOutputStream fos = new FileOutputStream("src\\main\\webapp\\image\\" + p.getIdP() + ".jpg");
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(b)) != -1) {
                    fos.write(b, 0, len);
                }
                fos.close();
                inputStream.close();
            }
        }
    }

    public static Client clientConnecter(String email, String mdp) {
        Client c = new Client();
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("select new miage.metier.Client(c.idCli,c.nomCli,c.prenomCli,c.emailCli,c.mdpCli,c.telCli,c.pointCli) "
                    + "from Client c "
                    + "where c.emailCli=:mail");
            query.setParameter("mail", email);
            List<Client> rlt = query.list();
            if (rlt.size() != 0) {
                String mdpcli = rlt.get(0).getMdpCli();
                if (mdpcli.equals(mdp)) {
                    c = rlt.get(0);
                }
            }
        }
        return c;
    }


    public static List<Magasin> obtenirMagasins() throws LazyInitializationException
    {
        /*----- Ouverture de la session -----*/
         try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Magasin> liste = session.createQuery("from Magasin").list();
            return liste;
            }
    }
/*----- Chercher les produits dans le panier d'un client-----*/
    public static List<Comporter> chercherPanierClient(int idCli){ //Client client
     /*----- Ouverture de la session -----*/
     try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
      {
      /*----- Ouverture d'une transaction -----*/
      Transaction t = session.beginTransaction();
      List<Comporter> listeRes = new ArrayList();
      List<Comporter> liste = session.createQuery("from Comporter").list();
      for (Comporter c : liste) {
          if(c.getPaniers().getIdPan()==idCli){
              listeRes.add(c);
          }
      }
      return listeRes;
      }
     }
    public static void insertProduitPanier(int idCli,int idP){
    try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
                {
                /*----- Ouverture d'une transaction -----*/
                Transaction t = session.beginTransaction();
                // ...
                Produit p1 = session.get(Produit.class,idP);
                Client c1 = session.get(Client.class, idCli);
                ComporterId comporterid= new ComporterId(idP,idCli); // idp;idPan
                Comporter comportement = new Comporter(comporterid,1,p1,c1.getPanier());
                session.save(comportement);
                t.commit(); // Commit et flush automatique de la session.
                }
    }
     public static void supprimerProduitPanier(int idCli,int idP){
         try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
                {
                /*----- Ouverture d'une transaction -----*/
                Transaction t = session.beginTransaction();
                Client c = session.get(Client.class,idCli);
                Panier p = c.getPanier();
                Set<Produit> produit = p.getComportements().keySet();
                for(Produit pdt : produit)
                    //System.out.println("-- " + pdt.getIdP());
                    if (pdt.getIdP()==idP){
                        //System.out.println("-- " + pdt.getIdP());                    
                        Comporter c2= (Comporter)p.getComportements().get(pdt);
                        //System.out.println("-- " + c2.getQtePP());
                        c.getPanier().getComportements().remove(pdt);                        
                        session.delete(c2);
                        t.commit();
               }
                }
    }
    public static List<Rayon> obtenirRayons() {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Rayon> liste = session.createQuery("from Rayon").list();
            return liste;
        }
    }
    
    
    public static List<Categorie> obtenirCategories(int id) {
        /*----- Ouverture de la session -----*/
        List<Categorie> lstCat=new ArrayList<>();
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
             Rayon ray = session.get(Rayon.class, id);
             Set<Categorie> lstC=ray.getCategories();
             for(Categorie c:lstC){
                 //System.out.println(c.getLibelleCat());
                 lstCat.add(c);                 
             }
        }
        return lstCat;
    }   
    public static List<Produit> obtenirProduits(int id) {
        /*----- Ouverture de la session -----*/
        List<Produit> lstP=new ArrayList<>();
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
             Transaction t = session.beginTransaction();
             Categorie c=session.get(Categorie.class, id);
             Set<Produit> prods=c.getProduits();
             for(Produit p:prods){
                 System.out.println(p.getLibelleP());
                 lstP.add(p);                 
             }
        }
        return lstP;
    }
        	/**
	 * Programme de test.
	 */
	public static void main(String[] args) throws IOException, FileNotFoundException, SQLException, LazyInitializationException{
                //System.out.println(TestHibernate.chercherCinqProduits());
                //TestHibernate.loadPhotos();
               //TestHibernate.obtenirMagasins();
                //chercherCinqProduits();
           for (miage.metier.Comporter c: chercherPanierClient(1))
                System.out.println("comportement:" +c.getPaniers().getIdPan());
            //insertProduitPanier();
                /*----- Exit -----*/
            System.exit(0);
            }

} /*----- Fin de la classe TestHibernate -----*/
