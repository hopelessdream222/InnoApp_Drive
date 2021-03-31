package miage.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 * Chargement de la configuration et création de la SessionFactory.
 * (hibernate.cfg.xml)
 */
public class HibernateUtil
{
	private static final SessionFactory SESSION_FACTORY;
	static
		{
		try	{
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");

			/**
			 * Ajout des classes.
			 * Pour miage.metier.Employe le fichier ressource hbm.xml
			 * attaché est miage/metier/Employe.hbm.xml.
			 */
//			configuration.addClass(miage.metier.Employe.class);

			/**
			 * Entité.
			 */
            configuration.addAnnotatedClass(miage.metier.Produit.class);
            configuration.addAnnotatedClass(miage.metier.Categorie.class);
            configuration.addAnnotatedClass(miage.metier.Rayon.class);
            configuration.addAnnotatedClass(miage.metier.Commande.class);
            configuration.addAnnotatedClass(miage.metier.Client.class);
            configuration.addAnnotatedClass(miage.metier.Magasin.class);
            configuration.addAnnotatedClass(miage.metier.Fournisseur.class);            
            configuration.addAnnotatedClass(miage.metier.LigneCommandeId.class);
            configuration.addAnnotatedClass(miage.metier.LigneCommande.class);
            configuration.addAnnotatedClass(miage.metier.StockerId.class); 
            configuration.addAnnotatedClass(miage.metier.Stocker.class);
            configuration.addAnnotatedClass(miage.metier.Comporter.class); 
            configuration.addAnnotatedClass(miage.metier.ComporterId.class);
            configuration.addAnnotatedClass(miage.metier.Promotion.class); 
            configuration.addAnnotatedClass(miage.metier.Creneau.class);
            configuration.addAnnotatedClass(miage.metier.Disponibilite.class);
            configuration.addAnnotatedClass(miage.metier.DisponibiliteId.class);
            configuration.addAnnotatedClass(miage.metier.Ingredient.class);
            configuration.addAnnotatedClass(miage.metier.Necessiter.class);
            configuration.addAnnotatedClass(miage.metier.NecessiterId.class);
            configuration.addAnnotatedClass(miage.metier.Recette.class);
            configuration.addAnnotatedClass(miage.metier.Panier.class); 
            configuration.addAnnotatedClass(miage.metier.PrefererId.class);
            configuration.addAnnotatedClass(miage.metier.Preferer.class);
            configuration.addAnnotatedClass(miage.metier.ListeCourse.class);
            configuration.addAnnotatedClass(miage.metier.Concerner.class);
            configuration.addAnnotatedClass(miage.metier.ConcernerId.class);
            configuration.addAnnotatedClass(miage.metier.ComposerId.class);
            configuration.addAnnotatedClass(miage.metier.Composer.class);
            

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");

            SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);
			}
		catch (HibernateException ex)
			{
			/*----- Exception -----*/
			System.err.println("Initial SessionFactory creation failed.\n" + ex);
			throw new ExceptionInInitializerError(ex);
			}
		}


	public static SessionFactory getSessionFactory () { return SESSION_FACTORY; }

} /*----- Fin de la classe HibernateUtil -----*/
