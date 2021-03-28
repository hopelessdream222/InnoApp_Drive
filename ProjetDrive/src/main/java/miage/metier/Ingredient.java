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

/**
 *
 * @author ccc
 */
@Entity (name="Ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPIng")    
    private int idIng;
    private String libelleIng;
    private String UnitedMesureIng;
    
    
    
   // Relation <Composer>
    @OneToMany(mappedBy = "ingredient", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Produit> produits = new HashSet<>(0);
    
    
    // Relation <Necessiter>
    @OneToMany(mappedBy = "ingredient",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "IdRect")
    private Map<Recette, Necessiter> necessiters=new HashMap(0);

    public Ingredient() {
    } 
    
    public Ingredient(String libelleIng, String UnitedMesureIng) {
        this.libelleIng = libelleIng;
        this.UnitedMesureIng = UnitedMesureIng;
    }

    public int getIdIng() {
        return idIng;
    }

    public void setIdIng(int idIng) {
        this.idIng = idIng;
    }

    public String getLibelleIng() {
        return libelleIng;
    }

    public void setLibelleIng(String libelleIng) {
        this.libelleIng = libelleIng;
    }

    public String getUnitedMesureIng() {
        return UnitedMesureIng;
    }

    public void setUnitedMesureIng(String UnitedMesureIng) {
        this.UnitedMesureIng = UnitedMesureIng;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "idIng=" + idIng + ", libelleIng=" + libelleIng + ", UnitedMesureIng=" + UnitedMesureIng + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idIng;
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
        final Ingredient other = (Ingredient) obj;
        if (this.idIng != other.idIng) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
