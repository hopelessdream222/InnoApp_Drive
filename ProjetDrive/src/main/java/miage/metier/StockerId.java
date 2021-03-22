package miage.metier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StockerId  implements Serializable{
    // Proprietes
    @Column(name="idP")     
    private int idP;
    @Column(name="idMag")  
    private int idMag;
    //constructeur

    public StockerId() {
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }
    @Override
    public String toString() {
        return "StockerId{" + "idP=" + idP + ", idMag=" + idMag + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idP;
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
        final StockerId other = (StockerId) obj;
        if (this.idP != other.idP) {
            return false;
        }
        return true;
    }
    

}
