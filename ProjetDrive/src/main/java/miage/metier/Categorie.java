/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity (name="Categorie")
public class Categorie {
    // proprietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCat")
    private int idCat;
    private String libelleCat;
    
    // relation <Situer>
    @ManyToOne(fetch =FetchType.EAGER)  // comparaison avec LAZY
    @JoinColumn(name = "idRay")
    private Rayon rayonCat;
    
    // relation <Appartenir>
    @OneToMany(mappedBy = "categorieP", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Produit> produits = new HashSet<>(0);
    
}
