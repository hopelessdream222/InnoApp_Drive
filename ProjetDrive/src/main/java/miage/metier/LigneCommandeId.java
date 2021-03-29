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
public class LigneCommandeId implements Serializable{
    @Column(name="idP")    
    private int idP;
    @Column(name="idCmd")  
    private int idCmd;

    public LigneCommandeId() {
    }

    public LigneCommandeId(int idP, int idCmd) {
        this.idP = idP;
        this.idCmd = idCmd;
    }
    

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    @Override
    public String toString() {
        return "LigneCommandeId{" + "idP=" + idP + ", idCmd=" + idCmd + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idP;
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
        final LigneCommandeId other = (LigneCommandeId) obj;
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    
}
