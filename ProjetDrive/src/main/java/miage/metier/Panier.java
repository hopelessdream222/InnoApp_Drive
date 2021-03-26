/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ccc
 */
@Entity(name="Panier")
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPan")    
    private int idPan;

    // relation <Posseder>
    @OneToOne(mappedBy = "panier")
    private Client clientPan;
    
    // Relation <Comporter>
    @OneToMany(mappedBy = "paniers",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idP")
    private Map<Produit, Comporter> comportements=new HashMap(0);

    public Panier() {
    }

    public Panier(Client clientPan) {
        this.clientPan = clientPan;
    }

    public int getIdPan() {
        return idPan;
    }

    public void setIdPan(int idPan) {
        this.idPan = idPan;
    }

    public Client getClientPan() {
        return clientPan;
    }

    public void setClientPan(Client clientPan) {
        this.clientPan = clientPan;
    }

    public Map<Produit, Comporter> getComportements() {
        return comportements;
    }

    public void setComportements(Map<Produit, Comporter> comportements) {
        this.comportements = comportements;
    }

    @Override
    public String toString() {
        return "Panier{" + "idPan=" + idPan + ", clientPan=" + clientPan + ", comportements=" + comportements + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.idPan;
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
        final Panier other = (Panier) obj;
        if (this.idPan != other.idPan) {
            return false;
        }
        return true;
    }
    
    
    
    

    
    
    
    
    
}
