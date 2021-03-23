/*  //prix litre/kolo+label+produit fournisseur
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity (name="Categorie")
public class Categorie {
    // proprietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCat")
    private int idCat;
    private String libelleCat;
    
    // relation <Situer>
    @ManyToOne(fetch =FetchType.EAGER)  // comparaison avec LAZY
    @JoinColumn(name = "idRay")
    private Rayon rayonCat;
    
    // relation <Appartenir>
    @OneToMany(mappedBy = "categorieP", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Produit> produits = new HashSet<>(0);

    public Categorie() {
    }
    
    public Categorie(String libelleCat, Rayon rayonCat) {
        this.libelleCat = libelleCat;
        this.rayonCat = rayonCat;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getLibelleCat() {
        return libelleCat;
    }

    public void setLibelleCat(String libelleCat) {
        this.libelleCat = libelleCat;
    }

    public Rayon getRayonCat() {
        return rayonCat;
    }

    public void setRayonCat(Rayon rayonCat) {
        this.rayonCat = rayonCat;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Categorie{" + "idCat=" + idCat + ", libelleCat=" + libelleCat + ", rayonCat=" + rayonCat + ", produits=" + produits + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.idCat;
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
        final Categorie other = (Categorie) obj;
        if (this.idCat != other.idCat) {
            return false;
        }
        return true;
    }
    
}
