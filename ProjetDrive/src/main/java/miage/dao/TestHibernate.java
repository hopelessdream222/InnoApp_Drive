package miage.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import miage.metier.Panier;
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

        public static void lirePanier(int idCli)
        {
           try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
               {
                  session.beginTransaction();
                  Panier pan=session.get(Panier.class,idCli);  //load也可以
                  System.out.print("----->Panier");
                  System.out.print(pan.getProduits().getIdP()+" "+pan.getQtePP());
               }
        }
        
        public static void addDdeEmploye(long id_emp,String jour_dde,int nb,String jour_deb) throws ParseException
       {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
               {
                   Transaction t = session.beginTransaction();
                   
                   //Employe e = session.get(Employe.class,id_emp);
                   //Demande d = new Demande(DF.parse(jour_dde),nb,DF.parse(jour_deb),e);
                   //e.getDemandes().add(d);
                   
                   t.commit();
               }
       }

} /*----- Fin de la classe TestHibernate -----*/
