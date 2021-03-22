/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ccc
 */
@Entity (name="LigneCommande")
public class LigneCommande {
    @EmbeddedId
    private LigneCommandeId ligneId;
    private int qteCP;

    // Relation
    @ManyToOne
    @JoinColumn(name = "idCmd", insertable = false,updatable = false)
    private Commande commandes;
    
    @ManyToOne
    @JoinColumn(name = "idP", insertable = false,updatable = false)    
    private Produit produits;
}
