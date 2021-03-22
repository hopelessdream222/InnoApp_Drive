package miage.metier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StockerId  implements Serializable{
    // Proprietes
    @Column(name="idP")     
    private int idP;
    @Column(name="idMag")  
    private int idMag;
    //constructeur


}
