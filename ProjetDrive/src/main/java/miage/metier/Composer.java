/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity (name="Composer")
public class Composer implements Serializable{
    // Proprietes
    @EmbeddedId    
    private ComposerId ComposerId;

    public Composer() {
    }

    public Composer(ComposerId ComposerId) {
        this.ComposerId = ComposerId;
    }
    
    @ManyToOne
    @JoinColumn(name="idIng",insertable=false,updatable=false)
    private Ingredient ingredient;
    
    @ManyToOne
    @JoinColumn(name="idListe",insertable=false,updatable=false)
    private ListeCourse listeCourse;

    public ComposerId getComposerId() {
        return ComposerId;
    }

    public void setComposerId(ComposerId ComposerId) {
        this.ComposerId = ComposerId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public ListeCourse getListeCourse() {
        return listeCourse;
    }

    public void setListeCourse(ListeCourse listeCourse) {
        this.listeCourse = listeCourse;
    }

    @Override
    public String toString() {
        return "Composer{" + "ComposerId=" + ComposerId + ", ingredient=" + ingredient + ", listeCourse=" + listeCourse + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Composer other = (Composer) obj;
        if (!Objects.equals(this.ComposerId, other.ComposerId)) {
            return false;
        }
        return true;
    }   
    
}
