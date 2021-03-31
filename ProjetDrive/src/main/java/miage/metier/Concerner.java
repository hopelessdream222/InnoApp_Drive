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
@Entity(name = "Concerner")
public class Concerner {
    // Proprietes

    @EmbeddedId
    private ConcernerId concernerid;

    @ManyToOne
    @JoinColumn(name = "idListe", insertable = false, updatable = false)
    private ListeCourse listecourse;

    @ManyToOne
    @JoinColumn(name = "idP", insertable = false, updatable = false)
    private Produit produit;

    public Concerner() {}

    
    public Concerner(ListeCourse listecourse, Produit produit) {
        this.listecourse = listecourse;
        this.produit = produit;
    }

    public ConcernerId getConcernerid() {
        return concernerid;
    }

    public void setConcernerid(ConcernerId concernerid) {
        this.concernerid = concernerid;
    }

    public ListeCourse getListecourse() {
        return listecourse;
    }

    public void setListecourse(ListeCourse listecourse) {
        this.listecourse = listecourse;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Concerner{" + "concernerid=" + concernerid + ", listecourse=" + listecourse + ", produit=" + produit + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.concernerid);
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
        final Concerner other = (Concerner) obj;
        if (!Objects.equals(this.concernerid, other.concernerid)) {
            return false;
        }
        return true;
    }
    

}
