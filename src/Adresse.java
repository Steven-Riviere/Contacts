public class Adresse {
    private String rue;
    private String ville;
    private String codePostal;

    // Constructeur
    public Adresse(String rue, String ville, String codePostal) {
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    // Getters
    public String getRue() {
        return rue;
    }

    public String getVille() {
        return ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    // Setters
    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    // Méthode pour l'affichage
    @Override
    public String toString() {
        return rue + ", " + codePostal + " " + ville;
    }
}
