
package miage.dao;


public class nbDde {
    // Proprietes
    private String nom;
    private long nbDde;
    
    // Constructeurs

    public nbDde() {
    }

    public nbDde(String nom, long nbDde) {
        this.nom = nom;
        this.nbDde = nbDde;
    }
    
    // Getter/ Setter

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getNbDde() {
        return nbDde;
    }

    public void setNbDde(long nbDde) {
        this.nbDde = nbDde;
    }
    
    
}
