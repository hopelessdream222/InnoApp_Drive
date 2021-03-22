/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.Date;
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
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int idCmd;
    private Date datecmd;
    
    // Relation <LigneCommande>
    @OneToMany(mappedBy = "commandes",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "IdCmd")
    private Map<Produit, LigneCommande> lignecommandes=new HashMap(0);
    
    // relation <Passer>
    @ManyToOne(fetch =FetchType.EAGER) 
    @JoinColumn(name = "idCli")
    private Client clientCmd;
    
    // relation <Retirer>
    @ManyToOne(fetch =FetchType.EAGER)  
    @JoinColumn(name = "idMag")
    private Magasin magasinCmd;

    public Commande() {
    }
    
    
    public Commande(Date datecmd, Client clientCmd, Magasin magasinCmd) {
        this.datecmd = datecmd;
        this.clientCmd = clientCmd;
        this.magasinCmd = magasinCmd;
    }

    public int getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    public Date getDatecmd() {
        return datecmd;
    }

    public void setDatecmd(Date datecmd) {
        this.datecmd = datecmd;
    }

    public Map<Produit, LigneCommande> getLignecommandes() {
        return lignecommandes;
    }

    public void setLignecommandes(Map<Produit, LigneCommande> lignecommandes) {
        this.lignecommandes = lignecommandes;
    }

    public Client getClientCmd() {
        return clientCmd;
    }

    public void setClientCmd(Client clientCmd) {
        this.clientCmd = clientCmd;
    }

    public Magasin getMagasinCmd() {
        return magasinCmd;
    }

    public void setMagasinCmd(Magasin magasinCmd) {
        this.magasinCmd = magasinCmd;
    }

    @Override
    public String toString() {
        return "Commande{" + "idCmd=" + idCmd + ", datecmd=" + datecmd + ", lignecommandes=" + lignecommandes + ", clientCmd=" + clientCmd + ", magasinCmd=" + magasinCmd + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idCmd;
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
        final Commande other = (Commande) obj;
        if (this.idCmd != other.idCmd) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
