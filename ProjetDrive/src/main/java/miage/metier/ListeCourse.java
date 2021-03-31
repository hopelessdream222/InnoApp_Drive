/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity (name="ListeCourse")
public class ListeCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idListe")
    private int idListe;
    private String libelleListe;
    
    // Relation <Avoir>
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "idCli")
    private Client client;
    
    // Relation <Concerner>
    @OneToMany(mappedBy = "listecourse",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idP")
    private Map<Produit, Concerner> concerner=new HashMap(0); 
    
    // Relation <Composer>
    @OneToMany(mappedBy = "listeCourse",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idIng")
    private Map<Ingredient, Composer> composers=new HashMap(0);

    public ListeCourse() {
    }

    public ListeCourse(String libelleListe, Client client) {
        this.libelleListe = libelleListe;
        this.client = client;
    }

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    public String getLibelleListe() {
        return libelleListe;
    }

    public void setLibelleListe(String libelleListe) {
        this.libelleListe = libelleListe;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Map<Produit, Concerner> getConcerner() {
        return concerner;
    }

    public void setConcerner(Map<Produit, Concerner> concerner) {
        this.concerner = concerner;
    }

    public Map<Ingredient, Composer> getComposers() {
        return composers;
    }

    public void setComposers(Map<Ingredient, Composer> composers) {
        this.composers = composers;
    }

    
    @Override
    public String toString() {
        return "ListeCourse{" + "idListe=" + idListe + ", libelleListe=" + libelleListe + ", client=" + client + ", concerner=" + concerner + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.idListe;
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
        final ListeCourse other = (ListeCourse) obj;
        if (this.idListe != other.idListe) {
            return false;
        }
        return true;
    }
    
    
    
}
