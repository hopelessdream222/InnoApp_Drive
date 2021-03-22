package miage.metier;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity (name="Stocker")
public class Stocker implements Serializable{
    // Proprietes
    @EmbeddedId
    private StockerId stockerId;
    private int qteSP;
    
    @ManyToOne
    @JoinColumn(name="idP",insertable=false,updatable=false)
    private Produit produits;
    
    @ManyToOne
    @JoinColumn(name="idMag",insertable=false,updatable=false)
    private Magasin magasins;

    public Stocker() {
    }

    public Stocker(int qteSP, Produit produits, Magasin magasins) {
        this.qteSP = qteSP;
        this.produits = produits;
        this.magasins = magasins;
    }

    @Override
    public String toString() {
        return "Stocker{" + "stockerId=" + stockerId + ", qteSP=" + qteSP + ", produits=" + produits + ", magasins=" + magasins + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.stockerId);
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
        final Stocker other = (Stocker) obj;
        if (!Objects.equals(this.stockerId, other.stockerId)) {
            return false;
        }
        return true;
    }
    
    
  
}

