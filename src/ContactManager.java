import java.util.ArrayList;
import java.io.*;

public class ContactManager {
    private ArrayList<Contact> contacts;
    private final String FICHIER_CONTACTS = "contacts.txt";

    // Constructeur
    public ContactManager() {
        this.contacts = new ArrayList<>();
        chargerContacts();
    }

    // Ajouter un contact
    public void ajouterContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Contact ajouté : " + contact);
        sauvegarderContacts();
    }

    // Afficher tous les contacts
    public void afficherContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Aucun contact enregistré.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    // Rechercher un contact par prénom
    public Contact rechercherContact(String prenom) {
        for (Contact contact : contacts) {
            if (contact.getPrenom().equalsIgnoreCase(prenom)) {
                return contact;
            }
        }
        return null;
    }

    // Supprimer un contact par prénom
    public void supprimerContact(String prenom) {
        Contact contact = rechercherContact(prenom);
        if (contact != null) {
            contacts.remove(contact);
            System.out.println("Contact supprimé : " + contact);
            sauvegarderContacts();
        } else {
            System.out.println("Contact non trouvé.");
        }
    }

    // Obtenir la liste des contacts
    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    // Sauvegarder les contacts dans un fichier
    private void sauvegarderContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_CONTACTS))) {
            for (Contact contact : contacts) {
                writer.write(contact.getPrenom() + "," +
                        contact.getNumeroTelephone() + "," +
                        contact.getEmail() + "," +
                        contact.getAdresse().getRue() + "," +
                        contact.getAdresse().getVille() + "," +
                        contact.getAdresse().getCodePostal());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des contacts : " + e.getMessage());
        }
    }

    // Charger les contacts depuis un fichier
    private void chargerContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_CONTACTS))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split(",");
                if (parties.length == 6) {
                    Adresse adresse = new Adresse(parties[3], parties[4], parties[5]);
                    Contact contact = new Contact(parties[0], parties[1], parties[2], adresse);
                    contacts.add(contact);
                }
            }
            System.out.println("Contacts chargés depuis le fichier.");
        } catch (IOException e) {
            System.err.println("Aucun fichier de contacts trouvé ou erreur lors du chargement.");
        }
    }
}
