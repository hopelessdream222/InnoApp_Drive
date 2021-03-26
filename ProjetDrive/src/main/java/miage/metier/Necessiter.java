/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ccc
 */
@Entity (name="Necessiter")
public class Necessiter {
    // Proprietes
    @EmbeddedId    
    private NecessiterId necessiter;
    private int qteRI;
    
    @ManyToOne
    @JoinColumn(name="idIng",insertable=false,updatable=false)
    private Ingredient ingredient;
    
    @ManyToOne
    @JoinColumn(name="idRect",insertable=false,updatable=false)
    private Recette recette;
    
    
}
