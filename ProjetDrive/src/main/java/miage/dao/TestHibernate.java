package miage.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.reflect.Array.set;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import miage.metier.Categorie;
import miage.metier.Client;
import miage.metier.Magasin;
import miage.metier.Produit;
import miage.metier.Rayon;
import org.hibernate.LazyInitializationException;
import org.hibernate.QueryException;
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


    public static List<Produit> chercherCinqProduits() {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            //List<Produit> liste = session.createQuery("select new miage.metier.Produit(libelleP,prixUnitaireP,prixKGP,nutriScoreP,photoP,labelP,formatP,conditionnementP,categorieP) from Produit where idP<=5").list();
            List<Produit> liste = session.createQuery("from Produit where idP>=3 and idP<=6").list();
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
            Query query = session.createQuery( "from Client "
                    + "where emailCli=:mail");
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
    
    public static List<Produit> searchProduits(String mot) 
    {
        List<Produit> liste1 = null;
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("select new miage.metier.Produit(p.idP,p.libelleP,p.prixUnitaireP,p.prixKGP,p.nutriScoreP,p.photoP,p.labelP,p.formatP,p.conditionnementP) "
                    +"from Produit p "
                    + "where p.libelleP like :m");
            query.setParameter("m","%"+ mot + "%");
            List<Produit> liste2 = query.list();
            if (liste2.size() != 0) {
                for (Produit pro : liste2) {
                    liste1.add(pro);
                }
            }
        }
        return liste1;
    }

        
    public static List<Magasin> obtenirMagasins()
    {
        /*----- Ouverture de la session -----*/
         try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Magasin> liste = session.createQuery("from Magasin").list();  
            return liste;
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
        
	/**
	 * Programme de test.
	 */
	public static void main(String[] args) throws IOException, FileNotFoundException, SQLException
		{
                    //TestHibernate.chercherCinqProduits();
                    //TestHibernate.loadPhotos();
                    //System.out.println(TestHibernate.obtenirMagasins());
                    //System.out.println(TestHibernate.obtenirRayons());
                    //System.out.println(TestHibernate.clientConnecter("jules@gmail.com", "123"));
                    System.out.println(TestHibernate.searchProduits("Citrons"));
		/*----- Exit -----*/
		System.exit(0);
		}


	/**
	 * Affichage d'une liste de tableaux d'objets.
	 */
	private static void affichage (List l)
		{
		Iterator e = l.iterator();
		while (e.hasNext())
			{
			Object[] tab_obj = ((Object[]) e.next());

			for (Object obj : tab_obj)
				System.out.print(obj + " ");

			System.out.println("");
			}
		}

} /*----- Fin de la classe TestHibernate -----*/
