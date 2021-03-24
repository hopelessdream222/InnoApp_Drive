/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ccc
 */
@Entity (name="Comporter")
public class Comporter {
    // Proprietes
    @EmbeddedId
    private ComporterId comporterId;
    private int qtePP;
    
    @ManyToOne
    @JoinColumn(name="idP",insertable=false,updatable=false)
    private Produit produits;
    
    @ManyToOne
    @JoinColumn(name="idPan",insertable=false,updatable=false)
    private Panier paniers;

    public Comporter() {
    }

    public Comporter(ComporterId comporterId, int qtePP, Produit produits, Panier paniers) {
        this.comporterId = comporterId;
        this.qtePP = qtePP;
        this.produits = produits;
        this.paniers = paniers;
    }
    
    
    


    public ComporterId getComporterId() {
        return comporterId;
    }

    public void setComporterId(ComporterId comporterId) {
        this.comporterId = comporterId;
    }

    public int getQtePP() {
        return qtePP;
    }

    public void setQtePP(int qtePP) {
        this.qtePP = qtePP;
    }

    public Produit getProduits() {
        return produits;
    }

    public void setProduits(Produit produits) {
        this.produits = produits;
    }

    public Panier getPaniers() {
        return paniers;
    }

    public void setPaniers(Panier paniers) {
        this.paniers = paniers;
    }

    @Override
    public String toString() {
        return "Comporter{" + "comporterId=" + comporterId + ", qtePP=" + qtePP + ", produits=" + produits + ", paniers=" + paniers + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.comporterId);
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
        final Comporter other = (Comporter) obj;
        if (!Objects.equals(this.comporterId, other.comporterId)) {
            return false;
        }
        return true;
    }
    
    
    
}
