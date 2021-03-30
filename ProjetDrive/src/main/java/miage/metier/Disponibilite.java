/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author ccc
 */
@Entity
public class Disponibilite {
    // proprietes
    @EmbeddedId
    private DisponibiliteId dispoId;
    private int nbPlaceTotal;
    private int nbPlaceRest;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCren;

    
    @ManyToOne
    @JoinColumn(name="idMag",insertable=false,updatable=false)
    private Magasin magasins;
    
    @ManyToOne
    @JoinColumn(name="idCren",insertable=false,updatable=false)
    private Creneau creneaux;

    public Disponibilite() {
    }

    public Disponibilite(DisponibiliteId dispoId, int nbPlaceTotal, int nbPlaceRest, Date dateCren, Magasin magasins, Creneau creneaux) {
        this.dispoId = dispoId;
        this.nbPlaceTotal = nbPlaceTotal;
        this.nbPlaceRest = nbPlaceRest;
        this.dateCren = dateCren;
        this.magasins = magasins;
        this.creneaux = creneaux;
    }    
    
    public Date getDateCren() {
        return dateCren;
    }

    public void setDateCren(Date dateCren) {
        this.dateCren = dateCren;
    }
    
    public DisponibiliteId getDispoId() {
        return dispoId;
    }

    public void setDispoId(DisponibiliteId dispoId) {
        this.dispoId = dispoId;
    }

    public int getNbPlaceTotal() {
        return nbPlaceTotal;
    }

    public void setNbPlaceTotal(int nbPlaceTotal) {
        this.nbPlaceTotal = nbPlaceTotal;
    }

    public int getNbPlaceRest() {
        return nbPlaceRest;
    }

    public void setNbPlaceRest(int nbPlaceRest) {
        this.nbPlaceRest = nbPlaceRest;
    }

    public Magasin getMagasins() {
        return magasins;
    }

    public void setMagasins(Magasin magasins) {
        this.magasins = magasins;
    }

    public Creneau getCreneaux() {
        return creneaux;
    }

    public void setCreneaux(Creneau creneaux) {
        this.creneaux = creneaux;
    }

    @Override
    public String toString() {
        return "Disponibilite{" + "dispoId=" + dispoId + ", nbPlaceTotal=" + nbPlaceTotal + ", nbPlaceRest=" + nbPlaceRest + ", magasins=" + magasins + ", creneaux=" + creneaux + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.dispoId);
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
        final Disponibilite other = (Disponibilite) obj;
        if (!Objects.equals(this.dispoId, other.dispoId)) {
            return false;
        }
        return true;
    }
    

    
    

}