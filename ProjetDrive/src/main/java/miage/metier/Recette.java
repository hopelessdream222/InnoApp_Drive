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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author ccc
 */
@Entity(name="Recette")
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRect")        
    private int idRect;
    private String libelleRect;
    
    // Relation <Necessiter>
    @OneToMany(mappedBy = "recette",cascade=CascadeType.ALL)
    @MapKeyJoinColumn(name = "IdRect")
    private Map<Ingredient, Necessiter> necessiters=new HashMap(0);
    
    
}
