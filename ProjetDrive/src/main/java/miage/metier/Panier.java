/*-----
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
@Entity (name="Panier")
public class Panier {
    @EmbeddedId
    private PanierId panierId;
    private int qtePP;
    
    // Relation
    @ManyToOne
    @JoinColumn(name = "idCli", insertable = false,updatable = false)
    private Client clients;
    
    @ManyToOne
    @JoinColumn(name = "idP", insertable = false,updatable = false)    
    private Produit produits;

    public Panier() {
    }
    

    public Panier(int qtePP, Client clients, Produit produits) {
        this.qtePP = qtePP;
        this.clients = clients;
        this.produits = produits;
    }

    public PanierId getPanierId() {
        return panierId;
    }

    public void setPanierId(PanierId panierId) {
        this.panierId = panierId;
    }

    public int getQtePP() {
        return qtePP;
    }

    public void setQtePP(int qtePP) {
        this.qtePP = qtePP;
    }

    public Client getClients() {
        return clients;
    }

    public void setClients(Client clients) {
        this.clients = clients;
    }

    public Produit getProduits() {
        return produits;
    }

    public void setProduits(Produit produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Panier{" + "panierId=" + panierId + ", qtePP=" + qtePP + ", clients=" + clients + ", produits=" + produits + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.panierId);
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
        final Panier other = (Panier) obj;
        if (!Objects.equals(this.panierId, other.panierId)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
}
