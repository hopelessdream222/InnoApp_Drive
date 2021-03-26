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
public class NecessiterId implements Serializable{
    // proprietes
    @Column(name="idIng")     
    private int idP;
    @Column(name="idRect")  
    private int idRect;

    public NecessiterId() {
    }
    
    public NecessiterId(int idP, int idRect) {
        this.idP = idP;
        this.idRect = idRect;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdRect() {
        return idRect;
    }

    public void setIdRect(int idRect) {
        this.idRect = idRect;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idP;
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
        final NecessiterId other = (NecessiterId) obj;
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    
    
    
    
}
