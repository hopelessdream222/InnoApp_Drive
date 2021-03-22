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

  
}

