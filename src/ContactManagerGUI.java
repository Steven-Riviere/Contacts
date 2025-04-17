import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class ContactManagerGUI extends JFrame {
    private ContactManager contactManager;

    private JTextField prenomField, telephoneField, emailField, rueField, villeField, codePostalField;
    private JTextArea contactListArea;

    public ContactManagerGUI() {
        contactManager = new ContactManager();

        setTitle("Gestionnaire de Contacts");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Formulaire d'ajout et de modification de contact
        JPanel formPanel = new JPanel(new GridLayout(8, 2));

        formPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField();
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Téléphone :"));
        telephoneField = new JTextField();
        formPanel.add(telephoneField);

        formPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Rue :"));
        rueField = new JTextField();
        formPanel.add(rueField);

        formPanel.add(new JLabel("Ville :"));
        villeField = new JTextField();
        formPanel.add(villeField);

        formPanel.add(new JLabel("Code Postal :"));
        codePostalField = new JTextField();
        formPanel.add(codePostalField);

        JButton ajouterButton = new JButton("Ajouter Contact");
        JButton rechercherButton = new JButton("Rechercher Contact");
        JButton mettreAJourButton = new JButton("Mettre à jour Contact");
        JButton supprimerButton = new JButton("Supprimer Contact");

        formPanel.add(ajouterButton);
        formPanel.add(rechercherButton);
        formPanel.add(mettreAJourButton);
        formPanel.add(supprimerButton);

        add(formPanel, BorderLayout.NORTH);

        // Zone d'affichage des contacts
        contactListArea = new JTextArea();
        contactListArea.setEditable(false);
        add(new JScrollPane(contactListArea), BorderLayout.CENTER);

        JButton afficherButton = new JButton("Afficher Contacts");
        add(afficherButton, BorderLayout.SOUTH);

        // Action : Ajouter un contact
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterContact();
            }
        });

        // Action : Afficher les contacts
        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherContacts();
            }
        });

        // Action : Rechercher un contact
        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercherContact();
            }
        });

        // Action : Mettre à jour un contact
        mettreAJourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreAJourContact();
            }
        });

        // Action : Supprimer un contact
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerContact();
            }
        });

        afficherContacts(); // Charger les contacts au démarrage

        setVisible(true);
    }

    private void ajouterContact() {
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        String rue = rueField.getText();
        String ville = villeField.getText();
        String codePostal = codePostalField.getText();

        if (prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || rue.isEmpty() || ville.isEmpty() || codePostal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contactManager.getContacts().stream().anyMatch(c -> c.getPrenom().equalsIgnoreCase(prenom) || c.getNumeroTelephone().equals(telephone))) {
            JOptionPane.showMessageDialog(this, "Un contact avec ce prénom ou ce numéro existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Adresse adresse = new Adresse(rue, ville, codePostal);
            Contact contact = new Contact(prenom, telephone, email, adresse);
            contactManager.ajouterContact(contact);

            JOptionPane.showMessageDialog(this, "Contact ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            reinitialiserChamps();
            afficherContacts(); // Met à jour l'affichage
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherContacts() {
        contactListArea.setText("");
        contactManager.getContacts().stream()
                .sorted(Comparator.comparing(Contact::getPrenom))
                .forEach(contact -> contactListArea.append(contact.afficherDetails() + "\n\n"));
    }

    private void rechercherContact() {
        String recherche = JOptionPane.showInputDialog(this, "Entrez un critère de recherche :");

        if (recherche == null || recherche.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un critère pour rechercher.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contactListArea.setText("");
        List<Contact> resultats = contactManager.getContacts().stream()
                .filter(contact -> contact.toString().toLowerCase().contains(recherche.toLowerCase()))
                .toList();

        if (resultats.size() == 1) {
            remplirChamps(resultats.get(0));
        } else {
            resultats.forEach(contact -> contactListArea.append(contact.afficherDetails() + "\n\n"));
        }
    }

    private void mettreAJourContact() {
        String prenom = prenomField.getText();
        Contact contact = contactManager.rechercherContact(prenom);

        if (contact == null) {
            JOptionPane.showMessageDialog(this, "Contact non trouvé pour mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contact.setNumeroTelephone(telephoneField.getText());
        contact.setEmail(emailField.getText());
        contact.setAdresse(new Adresse(rueField.getText(), villeField.getText(), codePostalField.getText()));

        JOptionPane.showMessageDialog(this, "Contact mis à jour avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        reinitialiserChamps();
        afficherContacts();
    }

    private void supprimerContact() {
        String prenom = prenomField.getText();
        if (prenom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un prénom pour supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contactManager.supprimerContact(prenom);
        JOptionPane.showMessageDialog(this, "Contact supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        reinitialiserChamps();
        afficherContacts();
    }

    private void remplirChamps(Contact contact) {
        prenomField.setText(contact.getPrenom());
        telephoneField.setText(contact.getNumeroTelephone());
        emailField.setText(contact.getEmail());
        rueField.setText(contact.getAdresse().getRue());
        villeField.setText(contact.getAdresse().getVille());
        codePostalField.setText(contact.getAdresse().getCodePostal());
    }

    private void reinitialiserChamps() {
        prenomField.setText("");
        telephoneField.setText("");
        emailField.setText("");
        rueField.setText("");
        villeField.setText("");
        codePostalField.setText("");
    }
}
