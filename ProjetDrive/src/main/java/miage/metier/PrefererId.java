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
public class PrefererId  implements Serializable{
    // proprietes
    @Column(name="idCli") 
    private int idCli;
    @Column(name="idP") 
    private int idP;

    public PrefererId() {
    }
    public PrefererId(int idCli, int idP) {
        this.idCli = idCli;
        this.idP = idP;

    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    @Override
    public String toString() {
        return "PrefererId{" + "idCli=" + idCli + ", idP=" + idP + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idCli;
        hash = 29 * hash + this.idP;
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
        final PrefererId other = (PrefererId) obj;
        if (this.idCli != other.idCli) {
            return false;
        }
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
