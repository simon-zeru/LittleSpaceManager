package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.*;
import fr.uga.iut2.genevent.util.LittleSpaceManager_Utilitaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Contrôleur principal de l'application Little Space Manager.
 * Cette classe gère les interactions avec l'interface utilisateur et la logique métier de l'application.
 */
public class MainControleur {

    private static final Log log = LogFactory.getLog(MainControleur.class);
    private TreeSet<Evenement> evenements = new TreeSet<>();
    private ArrayList<Artiste> artistes = new ArrayList<>();
    private ArrayList<Spectateur> spectateurs = new ArrayList<>();
    private ArrayList<Personnel> personnels = new ArrayList<>();
    private ArrayList<Salle> salles = new ArrayList<>();

    @FXML
    private Button btnAnnuler;

    //attribut accueil
    @FXML
    private ListView<Evenement> lvEvenement;

    @FXML
    private Label erreurLabel;

    /**
     * Initialise la vue de la liste des événements.
     * Cette méthode est appelée automatiquement après que le fichier FXML associé a été chargé.
     */
    public void initialize() {
        actualisationEvenement();
        lvEvenement.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Evenement selectedEvenement = lvEvenement.getSelectionModel().getSelectedItem();
                if (selectedEvenement != null) {
                    try {
                        ouvrirOptionEvenement(selectedEvenement);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Cette fonction permet d'ajouter un événement dans la liste des événements du Controleur.
     * @param evenement l'évenement à rajouter
     */
    public void ajouterEvenement(Evenement evenement) {
        evenements.add(evenement);
    }

    /**
     * Cette fonction permet de fermer la fenetre lors du clique de la souris sur le bouton Annuler.
     */
    @FXML
    public void onAnnulerClick(ActionEvent event) {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }

    /**
     * Cette fonction permet de de rajouter un artiste dans l'application
     * @param artiste l'artiste à rajouter
     */
    public void ajouterArtiste(Artiste artiste) {
        artistes.add(artiste);
    }

    /**
     * Cette fonction permet de de rajouter un spectateur dans l'application
     * @param spectateur le spectateur à rajouter
     */
    public void ajouterSpectateur(Spectateur spectateur) {
        spectateurs.add(spectateur);
    }

    /**
     * Cette fonction permet de de rajouter un salarié dans l'application
     * @param personnel le salarié à rajouter
     */
    public void ajouterPersonnel(Personnel personnel) {
        personnels.add(personnel);
    }

    /**
     * Cette fonction permet de de rajouter une salle dans l'application
     * @param salle la salle à rajouter
     */
    public void ajouterSalle(Salle salle) {
        salles.add(salle);
    }

    /**
     * Ouvre une nouvelle fenêtre pour la création d'un élément de type spécifié.
     *
     * @param typeCreation le type d'élément à créer.
     * @throws Exception si une erreur se produit lors du chargement du fichier FXML associé.
     */
    public void ouvrirFenetreCreation(String typeCreation, ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/Creation" + LittleSpaceManager_Utilitaire.removeAccents(typeCreation) + "View.fxml"));

        CreationControleur creationController = new CreationControleur();
        creationController.setMainController(this);
        creationController.setTypeCreation(typeCreation);

        loader.setController(creationController);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Création de " + typeCreation);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png"))));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    /**
     * Permet d'afficher une fenetre d'erreur avec un message d'erreur personnalisé.
     * Génère également un log de type WARNING
     * @param textErreur le message d'erreur affiché
     */
    public void afficherFenetreErreur(String textErreur) throws Exception {
        log.error(textErreur);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/AlerteErreurView.fxml"));
        loader.setController(this);
        Parent root = loader.load();


        erreurLabel.setText(textErreur);

        if (root instanceof VBox) {
            VBox vbox = (VBox) root;
            vbox.setPrefSize(500, 300);
        } else {
            throw new Exception("Le root n'est pas une instance de VBox");
        }

        Stage stage = new Stage();
        stage.setTitle("Erreur");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png")));
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    /**
     * Ouvre une nouvelle fenêtre pour afficher et modifier les options d'un événement donné.
     *
     * @param evenement l'événement à afficher et à modifier.
     */
    @FXML
    public void ouvrirOptionEvenement(Evenement evenement) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/OptionEvenementView.fxml"));

        ModificationEvenementController modificationControleur = new ModificationEvenementController();
        modificationControleur.setMainControleur(this);
        modificationControleur.setEvenement(evenement);

        loader.setController(modificationControleur);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Option événement");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ouvre la fenetre de création d'une salle
     */
    @FXML
    public void onButtonCreerSalle(ActionEvent event) throws Exception {
        ouvrirFenetreCreation("Salle", event);
    }

    /**
     * Ouvre la fenetre de création d'événement
     * Si aucune salle n'existe, cette fenetre ne s'ouvre pas
     */
    @FXML
    public void onButtonCreerEvenement(ActionEvent event) throws Exception {
        if (salles.isEmpty()) {
            afficherFenetreErreur("Vous devez paramétrer au moins une salle avant de pouvoir créer un événement.");
        } else {
            ouvrirFenetreCreation("Événement", event);
        }
    }

    /**
     * Ouvre la fenetre de création d'un salarié
     */
    @FXML
    public void onButtonCreerPersonnel(ActionEvent event) throws Exception {
        ouvrirFenetreCreation("Personnel", event);
    }

    /**
     * Ouvre la fenetre de création d'un artiste
     */
    @FXML
    public void onButtonCreerArtiste(ActionEvent event) throws Exception {
        ouvrirFenetreCreation("Artiste", event);
    }

    /**
     * Ouvre la fenetre de création d'un spectateur
     */
    @FXML
    public void onButtonCreerSpectateur(ActionEvent event) throws Exception {
        ouvrirFenetreCreation("Spectateur", event);
    }

    //getter

    /**
     * Renvoie la liste des salles
     * @return la liste des salles
     */
    public ArrayList<Salle> getSalles() {
        return salles;
    }

    /**
     * Renvoie la liste des événements, triés par odre chronologique
     * @return la liste des événements
     */
    public TreeSet<Evenement> getEvenements() {
        return evenements;
    }

    /**
     * Renvoie la liste des Artistes associés à ce Controleur.
     * @return
     */
    public ArrayList<Artiste> getArtistes() {
        return artistes;
    }

    /**
     * Renvoie la liste des Spectateurs associés à ce Controleur.
     * @return
     */

    public ArrayList<Spectateur> getSpectateurs() {
        return spectateurs;
    }

    /**
     * Renvoie la liste des membres du Personnel associés à ce Controleur.
     * @return
     */

    public ArrayList<Personnel> getPersonnels() {
        return personnels;
    }

    //méthode
    /**
     * Met à jour la liste des événements affichée dans l'interface utilisateur.
     * Cette méthode crée une nouvelle liste observable à partir de la liste d'événements
     * et l'associe à la ListView lvEvenement.
     */
    @FXML
    private void actualisationEvenement() {
        ObservableList<Evenement> listeEvenement = FXCollections.observableArrayList(new ArrayList<>(evenements));
        lvEvenement.setItems(listeEvenement);
    }

    /**
     * Récupère la fenêtre (Stage) associée à la ListView lvEvenement.
     *
     * @return la fenêtre (Stage) associée à la ListView lvEvenement.
     */

    @FXML
    public Stage getStage() {
        return (Stage) lvEvenement.getScene().getWindow();
    }

    //Voir Salle

    @FXML
    private Button btnVoirSalles;
    @FXML
    private ListView<String> lvSalles;


    /**
     * Cette fonction permet d'ouvrir la fenetre avec la liste des salles.
     * @param event
     */
    @FXML
    public void onButtonVoirSalle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/ListeSallesView.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png")));
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent
            Scene scene = new Scene(root, 737, 438);
            popupStage.setScene(scene);
            popupStage.setTitle("Liste des salles");

            ArrayList<String> lvSallesTemp = new ArrayList<>();
            for (Salle s : salles) {
                String tags = "";
                for (String t : s.getTags()) {
                    tags += " " + t;
                }
                lvSallesTemp.add("\t" + s + "\nAdresse : " + s.getAdresse() + "\nCapacité max : " + s.getCapacite_max() + "\n" + tags);
            }

            ObservableList<String> listeSalle = FXCollections.observableArrayList(new ArrayList<>(lvSallesTemp));
            lvSalles.setItems(listeSalle);

            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
