/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
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
@Entity (name="Magasin")
public class Magasin implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idMag")  
    private int idMag;
    private String nomMag;
    private String adresseMag;
    private String villeMag;
    private String cpMagasin;
    private String telMag;
    
    // relation  <Retirer>
    @OneToMany(mappedBy = "magasinCmd", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commande> commandes = new HashSet<>(0);
    
    // Relation <Stocker>
    @OneToMany(mappedBy = "magasins",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idP")
    private Map<Produit, Stocker> stockages=new HashMap(0);
    
    // Relation <Disponibilite>
    @OneToMany(mappedBy = "magasins",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idCren")
    private Map<Creneau, Disponibilite> creneaux=new HashMap(0);

    public Magasin() {
    }

    public Magasin(String nomMag, String adresseMag, String villeMag, String cpMagasin, String telMag) {
        this.nomMag = nomMag;
        this.adresseMag = adresseMag;
        this.villeMag = villeMag;
        this.cpMagasin = cpMagasin;
        this.telMag = telMag;
    }

    public Magasin(int idMag, String nomMag, String adresseMag, String villeMag, String cpMagasin, String telMag) {
        this.idMag = idMag;
        this.nomMag = nomMag;
        this.adresseMag = adresseMag;
        this.villeMag = villeMag;
        this.cpMagasin = cpMagasin;
        this.telMag = telMag;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public String getNomMag() {
        return nomMag;
    }

    public void setNomMag(String nomMag) {
        this.nomMag = nomMag;
    }

    public String getAdresseMag() {
        return adresseMag;
    }

    public void setAdresseMag(String adresseMag) {
        this.adresseMag = adresseMag;
    }

    public String getVilleMag() {
        return villeMag;
    }

    public void setVilleMag(String villeMag) {
        this.villeMag = villeMag;
    }

    public String getCpMagasin() {
        return cpMagasin;
    }

    public void setCpMagasin(String cpMagasin) {
        this.cpMagasin = cpMagasin;
    }

    public String getTelMag() {
        return telMag;
    }

    public void setTelMag(String telMag) {
        this.telMag = telMag;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    public Map<Produit, Stocker> getStockages() {
        return stockages;
    }

    public void setStockages(Map<Produit, Stocker> stockages) {
        this.stockages = stockages;
    }

    public Map<Creneau, Disponibilite> getCreneaux() {
        return creneaux;
    }

    public void setCreneaux(Map<Creneau, Disponibilite> creneaux) {
        this.creneaux = creneaux;
    }
    

    @Override
    public String toString() {
        return nomMag + "\n" + adresseMag + "\n" + cpMagasin +", "+ villeMag + "\n" + telMag;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.idMag;
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
        final Magasin other = (Magasin) obj;
        if (this.idMag != other.idMag) {
            return false;
        }
        return true;
    }
    
    
}
