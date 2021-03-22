
package miage.metier;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.persistence.Column;

public class FournirId implements Serializable{
    
    @Column(name="idP")
    private int idP;
    @Column(name="idF")
    private int idF;
    @Column(name="idMag")
    private int idMag;

}
