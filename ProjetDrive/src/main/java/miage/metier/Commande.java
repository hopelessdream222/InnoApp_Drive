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
    
    
    
    
}
