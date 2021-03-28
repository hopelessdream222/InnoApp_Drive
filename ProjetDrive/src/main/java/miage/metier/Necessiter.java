/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ccc
 */
@Entity (name="Necessiter")
public class Necessiter {
    // Proprietes
    @EmbeddedId    
    private NecessiterId necessiter;
    private int qteRI;

    public Necessiter(NecessiterId necessiter, int qteRI) {
        this.necessiter = necessiter;
        this.qteRI = qteRI;
    }
    
    @ManyToOne
    @JoinColumn(name="idIng",insertable=false,updatable=false)
    private Ingredient ingredient;
    
    @ManyToOne
    @JoinColumn(name="idRect",insertable=false,updatable=false)
    private Recette recette;

    public NecessiterId getNecessiter() {
        return necessiter;
    }

    public void setNecessiter(NecessiterId necessiter) {
        this.necessiter = necessiter;
    }

    public int getQteRI() {
        return qteRI;
    }

    public void setQteRI(int qteRI) {
        this.qteRI = qteRI;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.necessiter);
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
        final Necessiter other = (Necessiter) obj;
        if (!Objects.equals(this.necessiter, other.necessiter)) {
            return false;
        }
        return true;
    }
    
    
}
