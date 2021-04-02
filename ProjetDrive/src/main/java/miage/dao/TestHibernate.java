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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import miage.metier.Categorie;
import miage.metier.Client;
import miage.metier.Commande;
import miage.metier.Comporter;
import miage.metier.ComporterId;
import miage.metier.Composer;
import miage.metier.ComposerId;
import miage.metier.Concerner;
import miage.metier.ConcernerId;
import miage.metier.Creneau;
import miage.metier.Disponibilite;
import miage.metier.Ingredient;
import miage.metier.LigneCommande;
import miage.metier.LigneCommandeId;
import miage.metier.ListeCourse;
import miage.metier.Magasin;
import miage.metier.Necessiter;
import miage.metier.Panier;
import miage.metier.Produit;
import miage.metier.Rayon;
import miage.metier.Recette;
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


	/**
	 * Création, enregistrement et lecture d'objets.
	 */

	/*----- Création et enregistrement d'employés -----*/

     public static List<Produit> searchProduits(String mot) throws QueryException
    {
        List<Produit> lp = new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("from Produit where libelleP LIKE :m");
            query.setParameter("m", "%" + mot + "%");
            List<Produit> liste=query.list();           
            if (liste.size() != 0) {
                for(int i=0;i<liste.size();i++) {
                    Produit p = session.get(Produit.class, liste.get(i).getIdP());
                    System.out.println("Produit--"+p.getLibelleP());
                    lp.add(p);
                }
            }
        }
        return lp;
    }

    public static Client clientConnecter(String email, String mdp) {
        Client c = new Client();
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("from Client where emailCli=:mail");
            query.setParameter("mail", email);
            List<Client> rlt = query.list();
            if (rlt.size() != 0) {
                String mdpcli = rlt.get(0).getMdpCli();
                if (mdpcli.equals(mdp)) {
                    c= session.get(Client.class,rlt.get(0).getIdCli());
                    System.out.println(c.getEmailCli());
                }
            }
        }
        return c;
    }


    public static void insertProduitPanier(int idCli, int idP,int qte) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Produit p1 = session.get(Produit.class, idP);
            Client c1 = session.get(Client.class, idCli);
            Comporter comporterment = c1.getPanier().getComportements().get(p1);
            comporterment.setQtePP(comporterment.getQtePP() + qte);
            session.save(comporterment);
            t.commit();
        } catch (NullPointerException npe) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();
            Produit p1 = session.get(Produit.class, idP);
            Client c1 = session.get(Client.class, idCli);
            ComporterId comporterid = new ComporterId(idP, idCli); // idp;idPan
            Comporter comportement = new Comporter(comporterid, qte, p1, c1.getPanier());
            session.save(comportement);
            t.commit(); 
        }
    }
    
    public static Produit loadProduit(int id) {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            session.beginTransaction();
            Produit p = session.get(Produit.class, id);
            //System.out.println("Produit---- " + p.getCategorieP());
            return p;
        }
    }
    
    public static List<String> afficherLabels(String str) {
        List<String> lstLables = new ArrayList<>();
        if(!str.isEmpty()){
            String[] lab = str.split(",");
            for (String a : lab) {
                //System.out.println(a);
                lstLables.add(a); 
            }
        }
        System.out.println(lstLables.size());
        return lstLables;
    }

    /**
     * 
     * @return tous les rayons dans la base de données
     */
    public static List<Rayon> obtenirRayons() {
        List<Rayon> lstRay=new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Rayon> liste = session.createQuery("from Rayon").list();
            for (int i = 0; i < liste.size(); i++) {
                Rayon r = session.get(Rayon.class, liste.get(i).getIdRay());
                lstRay.add(r);
            }
        }
         return lstRay;
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
    
    public static List<Recette> obtenirRecettes() {
        /*----- Ouverture de la session -----*/
        List<Recette> lstRec=new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Recette> liste = session.createQuery("from Recette").list();
            for (int i = 0; i < liste.size(); i++) {
                Recette r = session.get(Recette.class, liste.get(i).getIdRect());
                System.out.println("Recette: " + r.getIdRect());
                lstRec.add(r);
            }
        }
         return lstRec;
    }
    
    public static void insertRecettePanier(int idCli, int idRect) {
        List<Necessiter> lstNes = chercherIngRecette(idRect);
        for (Necessiter n : lstNes) {
            List<Produit> lstP = chercherProduitRecommenter(idRect, n.getIngredient().getIdIng());
            insertProduitPanier(idCli, lstP.get(0).getIdP(), 1);
            System.out.println("cli: " + idCli + "ingredient: " + n.getIngredient().getLibelleIng() + "produit: " + lstP.get(0).getIdP());
        }
    }
    
    public static List<Produit> chercherPromsProduits() {
        List<Produit> lstP=new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            //List<Produit> liste = session.createQuery("select new miage.metier.Produit(libelleP,prixUnitaireP,prixKGP,nutriScoreP,photoP,labelP,formatP,conditionnementP,categorieP) from Produit where idP<=5").list();
            List<Produit> liste = session.createQuery("from Produit where idProm=5 or idProm=4").list();
            for(Produit pr:liste){
                Produit p = session.get(Produit.class, pr.getIdP());
                lstP.add(p);       
                System.out.println(p.getProm().getIdProm());
            } 
        }
        return lstP;
    }
    
    public static Recette loadRecette(int id) {
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            session.beginTransaction();
            Recette r = session.get(Recette.class, id);
            //System.out.println("Produit---- " + p.getCategorieP());
            return r;
        }
    }
        
    public static List<Produit> chercherProduitRecommenter(int idRect, int idIng) { 
        List<Produit> lstP = new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Recette r = session.get(Recette.class, idRect);
            Map<Ingredient, Necessiter> m = r.getNecessiters();
            Ingredient ing = session.get(Ingredient.class, idIng);
            float qte=m.get(ing).getQteRI();
            Query query = session.createQuery("from Produit where idIng=:Ing and prixUnitaireP/prixKGP >=:n order by prixUnitaireP/prixKGP ASC, prixUnitaireP ASC");
            query.setParameter("Ing", idIng);
            query.setParameter("n", qte);
            List<Produit> liste = query.list();
            for (Produit pr : liste) {
                Produit p = session.get(Produit.class, pr.getIdP());
                lstP.add(p);
                System.out.println("Produit: "+p.getIdP());
            }
        }
        return lstP;
    }
    
    public static String chercherProduitPromotion(int idP) {
        String res = "";
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                Transaction t = session.beginTransaction();
                Produit p = session.get(Produit.class, idP);
                
                int libelleProm = p.getProm().getLibelleProm();
                float pourcentage = p.getProm().getPourcentageProm();
                if (libelleProm == 1) {
                    res = "Promo: - "+ pourcentage * 100 + "%";
                    System.out.println(res);
                } else if (libelleProm == 2) {
                    res = "Le deuxième - "+pourcentage * 100 + "%, soit un";
                    System.out.println(res);
                } else if (libelleProm == 3) {
                    res = "Le troisième - "+pourcentage * 100 + "%, soit un";
                    System.out.println(res);
                }
            } catch (NullPointerException npe) {
                res = "Pas de promotion";
                System.out.println(res);
            }
        return res;
    }
    
     public static List<Necessiter> chercherIngRecette(int id) { //Client client
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Recette r = session.get(Recette.class, id);
            Map<Ingredient, Necessiter> m = r.getNecessiters();
            List<Necessiter> lstNecessiter = new ArrayList<>();
            for (Ingredient ing : m.keySet()) {
                System.out.println("Ingredient--" + ing.getLibelleIng());
                lstNecessiter.add(m.get(ing));
            }
            return lstNecessiter;
        }
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
    
    /*----- Chercher la quantite des produits dans le panier d'un client-----*/
    public static int chercherQuantitePanierClient(int idCli){ //Client client
     /*----- Ouverture de la session -----*/
     try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
      {
      /*----- Ouverture d'une transaction -----*/
      Transaction t = session.beginTransaction();
      int quantite = 0;
      List<Comporter> liste = session.createQuery("from Comporter").list();
      for (Comporter c : liste) {
          if(c.getPaniers().getIdPan()==idCli){
              quantite = quantite +c.getQtePP();
          }
      }
      return quantite;
      }
     }
    /*----- insert un produit dans le panier d'un client-----*/
    public static void insertProduitPanier(int idCli, int idP) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Produit p1 = session.get(Produit.class, idP);
            Client c1 = session.get(Client.class, idCli);
            Comporter comporter = c1.getPanier().getComportements().get(p1);
            comporter.setQtePP(comporter.getQtePP() + idCli);
            session.save(comporter);
            t.commit();
        } catch (NullPointerException npe) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();

            Produit p1 = session.get(Produit.class, idP);
            Client c1 = session.get(Client.class, idCli);

            Comporter comportement = new Comporter();
            ComporterId comporterid = new ComporterId(idP, idCli);
            comportement.setComporterId(comporterid);
            comportement.setPaniers(c1.getPanier());
            comportement.setProduits(p1);
            comportement.setQtePP(1);
            session.save(comportement);
            t.commit();
        }
    }

        /*----- Chercher les produits dans le panier d'un client-----*/
    public static List<Comporter> chercherPanierClient(int idCli) { //Client client
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            Panier pan=c.getPanier();
            Map<Produit, Comporter> m=pan.getComportements();
            List<Comporter> listeRes = new ArrayList();
            for (Produit p : m.keySet()) {
                    listeRes.add(m.get(p));
                    //System.out.println(m.get(p).getProduits().getIdP());
            }
            //System.out.println(listeRes.size());
            return listeRes;
        }
    }
    /*----- Chercher les creneaux et le nombre de places disponibles dans un magasin-----*/
    public static List<Disponibilite> chercherCreneaux(int idMag) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Magasin mag = session.get(Magasin.class, idMag);
        List<Disponibilite> liste = new ArrayList();
        for (Creneau c : mag.getCreneaux().keySet()) {
            liste.add(mag.getCreneaux().get(c));
        }
        return liste;
    }
    /*----- Enregistrer les donnees d'une commande-----*/
    public static int enregistrerCmd(int idCli, int idMag, int idCren, String dateR,float economie,int utilisationPointF) throws ParseException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        System.out.println("---------------------"+idCli+idMag+idCren);

        // La date de la commande
        Date dateSys = new Date();   
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatDate.format(dateSys);
        Date dateCmd = formatDate.parse(date);

        // La date retrait de la commande
        SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateRetrait = formatDate2.parse(dateR);

        Client clientCmd = session.get(Client.class, idCli);
        Magasin magCmd = session.get(Magasin.class, idMag);
        Creneau creneauCmd = session.get(Creneau.class, idCren);

        // Chercher des produits dans le panier
        List<Comporter> liste = new ArrayList();
        for (Produit p : clientCmd.getPanier().getComportements().keySet()) {
            liste.add(clientCmd.getPanier().getComportements().get(p));
        }

        // Modifier la nombre de places disponible pour ce creneau ( moins 1)
        magCmd.getCreneaux().get(creneauCmd).setNbPlaceRest(magCmd.getCreneaux().get(creneauCmd).getNbPlaceRest() - 1);

        // Generation d'une nouvelle commande
        Commande cmd = new Commande();
        cmd.setClientCmd(clientCmd);
        cmd.setCreneauCmd(creneauCmd);
        cmd.setDatecmd(dateCmd);
        cmd.setMagasinCmd(magCmd);
        cmd.setDateRetrait(dateRetrait);
        cmd.setEconomieCmd(economie);
        // Calculer le prixTotal de la commande
        float prixTotal = 0;
        for (miage.metier.Comporter comporter : liste) {
            prixTotal += comporter.getQtePP() * comporter.getProduits().getPrixUnitaireP();
        }
        t.commit();
        //point fidelite apres l'utilisation pour une commande
        float c = clientCmd.getPointCli() - chercherPointfideliteUtilisableClient(clientCmd.getIdCli()) * 10;
        // point fidelite apres la validation d'une commande
        int c1 = (int) (c + prixTotal - calculerEconomiePromotionClient(clientCmd.getIdCli())) / 10;

        if (utilisationPointF == 1) {
            cmd.setUtilisationPointF(1);
            cmd.getClientCmd().setPointCli(c1);
        } else if (utilisationPointF == 0) {
            cmd.setUtilisationPointF(0);
        }
        Session session1 = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t1 = session1.beginTransaction();
        session1.save(cmd);
        t1.commit();

        // Generation des lignes commandes
        enregistrerLigneCmd(clientCmd, cmd, liste);
        return cmd.getIdCmd();
    }
    
    
    /*----- Enregistrer des lignes commandes -----*/ 
    public static void enregistrerLigneCmd(Client clientCmd, Commande cmd, List<Comporter> liste) throws ParseException {
        for (Comporter c : liste) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();

            LigneCommande ligneCmd = new LigneCommande();
            LigneCommandeId ligneCmdId = new LigneCommandeId(c.getProduits().getIdP(), cmd.getIdCmd());
            ligneCmd.setLigneId(ligneCmdId);
            ligneCmd.setQteCP(c.getQtePP());

            session.save(ligneCmd);
            t.commit();
            supprimerProduitPanier(clientCmd.getIdCli(), c.getProduits().getIdP());
        }
    }
    /*----- Supprimer un produit dans le panier -----*/     
    public static void supprimerProduitPanier(int idCli, int idP) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Client c = session.get(Client.class, idCli);
            Produit p = session.get(Produit.class, idP);

            Comporter comporter = c.getPanier().getComportements().get(p);
            c.getPanier().getComportements().remove(p);
            session.delete(comporter);
            t.commit();
        }
    }
    public static float calculerEconomiePromotionClient(int idCli) {

        float economie = 0.00f;
        int libelleProm = 0;
        List<Comporter> liste = new ArrayList();
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
        
            for (Produit p : c.getPanier().getComportements().keySet()) {
                liste.add(c.getPanier().getComportements().get(p));

            }
        }
        for (miage.metier.Comporter comporter : liste) {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                Transaction t = session.beginTransaction();
                libelleProm = comporter.getProduits().getProm().getLibelleProm();
                float pu = comporter.getProduits().getPrixUnitaireP();
                float pourcentage = comporter.getProduits().getProm().getPourcentageProm();
                if (libelleProm == 1) {
                    economie += pu * pourcentage;
//                    System.out.println("libelleProm == 1: " + economie);
                } else if (libelleProm == 2) {
                    economie += pu * pourcentage * (comporter.getQtePP() / 2);
//                    System.out.println("libelleProm == 2: " + economie);
                } else if (libelleProm == 3) {
                    economie += pu * pourcentage * (comporter.getQtePP() / 3);
//                    System.out.println("libelleProm == 3: " + economie);
                }
            } catch (NullPointerException npe) {
                economie += 0;
            }
        }
        return economie;
    }
    public static float calculerEconomiePromotionClientUnProd(int idProd) {

        float economie = 0.00f;
        int libelleProm = 0;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                Transaction t = session.beginTransaction();
                
                Produit p = session.get(Produit.class, idProd);
                libelleProm = p.getProm().getLibelleProm();
                float pu = p.getPrixUnitaireP();
                float pourcentage = p.getProm().getPourcentageProm();
                if (libelleProm == 0) {
                    economie = 0;
                } else if (libelleProm == 1) {
                    economie = pu * pourcentage;
//                    System.out.println("libelleProm == 1: " + economie);
                } else if (libelleProm == 2) {
                    economie = pu - (pu * (1-pourcentage)+ pu)/2 ;
//                    System.out.println("libelleProm == 2: " + economie);
                } else if (libelleProm == 3) {
                    economie = pu - (pu * (1-pourcentage)+ 2*pu)/3;
//                    System.out.println("libelleProm == 3: " + economie);
                }
            } catch (NullPointerException npe) {
                 economie = 0.00f;
            }
        
        return economie;
    }
    /*----- Chercher le prix bloqué par les points difilite d'un client pour sa commande-----*/
    public static int chercherPointfideliteUtilisableClient(int idCli) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Client c = session.get(Client.class, idCli);
            // Calculer le prixTotal de la commande
            float prixTotal = 0;
            List<Comporter> liste = new ArrayList();
            for (Produit p : c.getPanier().getComportements().keySet()) {
                liste.add(c.getPanier().getComportements().get(p));
            }
            for (miage.metier.Comporter comporter : liste) {
                prixTotal += comporter.getQtePP() * comporter.getProduits().getPrixUnitaireP();
            }
            // Afficher les points fidelites à consumer
            int prixDebloque = 0;
            if (c.getPointCli() / 10 < prixTotal) {
                prixDebloque = c.getPointCli() / 10;
            } else {
                prixDebloque = (int) Math.floor(prixTotal);
            }
            return prixDebloque;
        }
    }
    /*----- Chercher les produits dans une commande d'un client-----*/
    public static List<LigneCommande> chercherCommandeClient(int idCli, int idCmd) { //Client client
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Commande cmd = session.get(Commande.class, idCmd);
            Map<Produit, LigneCommande> m = cmd.getLignecommandes();

            List<LigneCommande> listeRes = new ArrayList();
            for (Produit p : m.keySet()) {
                listeRes.add(m.get(p));
                //System.out.println(m.get(p).getProduits().getIdP());
            }
            //System.out.println(listeRes.size());
            return listeRes;
        }
    }
    public static void insertListeCoursesClient(int idCli, String lib) {
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            ListeCourse l = new ListeCourse(lib,c);
//            Set<ListeCourse> lstC=c.getListecourses();
//            lstC.add(l);
            session.save(l);
            t.commit(); 
        }
    }
    public static List<ListeCourse> chercherListeCourseClient(int idCli) {
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            Set<ListeCourse> lstC=c.getListecourses();
            List<ListeCourse> lstCourse=new ArrayList<>();
            for(ListeCourse lc:lstC){
                //System.out.println(lc.getLibelleListe());
                lstCourse.add(lc);
            }
            return lstCourse;
        }
    }
    
    public static List<Ingredient> obtenirIngredient() {
        List<Ingredient> lstIng=new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            List<Ingredient> liste = session.createQuery("from Ingredient").list();
            for (int i = 0; i < liste.size(); i++) {
                Ingredient Ing = session.get(Ingredient.class, liste.get(i).getIdIng());
                //System.out.println("Ingredient: " + Ing.getIdIng());
                lstIng.add(Ing);
            }
        }
         return lstIng;
    }
        public static List<Ingredient> obtenirPostitListeCourse(int idListe) {
        /*----- Ouverture de la session -----*/
        List<Ingredient> lstIng = new ArrayList<>();
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            ListeCourse lc = session.get(ListeCourse.class, idListe);
            Map<Ingredient, Composer> m = lc.getComposers();
            for (Ingredient ing : m.keySet()) {
                System.out.println(ing.getLibelleIng());
                lstIng.add(ing);
            }
        }
        return lstIng;
    }
        public static String inserPostitListeCourses(int idList, int idInt) {
        String res = "";
        List<Ingredient> lstIng = obtenirPostitListeCourse(idList);
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Ingredient ing = session.get(Ingredient.class, idInt);
            if (lstIng.contains(ing)) {
                res = "existe";
            } else {
                Composer com = new Composer(new ComposerId(idInt, idList));
                session.save(com);              
                t.commit();
                res="success";
            }
        }
        return res;
    }
        
    public static List<Produit> obtenirProduitListeCourse(int idListe) {
        /*----- Ouverture de la session -----*/
        List<Produit> lstP = new ArrayList<>();
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            ListeCourse lc = session.get(ListeCourse.class, idListe);
            Map<Produit, Concerner> m = lc.getConcerner();
            for (Produit p : m.keySet()) {
                System.out.println(p.getLibelleP());
                lstP.add(p);
            }
        }
        return lstP;
    }
    
     /*----- Chercher les produits proposés pour un post-it -----*/
    public static List<Produit> chercherProduitProposePostit(int idCli, int idListe, int idIng) {
        List<Produit> lstPPropose = new ArrayList<>();
        //Liste de produits qui sont déjà existés dans cette liste de courses
        List<Produit> lstPListeCourses = obtenirProduitListeCourse(idListe);
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            ListeCourse lc = session.get(ListeCourse.class, idListe);
            Ingredient ing = session.get(Ingredient.class, idIng);
            //tous les produits contenant cet ingrédient
            Set<Produit> lstPIng = ing.getProduits();
            //proposer premièrement les produits préférés par le client
            for (Produit produitPref : c.getPreferences().keySet()) {
                if (produitPref.getIngredient().getIdIng() == idIng) {
                    lstPPropose.add(produitPref);
                }
            }
            //puis proposer les produits qui ne sont pas dans la liste préférée
            if (lstPPropose.size() > 0) {
                for (Produit p : lstPIng) {
                    if (!lstPPropose.contains(p)) {
                        lstPPropose.add(p);
                    }
                }
            } else {
                for (Produit p : lstPIng) {
                    lstPPropose.add(p);
                }
            }
            for (Produit pro : lstPPropose) {
                System.out.println("Pinggredient: " + pro.getIdP());
            }
            //enfin, supprimer tous les produits qui sont déjà dans la liste de courses
            List<Produit> lstPSup = new ArrayList<>();
            for (Produit PListeCourse : lstPListeCourses) {
                for (Produit prod : lstPPropose) {
                    if (prod.getIdP() == PListeCourse.getIdP()) {
                        lstPSup.add(prod);
                        //System.out.println("Remove: "+prod.getIdP());
                    }
                }
            }
            if (lstPSup.size() > 0) {
                for (int i = 0; i < lstPSup.size(); i++) {
                    lstPPropose.remove(lstPSup.get(i));
                }
            }
        }       
        for(int j=0;j<lstPPropose.size();j++){
            System.out.println("Produit: "+lstPPropose.get(j).getIdP());
        }
        return lstPPropose;
    }
    
    public static void inserProduitListeCourses(int idList, int idP) {
        try ( Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Produit p = session.get(Produit.class, idP);
            Concerner con = new Concerner(new ConcernerId(idP, idList));
            session.save(con);
            t.commit();
        }
    }
      
    //-----------------------------------------------   
    public static Magasin chercherMagasin(int idMag) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Magasin mag = session.get(Magasin.class, idMag);
            return mag;
        }
    }
    
       /*----- Chercher les creneaux et le nombre de places disponibles dans un magasin-----*/
    public static List<Disponibilite> chercherCreneaux(int idMag, String dateR) throws ParseException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        Magasin mag = session.get(Magasin.class, idMag);

        List<Disponibilite> liste = new ArrayList();
        for (Disponibilite disponibilite : mag.getCreneaux().values()) {
            Date DateCren = disponibilite.getDateCren();
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatDate.format(DateCren);
            if (date.equals(dateR)) {
                liste.add(disponibilite);
            }
        }
        return liste;
    }
    
    /*----- Calculer le nouveau prix d'un produit dans le panier d'un client-----*/
    public static double calculerEconomiePromProduitPanierClient(int idCli, int idP) {
        double qtePan = 0;
        double libelleProm = 0;
        double PUApresProm = 0;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            Produit p = session.get(Produit.class, idP);

            qtePan = c.getPanier().getComportements().get(p).getQtePP();
            libelleProm = p.getProm().getLibelleProm();
            t.commit();
            if (libelleProm > 0) {
                PUApresProm = ((p.getPrixUnitaireP()-calculerEconomiePromotionClientUnProd(idP)) *libelleProm* (int)(qtePan / libelleProm) + p.getPrixUnitaireP() * (int)(qtePan % libelleProm)) / qtePan; //??
            } else if (libelleProm == 0) {
                PUApresProm = p.getPrixUnitaireP();
            }
            return PUApresProm;
        }
    }
    
    /*----- Comparer la quantite d'un produit dans le panier et le stokage de magasin -----*/
    public static Map comparerQtePanierQteStockClient(int idCli, int idP, int idMag) {
        Map res = new HashMap();
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            Produit p = session.get(Produit.class, idP);
            Magasin mag = session.get(Magasin.class, idMag);
            int qtePan = 0;
            int qteStock = 0;

            qtePan = c.getPanier().getComportements().get(p).getQtePP();
            qteStock = mag.getStockages().get(p).getQteSP();

            System.out.println(qteStock);

            if (qteStock >= qtePan) {
                res.put(p, 1);
            } else {
                res.put(p, 0);
            }
            System.out.println(res.get(p));

        } catch (NullPointerException npe) {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                /*----- Ouverture d'une transaction -----*/
                Transaction t = session.beginTransaction();
                Produit p = session.get(Produit.class, idP);
                res.put(p, 0);
            }
        }
        return res;
    }
    
    /*----- Chercher les produits de remplacement pour un client -----*/
    public static List<Produit> chercherProduitRemplacementClient(int idCli, int idP, int idMag) {
        List<Produit> produitRemplacement = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            Produit p = session.get(Produit.class, idP);
            Magasin m = session.get(Magasin.class, idMag);
            
            // Chercher des produits de remplacement parmi les produits preferes
            for (Produit produitPref : c.getPreferences().keySet()) {
                if (produitPref.getIngredient() == p.getIngredient()) { // meme ingredient
                    if (produitPref.getIdP() != idP) {     // pas de prod de remplacement
                        if (m.getStockages().get(produitPref).getQteSP() > 0) { //assez de stackage
                            
                            int i = 0;
                            List<Produit> listeProdPan = new ArrayList<>();
                            for (Produit prodPan : c.getPanier().getComportements().keySet()) { // pas dans le panier                            
                                if (prodPan.getIdP() == produitPref.getIdP()) {
                                    listeProdPan.add(produitPref);
                                }
                            }
                            if (listeProdPan.isEmpty()) {
                                produitRemplacement.add(produitPref);
                            }
                        }
                    }
                }
            }

            // Chercher des produits de remplacement parmi tous les produits
            if (produitRemplacement.size() >= 0) {
                List<Produit> listeTousProduit = session.createQuery("from Produit").list();
                for (Produit produit : listeTousProduit) {
                    if (produitRemplacement.contains(produit) == false) { // pas dans la prefeference
                        if (produit.getIngredient() == p.getIngredient()) {  // meme ingrediant
                            if (produit.getIdP() != idP) { // pas de meme produit
                                if (m.getStockages().get(produit).getQteSP() > 0) { //assez de stackage
                                    List<Produit> prodP = new ArrayList<>();
                                    for (Produit prodPan : c.getPanier().getComportements().keySet()) { // pas dans le panier                                       
                                        if (prodPan.getIdP() == produit.getIdP()) {
                                            prodP.add(prodPan);
                                        }
                                    }
                                    if (prodP.isEmpty()) {
                                        produitRemplacement.add(produit);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return produitRemplacement;
    }
    /*----- Modifier la quantite d'un produit dans le panier d'un client-----*/
    public static void modifierQteProduitPanierClient(int idCli, int idP, int qte) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Client c = session.get(Client.class, idCli);
            Produit p = session.get(Produit.class, idP);

            c.getPanier().getComportements().get(p).setQtePP(qte);
            session.save(c);
            t.commit();
        }
    }

    
    /**
     * Programme de test.
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, SQLException, ClassNotFoundException, LazyInitializationException {
        //System.out.println(TestHibernate.chercherCinqProduits());
        //TestHibernate.loadPhotos();
        //TestHibernate.obtenirMagasins();
        //chercherCinqProduits();
        //insertProduitPanier();
        //TestHibernate.afficherLabels("");
        //TestHibernate.obtenirRecettes();
        //TestHibernate.chercherIngRecette(1);
        Map res = new HashMap();
        for (miage.metier.Comporter comporter : TestHibernate.chercherPanierClient(1)) {        
            res=comparerQtePanierQteStockClient(1,comporter.getProduits().getIdP(),1);
            System.out.println(res.get(comporter.getProduits()));
        }
        /*----- Exit -----*/
//        System.exit(0);
    }

}
/*----- Fin de la classe TestHibernate -----*/
