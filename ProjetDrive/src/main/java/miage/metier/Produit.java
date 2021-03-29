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
    private String tailleReferenceP;
    private String compositionP;

    
    // Relation <Appartenir>
    @ManyToOne(fetch =FetchType.EAGER) 
    @JoinColumn(name = "idCat")
    private Categorie categorieP;
    
    // Relation <LigneCommande>
    @OneToMany(mappedBy = "produits",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idCmd")
    private Map<Commande, LigneCommande> ligneCommandes=new HashMap(0);
    
    // Relation <Stocker>
    @OneToMany(mappedBy = "produits",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idMag")
    private Map<Magasin, Stocker> stockages=new HashMap(0);
    
    // Relation <Comporter>
    @OneToMany(mappedBy = "produits",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idPan")
    private Map<Panier, Comporter> comportements=new HashMap(0);
    
    // Relation <Promouvoir>
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "idProm")
    private Promotion Prom;
    
    // Relation <Composer>
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "idIng")
    private Ingredient ingredient;

    public Produit() {
    }
    

    public Produit(String libelleP, float prixUnitaireP, float prixKGP, String nutriScoreP, Blob photoP, String labelP, String formatP, String conditionnementP, Categorie categorieP) {
        this.libelleP = libelleP;
        this.prixUnitaireP = prixUnitaireP;
        this.prixKGP = prixKGP;
        this.nutriScoreP = nutriScoreP;
        this.photoP = photoP;
        this.labelP = labelP;
        this.formatP = formatP;
        this.conditionnementP = conditionnementP;
        this.categorieP = categorieP;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getLibelleP() {
        return libelleP;
    }

    public void setLibelleP(String libelleP) {
        this.libelleP = libelleP;
    }

    public float getPrixUnitaireP() {
        return prixUnitaireP;
    }

    public void setPrixUnitaireP(float prixUnitaireP) {
        this.prixUnitaireP = prixUnitaireP;
    }

    public float getPrixKGP() {
        return prixKGP;
    }

    public void setPrixKGP(float prixKGP) {
        this.prixKGP = prixKGP;
    }

    public String getNutriScoreP() {
        return nutriScoreP;
    }

    public void setNutriScoreP(String nutriScoreP) {
        this.nutriScoreP = nutriScoreP;
    }

    public Blob getPhotoP() {
        return photoP;
    }

    public void setPhotoP(Blob photoP) {
        this.photoP = photoP;
    }

    public String getLabelP() {
        return labelP;
    }

    public void setLabelP(String labelP) {
        this.labelP = labelP;
    }

    public String getFormatP() {
        return formatP;
    }

    public void setFormatP(String formatP) {
        this.formatP = formatP;
    }

    public String getConditionnementP() {
        return conditionnementP;
    }

    public void setConditionnementP(String conditionnementP) {
        this.conditionnementP = conditionnementP;
    }

    public Categorie getCategorieP() {
        return categorieP;
    }

    public void setCategorieP(Categorie categorieP) {
        this.categorieP = categorieP;
    }

    public Map<Commande, LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(Map<Commande, LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    public Map<Magasin, Stocker> getStockages() {
        return stockages;
    }

    public void setStockages(Map<Magasin, Stocker> stockages) {
        this.stockages = stockages;
    }

    public Map<Panier, Comporter> getComportements() {
        return comportements;
    }

    public void setComportements(Map<Panier, Comporter> comportements) {
        this.comportements = comportements;
    }

    public String getTailleReferenceP() {
        return tailleReferenceP;
    }

    public void setTailleReferenceP(String tailleReferenceP) {
        this.tailleReferenceP = tailleReferenceP;
    }

    public String getCompositionP() {
        return compositionP;
    }

    public void setCompositionP(String compositionP) {
        this.compositionP = compositionP;
    }

    public Promotion getProm() {
        return Prom;
    }

    public void setProm(Promotion Prom) {
        this.Prom = Prom;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "libelle:" + libelleP + ", prix Unitaire:" + prixUnitaireP + ", prix/kg:" + prixKGP + ", nutri Score:" + nutriScoreP;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idP;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    
    
       
    
}
