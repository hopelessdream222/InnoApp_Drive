/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity(name="Recette")
public class Recette implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRect")        
    private int idRect;
    private String libelleRect;
    private String MoyenRect;

    public Recette() {
    }

    public Recette(String libelleRect) {
        this.libelleRect = libelleRect;
    }   
    
     
    // Relation <Necessiter>
    @OneToMany(mappedBy = "recette",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idIng")
    private Map<Ingredient, Necessiter> necessiters=new HashMap<Ingredient, Necessiter>(0);
    
    public int getIdRect() {
        return idRect;
    }

    public void setIdRect(int idRect) {
        this.idRect = idRect;
    }

    public String getLibelleRect() {
        return libelleRect;
    }

    public void setLibelleRect(String libelleRect) {
        this.libelleRect = libelleRect;
    }

    public Map<Ingredient, Necessiter> getNecessiters() {
        return necessiters;
    }

    public void setNecessiters(Map<Ingredient, Necessiter> necessiters) {
        this.necessiters = necessiters;
    }

    public String getMoyenRect() {
        return MoyenRect;
    }

    public void setMoyenRect(String MoyenRect) {
        this.MoyenRect = MoyenRect;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.idRect;
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
        final Recette other = (Recette) obj;
        if (this.idRect != other.idRect) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recette{" + "idRect=" + idRect + ", libelleRect=" + libelleRect + '}';
    }
    
}
