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
import miage.metier.Magasin;
import miage.metier.Produit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


/**
 * Hibernate.
 */
public class TestHibernate{

        /**
         * Retourner une liste de magasins
         */
        public static List<Magasin> obtenirMagasins (){
        /*----- Ouverture de la session -----*/
         try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Magasin> liste = session.createQuery("from Magasin").list();                
            return liste;
            }
        }
        
        public static void loadPhotos () throws FileNotFoundException, IOException, SQLException{
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
                {
                /*----- Ouverture d'une transaction -----*/
                Transaction t = session.beginTransaction();
                    List<Produit> liste = session.createQuery("from Produit").list();
                    for (Produit p : liste)
                    {
                        InputStream inputStream = p.getPhotoP().getBinaryStream();
                        FileOutputStream fos = new FileOutputStream("src\\main\\webapp\\image\\"+p.getIdP()+".jpg");
                        byte[] b = new byte[1024];
                        int len = -1;
                        while ((len = inputStream.read(b)) != -1) {
                            fos.write(b, 0, len);
                        }
                        fos.close();
                        inputStream.close();
                    }
                // ...
                t.commit(); // Commit et flush automatique de la session.
                }
        }


	/**
	 * Programme de test.
	 */
        public static void main(String[] args) {
            //for(Magasin mag : TestHibernate.obtenirMagasins()){
                        //System.out.println("--"+mag.getNomMag()+"--");
                    //}
            System.out.println("-------------aaaaaaaaaaaaaaaaa");
                    //obtenirMagasins ();
                    //TestHibernate.loadPhotos();
		/*----- Exit -----*/
		System.exit(0);
    }
	

} /*----- Fin de la classe TestHibernate -----*/
