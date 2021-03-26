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
public class DisponibiliteId implements Serializable{
    // Proprietes
    @Column(name="idCren")     
    private int idCren;
    @Column(name="idMag")  
    private int idMag;

    public DisponibiliteId() {
    }

    public DisponibiliteId(int idCren, int idMag) {
        this.idCren = idCren;
        this.idMag = idMag;
    }

    public int getIdCren() {
        return idCren;
    }

    public void setIdCren(int idCren) {
        this.idCren = idCren;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    @Override
    public String toString() {
        return "DisponibiliteId{" + "idCren=" + idCren + ", idMag=" + idMag + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idCren;
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
        final DisponibiliteId other = (DisponibiliteId) obj;
        if (this.idCren != other.idCren) {
            return false;
        }
        return true;
    }

}
