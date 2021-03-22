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
public class PanierId implements Serializable{
    @Column(name="idP") 
    private int idP;
    @Column(name="idCli")  
    private int idCli;

    public PanierId() {
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }

    @Override
    public String toString() {
        return "PanierId{" + "idP=" + idP + ", idCli=" + idCli + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.idP;
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
        final PanierId other = (PanierId) obj;
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    
    
    
}
