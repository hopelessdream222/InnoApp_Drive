package miage.metier;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;


@Entity
//@SuppressWarnings("PersistencesUnitPresent")
public class Fournir implements Serializable{
          
    @EmbeddedId
    private FournirId codeF;
    private int qteFP;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateF;
     
    @ManyToOne
    @JoinColumn(name="idP",insertable=false,updatable=false)
    private Produit produits;
    
    @ManyToOne
    @JoinColumn(name="idMag",insertable=false,updatable=false)
    private Magasin magasins;
    
    @ManyToOne
    @JoinColumn(name="idF",insertable=false,updatable=false)
    private Fournisseur fournisseurs;

    public Fournir() {
    }

    public Fournir(FournirId codeF, int qteFP, Date dateF) {
        this.codeF = codeF;
        this.qteFP = qteFP;
        this.dateF = dateF;
    }

    public FournirId getCodeF() {
        return codeF;
    }

    public void setCodeF(FournirId codeF) {
        this.codeF = codeF;
    }

    public int getQteFP() {
        return qteFP;
    }

    public void setQteFP(int qteFP) {
        this.qteFP = qteFP;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.codeF);
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
        final Fournir other = (Fournir) obj;
        if (!Objects.equals(this.codeF, other.codeF)) {
            return false;
        }
        return true;
    }


    
}
