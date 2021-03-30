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
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ccc
 */
@Entity (name="Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCli")
    private int idCli;
    private String nomCli;
    private String prenomCli;
    private String emailCli;
    private String mdpCli;
    private String telCli;
    private int pointCli;
    
    // Relation <Passer>
    @OneToMany(mappedBy = "clientCmd", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Commande> commandes = new HashSet<>(0);
    
    // Relation <Posseder>
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="panier_fk")
    private Panier panier;
    
    // Relation <Preferer>
    @OneToMany(mappedBy = "client",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "idP")
    private Map<Produit,Preferer> preferences=new HashMap<>(0);
    
    public Client() {
    }
    
    public Client(String nomCli, String prenomCli, String emailCli,String mdpCli, String telCli, int pointCli) {
        this.nomCli = nomCli;
        this.prenomCli = prenomCli;
        this.emailCli = emailCli;
        this.mdpCli=mdpCli;
        this.telCli = telCli;
        this.pointCli = pointCli;
    }

    public Client(int idCli, String nomCli, String prenomCli, String emailCli, String mdpCli, String telCli, int pointCli) {
        this.idCli = idCli;
        this.nomCli = nomCli;
        this.prenomCli = prenomCli;
        this.emailCli = emailCli;
        this.mdpCli = mdpCli;
        this.telCli = telCli;
        this.pointCli = pointCli;
    }

    public String getMdpCli() {
        return mdpCli;
    }

    public void setMdpCli(String mdpCli) {
        this.mdpCli = mdpCli;
    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getPrenomCli() {
        return prenomCli;
    }

    public void setPrenomCli(String prenomCli) {
        this.prenomCli = prenomCli;
    }

    public String getEmailCli() {
        return emailCli;
    }

    public void setEmailCli(String emailCli) {
        this.emailCli = emailCli;
    }

    public String getTelCli() {
        return telCli;
    }

    public void setTelCli(String telCli) {
        this.telCli = telCli;
    }

    public int getPointCli() {
        return pointCli;
    }

    public void setPointCli(int pointCli) {
        this.pointCli = pointCli;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }
    

    @Override
    public String toString() {
        return "Client{" + "idCli=" + idCli + ", nomCli=" + nomCli + ", prenomCli=" + prenomCli + ", emailCli=" + emailCli + ", mdpCli=" + mdpCli + ", telCli=" + telCli + ", pointCli=" + pointCli + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.idCli;
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
        final Client other = (Client) obj;
        if (this.idCli != other.idCli) {
            return false;
        }
        return true;
    }
    
    
    
    
}
