/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ccc
 */
@Entity
public class Disponibilite implements Serializable{
    // proprietes
    @EmbeddedId
    private DisponibiliteId dispoId;
    private int nbPlace;
    private Date dateCren;
    
    @ManyToOne
    @JoinColumn(name="idMag",insertable=false,updatable=false)
    private Magasin magasins;
    
    @ManyToOne
    @JoinColumn(name="idCren",insertable=false,updatable=false)
    private Creneau creneaux;

    public Disponibilite() {
    }

    public Disponibilite(DisponibiliteId dispoId, int nbPlace, Date dateCren, Magasin magasins, Creneau creneaux) {
        this.dispoId = dispoId;
        this.nbPlace = nbPlace;
        this.dateCren = dateCren;
        this.magasins = magasins;
        this.creneaux = creneaux;
    }

    public DisponibiliteId getDispoId() {
        return dispoId;
    }

    public void setDispoId(DisponibiliteId dispoId) {
        this.dispoId = dispoId;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public Date getDateCren() {
        return dateCren;
    }

    public void setDateCren(Date dateCren) {
        this.dateCren = dateCren;
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
        return "Disponibilite{" + "dispoId=" + dispoId + ", nbPlace=" + nbPlace + ", dateCren=" + dateCren + ", magasins=" + magasins + ", creneaux=" + creneaux + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.dispoId);
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
