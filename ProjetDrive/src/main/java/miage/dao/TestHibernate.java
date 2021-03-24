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
import java.util.logging.Level;
import java.util.logging.Logger;
import miage.metier.Client;
import miage.metier.Comporter;
import miage.metier.Magasin;
import miage.metier.Produit;
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
    public static List<Produit> chercherCinqProduits() {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            //List<Produit> liste = session.createQuery("select new miage.metier.Produit(libelleP,prixUnitaireP,prixKGP,nutriScoreP,photoP,labelP,formatP,conditionnementP,categorieP) from Produit where idP<=5").list();
            List<Produit> liste = session.createQuery("from Produit where idP>=3 and idP<=6").list();
            //for(Produit p:liste)
            //System.out.println("Produit: "+p.getLibelleP()+"photo:"+p.getPhotoP());        
            // t.commit(); // Commit et flush automatique de la session.
            return liste;
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
	/**
	 * Programme de test.
	 */
    public static void main(String[] args) throws IOException, FileNotFoundException, SQLException, LazyInitializationException{
        //System.out.println(TestHibernate.chercherCinqProduits());
        //TestHibernate.loadPhotos();
        TestHibernate.obtenirMagasins();
        /*----- Exit -----*/
        System.exit(0);
    }


} /*----- Fin de la classe TestHibernate -----*/
