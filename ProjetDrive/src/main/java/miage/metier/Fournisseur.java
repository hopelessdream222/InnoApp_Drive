
package miage.metier;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;


@Entity(name = "Fournisseur")
public class Fournisseur implements Serializable{
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="idF")
    private long id;
    
    @Column(name="nomF")
    private String nom;
    
    @Column(name="telF")
    private int tel;
    
}
