public class Contact {
    private String prenom;
    private String numeroTelephone;
    private String email;
    private Adresse adresse;

    // Constructeur avec validation
    public Contact(String prenom, String numeroTelephone, String email, Adresse adresse) {
        if (prenom == null || prenom.isEmpty()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (!numeroTelephone.matches("^(\\+33|0)[1-9](\\d{8})$")) {
            throw new IllegalArgumentException("Numéro de téléphone invalide (format attendu : +33 ou 0 suivi de 9 chiffres).");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Adresse email invalide.");
        }

        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.email = email;
        this.adresse = adresse;
    }

    // Getters
    public String getPrenom() {
        return prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getEmail() {
        return email;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    // Setters
    public void setPrenom(String prenom) {
        if (prenom == null || prenom.isEmpty()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        this.prenom = prenom;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        if (!numeroTelephone.matches("^(\\+33|0)[1-9](\\d{8})$")) {
            throw new IllegalArgumentException("Numéro de téléphone invalide.");
        }
        this.numeroTelephone = numeroTelephone;
    }

    public void setEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Adresse email invalide.");
        }
        this.email = email;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    // Affichage détaillé du contact
    public String afficherDetails() {
        return "Prénom: " + prenom + "\n" +
                "Téléphone: " + numeroTelephone + "\n" +
                "Email: " + email + "\n" +
                "Adresse: " + (adresse != null ? adresse.toString() : "Non spécifiée");
    }

    @Override
    public String toString() {
        return afficherDetails();
    }
}
