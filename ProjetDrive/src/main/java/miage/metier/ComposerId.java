/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

public class ComposerId implements Serializable{
    // proprietes
    @Column(name = "idIng")
    private int idIng;
    @Column(name = "idListe")
    private int idListe;

    public ComposerId() {
    }

    public ComposerId(int idIng, int idListe) {
        this.idIng = idIng;
        this.idListe = idListe;
    }

    public int getIdIng() {
        return idIng;
    }

    public void setIdIng(int idIng) {
        this.idIng = idIng;
    }

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.idIng;
        hash = 43 * hash + this.idListe;
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
        final ComposerId other = (ComposerId) obj;
        if (this.idIng != other.idIng) {
            return false;
        }
        if (this.idListe != other.idListe) {
            return false;
        }
        return true;
    }
    
    

}
