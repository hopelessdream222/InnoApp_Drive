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
public class ComporterId implements Serializable{
        // proprietes
    @Column(name="idP")     
    private int idP;
    @Column(name="idPan")  
    private int idPan;
    // constructeur

    public ComporterId() {
    }

    public ComporterId(int idP, int idPan) {
        this.idP = idP;
        this.idPan = idPan;
    }
    
    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdPan() {
        return idPan;
    }

    public void setIdPan(int idPan) {
        this.idPan = idPan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ComporterId{idP=").append(idP);
        sb.append(", idPan=").append(idPan);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ComporterId other = (ComporterId) obj;
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    
    
}
