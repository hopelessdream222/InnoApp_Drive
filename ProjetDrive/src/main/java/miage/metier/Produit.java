/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */

@Entity (name="Produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idP")
    private int idP;
    private String libelleP;
    private float prixUnitaireP;
    private float prixKGP;
    private String nutriScoreP;
    private Blob photoP;
    private String labelP;
    private String formatP;
    private String conditionnementP;

    
    // relation <Appartenir>
    @ManyToOne(fetch =FetchType.EAGER) 
    @JoinColumn(name = "idCat")
    private Categorie categorieP;
    
    // Relation <LigneCommande>
    @OneToMany(mappedBy = "produits",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "IdP")
    private Map<Commande, LigneCommande> ligneCommandes=new HashMap(0);
    
    // Relation <Stocker>
    @OneToMany(mappedBy = "produits",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "IdP")
    private Map<Magasin, Stocker> stockages=new HashMap(0);
    
    // Relation <Fournir>
    //@OneToMany(mappedBy = "produits",cascade=CascadeType.ALL)
    //@MapKeyJoinColumn(name = "IdP")
    private Map<Magasin, Map<Fournisseur,Fournir>> fournirs=new HashMap(0);
       
    
}
