package miage.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import miage.metier.Produit;
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
	public static List<Produit> chercherCinqProduits ()
		{
		/*----- Ouverture de la session -----*/
		try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
			{
			/*----- Ouverture d'une transaction -----*/
			Transaction t = session.beginTransaction();
			//List<Produit> liste = session.createQuery("select new miage.metier.Produit(libelleP,prixUnitaireP,prixKGP,nutriScoreP,photoP,labelP,formatP,conditionnementP,categorieP) from Produit where idP<=5").list();
                        List<Produit> liste = session.createQuery("from Produit where idP<=5").list();
                        //for(Produit p:liste)
                           //System.out.println("Produit: "+p.getLibelleP()+"photo:"+p.getPhotoP());        
                       // t.commit(); // Commit et flush automatique de la session.
                       return liste;
			}
		}

	/**
	 * Programme de test.
	 */
	public static void main(String[] args) 
		{
                   TestHibernate.chercherCinqProduits();
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
