/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ccc
 */
@Embeddable
public class ConcernerId implements Serializable{
    // proprietes
    @Column(name = "idP")
    private int idP;
    @Column(name = "idListe")
    private int idListe;

    public ConcernerId() {
    }

    public ConcernerId(int idP, int idListe) {
        this.idP = idP;
        this.idListe = idListe;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    @Override
    public String toString() {
        return "ConcernerId{" + "idP=" + idP + ", idListe=" + idListe + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.idP;
        hash = 41 * hash + this.idListe;
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
        final ConcernerId other = (ConcernerId) obj;
        if (this.idP != other.idP) {
            return false;
        }
        if (this.idListe != other.idListe) {
            return false;
        }
        return true;
    }
    
    

}
