/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author ccc
 */
@Entity
public class Creneau implements Serializable{
    // proprietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCren")    
    private int idCren;
    private String dureeCren;
    
   // Relation <Disponibilite>
    @OneToMany(mappedBy = "creneaux",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idMag")
    private Map<Magasin, Disponibilite> magasins=new HashMap(0);
    
    // Relation <ChoisirCren>
    @OneToMany(mappedBy = "creneauCmd", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commande> commandes = new HashSet<>(0);

    public Creneau() {
    }

    public Creneau(String dureeCren) {
        this.dureeCren = dureeCren;
    }
    
    

    public int getIdCren() {
        return idCren;
    }

    public void setIdCren(int idCren) {
        this.idCren = idCren;
    }

    public String getDureeCren() {
        return dureeCren;
    }

    public void setDureeCren(String dureeCren) {
        this.dureeCren = dureeCren;
    }

    public Map<Magasin, Disponibilite> getMagasins() {
        return magasins;
    }

    public void setMagasins(Map<Magasin, Disponibilite> magasins) {
        this.magasins = magasins;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }
    
    @Override
    public String toString() {
        return "Creneau{" + "idCren=" + idCren + ", dureeCren=" + dureeCren + ", magasins=" + magasins + ", commandes=" + commandes + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.idCren;
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
        final Creneau other = (Creneau) obj;
        if (this.idCren != other.idCren) {
            return false;
        }
        return true;
    }
    
    
    
    

    
}
