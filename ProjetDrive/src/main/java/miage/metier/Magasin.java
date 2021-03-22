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
@Entity (name="Magasin")
public class Magasin {
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
    @MapKeyJoinColumn(name = "IdMag")
    private Map<Produit, Stocker> stockages=new HashMap(0);

}
