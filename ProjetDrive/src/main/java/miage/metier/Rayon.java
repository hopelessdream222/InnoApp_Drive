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
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity (name="Rayon")
public class Rayon {
    // proprietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRay")
    private int idRay;
    private String libelleRay;
    
    // relation <Situer>
    @OneToMany(mappedBy = "rayonCat", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Categorie> categories = new HashSet<>(0);
    
   
    
    
}
