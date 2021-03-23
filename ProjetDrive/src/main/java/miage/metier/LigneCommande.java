/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ccc
 */
@Entity (name="LigneCommande")
public class LigneCommande implements Serializable{
    @EmbeddedId
    private LigneCommandeId ligneId;
    private int qteCP;

    // Relation
    @ManyToOne
    @JoinColumn(name = "idCmd", insertable = false,updatable = false)
    private Commande commandes;
    
    @ManyToOne
    @JoinColumn(name = "idP", insertable = false,updatable = false)    
    private Produit produits;

    public LigneCommande() {
    }

    public LigneCommande(int qteCP, Commande commandes, Produit produits) {
        this.qteCP = qteCP;
        this.commandes = commandes;
        this.produits = produits;
    }

    public LigneCommandeId getLigneId() {
        return ligneId;
    }

    public void setLigneId(LigneCommandeId ligneId) {
        this.ligneId = ligneId;
    }

    public int getQteCP() {
        return qteCP;
    }

    public void setQteCP(int qteCP) {
        this.qteCP = qteCP;
    }

    public Commande getCommandes() {
        return commandes;
    }

    public void setCommandes(Commande commandes) {
        this.commandes = commandes;
    }

    public Produit getProduits() {
        return produits;
    }

    public void setProduits(Produit produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "LigneCommande{" + "ligneId=" + ligneId + ", qteCP=" + qteCP + ", commandes=" + commandes + ", produits=" + produits + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.ligneId);
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
        final LigneCommande other = (LigneCommande) obj;
        if (!Objects.equals(this.ligneId, other.ligneId)) {
            return false;
        }
        return true;
    }
    
    
}
