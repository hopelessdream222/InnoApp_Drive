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
@Entity (name="Preferer")
public class Preferer {
    // Proprietes
    @EmbeddedId  
    private PrefererId prefererid;
    
    @ManyToOne
    @JoinColumn(name="idCli",insertable=false,updatable=false)
    private Client client;
    
    @ManyToOne
    @JoinColumn(name="idP",insertable=false,updatable=false)
    private Produit produit;

    public Preferer() {
    }
    
    public Preferer(PrefererId prefererid, Client client, Produit produit) {
        this.prefererid = prefererid;
        this.client = client;
        this.produit = produit;
    }

    public PrefererId getPrefererid() {
        return prefererid;
    }

    public void setPrefererid(PrefererId prefererid) {
        this.prefererid = prefererid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Preferer{" + "prefererid=" + prefererid + ", client=" + client + ", produit=" + produit + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.prefererid);
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
        final Preferer other = (Preferer) obj;
        if (!Objects.equals(this.prefererid, other.prefererid)) {
            return false;
        }
        return true;
    }     
    
}
