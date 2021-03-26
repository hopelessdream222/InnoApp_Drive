/*
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
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity
public class Promotion {
    // proprietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idProm")    
    private int idProm;
    private String libelleProm;
    private int pourcentageProm;
    
    // relation <Promouvoir>
    @OneToMany(mappedBy = "Prom", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Produit> produits = new HashSet<>(0);

    public Promotion() {
    }

    public Promotion(String libelleProm) {
        this.libelleProm = libelleProm;
    }

    public int getIdProm() {
        return idProm;
    }

    public void setIdProm(int idProm) {
        this.idProm = idProm;
    }

    public String getLibelleProm() {
        return libelleProm;
    }

    public void setLibelleProm(String libelleProm) {
        this.libelleProm = libelleProm;
    }

    public int getPourcentageProm() {
        return pourcentageProm;
    }

    public void setPourcentageProm(int pourcentageProm) {
        this.pourcentageProm = pourcentageProm;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Promotion{" + "idProm=" + idProm + ", libelleProm=" + libelleProm + ", pourcentageProm=" + pourcentageProm + ", produits=" + produits + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idProm;
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
        final Promotion other = (Promotion) obj;
        if (this.idProm != other.idProm) {
            return false;
        }
        return true;
    }

    
    
}
