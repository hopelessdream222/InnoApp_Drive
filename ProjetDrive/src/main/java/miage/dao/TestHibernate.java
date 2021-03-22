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
	public static void chercherCinqProduits ()
		{
		/*----- Ouverture de la session -----*/
		try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
			{
			/*----- Ouverture d'une transaction -----*/
			Transaction t = session.beginTransaction();
			// ...
                        t.commit(); // Commit et flush automatique de la session.
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
