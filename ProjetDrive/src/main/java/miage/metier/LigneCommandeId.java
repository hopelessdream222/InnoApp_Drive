/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.metier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ccc
 */
@Embeddable 
public class LigneCommandeId implements Serializable{
    @Column(name="idP")    
    private int idP;
    @Column(name="idCmd")  
    private int idCmd;
}
