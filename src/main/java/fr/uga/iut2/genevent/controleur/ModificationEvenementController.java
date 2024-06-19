package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModificationEvenementController {

    //Bilan comptable
    @FXML
    private Button btnBilan;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnQuitter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnCreer, btnValider, btnAnnuler;
    @FXML
    private Label labelCoutInitial;
    @FXML
    private Label labelSalairesArt;
    @FXML
    private Label labelSalairesPer;
    @FXML
    private Label labelPrixTickets;
    @FXML
    private Label labelTotal;
    @FXML
    private Label lbAssocier;
    @FXML
    private ListView<Participant> lvPersonnel;
    @FXML
    private ListView<Spectateur> lvSpectateur;
    @FXML
    private ListView<Participant> lvArtiste;
    @FXML
    private ComboBox<Personne> cbAssocier;
    //Supprimer evenement
    @FXML
    private Button btnValiderSupre;
    @FXML
    private Button btnAnnulerSupre;


    private MainControleur mainControleur;
    private Evenement evenement;


    public void initialize() {
        if (lvSpectateur != null && lvPersonnel != null && lvArtiste != null) {
            actualisationListe();
        }
    }

    //Methodes

    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    @FXML
    public void onQuitterClick(ActionEvent event) {
        Stage stage = (Stage) btnQuitter.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onRetourClick(ActionEvent event) {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onBilanClick(ActionEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/BilanComptableView.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent
            Scene scene = new Scene(root, 498, 245);

            /*=================================================================
                A modifier quand La liste des événements sera fonctionelle
            ==================================================================*/

//            Salle testSalle = new Salle("i", "Jardin d'Eden", 200);
//            Concert eventActuel = new Concert("HeavenFest", 1, 1000, 15, new Date(), new Date(), "Feur", testSalle);
//            Artiste Adam = new Artiste("", "Adam", 100, 1.5f);
//            eventActuel.addParticipant(Adam);

            //==================================================================


            labelCoutInitial.setText(String.valueOf(evenement.getCoutInitial()));

            labelSalairesArt.setText(String.valueOf(evenement.getSalairesArtistes()));

            labelSalairesPer.setText(String.valueOf(evenement.getSalairesPersonnels()));

            labelPrixTickets.setText(String.valueOf(evenement.getGainsTickets()));

            labelTotal.setText(String.valueOf(evenement.getBenefices()));

            //=================================================================

            popupStage.setScene(scene);
            popupStage.setTitle("Bilan Comptable");
            popupStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void onSupr(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/PageValidationSuppressionView.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent
            Scene scene = new Scene(root, 498, 245);
            popupStage.setScene(scene);
            popupStage.setTitle("Supprimer " + evenement.getNom() + " ?");
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onValiderSupre(Event event) {
        try {
            mainControleur.getEvenements().remove(evenement);
            evenement.setSalle(null);
            Stage stage = (Stage) btnValiderSupre.getScene().getWindow();
            stage.close();
            Stage stage1 = (Stage) btnRetour.getScene().getWindow();
            stage1.close();
            mainControleur.initialize();
            System.out.println(mainControleur.getEvenements().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void onAnnulerSupre(Event event) {
        try {
            Stage stage = (Stage) btnAnnulerSupre.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onButtonModifierClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/ModificationEvenementView.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Stage stage = mainControleur.getStage();

        Stage popupPrecedent = (Stage) btnModifier.getScene().getWindow();
        popupPrecedent.close();

        stage.setScene(new Scene(root));
    }

    private void actualisationListe() {

        //test
        ObservableList<Participant> listePersonnel = FXCollections.observableArrayList(evenement.getPersonnels());
        lvPersonnel.setItems(listePersonnel);

        ObservableList<Participant> listeArtiste = FXCollections.observableArrayList(evenement.getArtistes());
        lvArtiste.setItems(listeArtiste);

        ObservableList<Spectateur> listeSpectateur = FXCollections.observableArrayList(evenement.getSpectateurs());
        lvSpectateur.setItems(listeSpectateur);
    }

    private void ouvertureAssociationPage(String typePersonne) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/AssocierView.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Stage stage = new Stage();

        if (typePersonne.equalsIgnoreCase("Spectateur")) {
            stage.setTitle("Associer spectateur");
            lbAssocier.setText(lbAssocier.getText() + "spectateur");
            ObservableList<Personne> listSpectateur = FXCollections.observableArrayList(mainControleur.getSpectateurs());
            cbAssocier.setItems(listSpectateur);
        } else if (typePersonne.equalsIgnoreCase("Artiste")) {
            stage.setTitle("Associer artiste");
            lbAssocier.setText(lbAssocier.getText() + "artiste");
            ObservableList<Personne> listArtiste = FXCollections.observableArrayList(mainControleur.getArtistes());
            cbAssocier.setItems(listArtiste);
        } else {
            stage.setTitle("Associer personnel");
            lbAssocier.setText(lbAssocier.getText() + "personnel");
            ObservableList<Personne> listPersonnel = FXCollections.observableArrayList(mainControleur.getPersonnels());
            cbAssocier.setItems(listPersonnel);
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onSpectateurClick() throws IOException {
        ouvertureAssociationPage("spectateur");
    }

    @FXML
    private void onArtisteClick() throws IOException {
        ouvertureAssociationPage("artiste");
    }

    @FXML
    private void onPersonnelClick() throws IOException {
        ouvertureAssociationPage("personnel");
    }

    @FXML
    private void onAnnulerClick() {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCreerClick() {

    }

    @FXML
    private void onValiderClick() {
        Stage stage = (Stage) btnValider.getScene().getWindow();

        if (cbAssocier.getValue() != null) {
            Personne personne = cbAssocier.getValue();
            if (personne.getClass().equals(Artiste.class) || personne.getClass().equals(Personnel.class)) {
                evenement.addParticipant((Participant) personne);
            } else {
                evenement.addTicket((Spectateur) personne);
            }
        }
        initialize();
        stage.close();
    }
}
