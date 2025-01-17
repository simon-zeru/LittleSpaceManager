package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.exception.CreateException;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Contrôleur pour la modification d'un événement.
 * Cette classe gère les interactions avec l'interface utilisateur et la logique métier pour modifier un événement.
 */
public class ModificationEvenementController {

    private final GenEvent genevent;
    private static final Log log = LogFactory.getLog(ModificationEvenementController.class);
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
    private ComboBox<Personne> cbAssocier;

    //Modification événement
    @FXML
    private ListView<Participant> lvPersonnel;
    @FXML
    private ListView<Spectateur> lvSpectateur;
    @FXML
    private ListView<Participant> lvArtiste;
    @FXML
    private TextField tfNom, tfPrixTicket;
    @FXML
    private DatePicker dpDebut, dpFin;
    @FXML
    private ComboBox<Salle> cbSalle;
    @FXML
    private Button btnModificationValider;

    //Spec Piece de theatre
    @FXML
    private Button btnSpec;

    //option personne
    @FXML
    private Button btnSuprPersonne, btnModifierPersonne;


    //Supprimer evenement
    @FXML
    private Button btnValiderSupre;
    @FXML
    private Button btnAnnulerSupre;

    @FXML
    private  Button btnCreerAssocier;

    //modification salle
    @FXML
    private TextField tfNomSalle, tfCapaciteMax, tfAdresse;
    @FXML
    private TextArea taTags;
    @FXML
    private Button btnModifierSalle, btnSuprSalle, btnPlanningSalle;

    @FXML
    private ListView<Evenement> lvEvenementSalle;


    private MainControleur mainControleur;
    private Evenement evenement;
    private Salle salle;
    private Personne personne;

    public ModificationEvenementController(GenEvent genevent) {
        this.genevent = genevent;
    }

    /**
     * Initialise la vue de modification d'un événement.
     * Cette méthode est appelée automatiquement après que le fichier FXML associé a été chargé.
     * Elle met à jour les listes des participants de l'événement.
     */
    public void initialize() {
        if (lvSpectateur != null && lvPersonnel != null && lvArtiste != null) {
            actualisationListe();
        }

        if (lvArtiste != null){
            ajouterGestionnaireDoubleClic(lvArtiste, this::ouvrirOptionView);
        }
        if (lvSpectateur != null){
            ajouterGestionnaireDoubleClic(lvSpectateur, this::ouvrirOptionView);
        }
        if (lvPersonnel != null){
            ajouterGestionnaireDoubleClic(lvPersonnel, this::ouvrirOptionView);
        }
    }
    //Methodes

    /**
     * Permet de faire en sorte que le double clic sur une liste view déclanche un événement avec l'item séléctionné en paramétre.
     * @param listView
     * @param action
     * @param <T>
     */
    private <T> void ajouterGestionnaireDoubleClic(ListView<T> listView, Consumer<T> action) {
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                T selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    try {
                        log.info("Selected item : " + selectedItem.toString());
                        action.accept(selectedItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Ouvre le panneau des options possibles sur une personne.
     * @param personne
     * @param <T>
     */

    @FXML
    private Label lbOptionPersonne;
    @FXML
    private <T> void ouvrirOptionView(T personne){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/OptionPersonneView.fxml"));
            loader.setController(this);

            Parent root = loader.load();

            this.personne = (Personne) personne;

            lbOptionPersonne.setText(lbOptionPersonne.getText() + this.personne.toString());



            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // stage.initModality(Modality.APPLICATION_MODAL);
            //stage.showAndWait();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Définit le contrôleur principal de l'application.
     *
     * @param mainControleur le contrôleur principal de l'application.
     */
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }

    public void setSalle(Salle salle){
        this.salle = salle;
    }

    /**
     * Définit l'événement à modifier.
     *
     * @param evenement l'événement à modifier.
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    /**
     * Ferme la fenêtre de modification d'un événement lorsque le bouton Quitter est cliqué.
     *
     * @param event
     */
    @FXML
    public void onQuitterClick(ActionEvent event) {
        Stage stage = (Stage) btnQuitter.getScene().getWindow();
        stage.close();
    }

    /**
     * Ferme la fenêtre de modification d'un événement lorsque le bouton Retour est cliqué.
     *
     * @param event
     */
    @FXML
    public void onRetourClick(ActionEvent event) {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        stage.close();
    }

    /**
     * Affiche le bilan comptable de l'événement lorsque le bouton Bilan est cliqué.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    public void onBilanClick(ActionEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/BilanComptableView.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png")));

            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent
            Scene scene = new Scene(root, 498, 245);

            labelCoutInitial.setText(String.valueOf(evenement.getCoutInitial()));

            labelSalairesArt.setText(String.valueOf(evenement.getSalairesArtistes()));

            labelSalairesPer.setText(String.valueOf(evenement.getSalairesPersonnels()));

            labelPrixTickets.setText(String.valueOf(evenement.getGainsTickets()));

            labelTotal.setText(String.valueOf(evenement.getBenefices()));

            //=================================================================

            popupStage.setScene(scene);
            popupStage.setTitle("Bilan : "+evenement.getNom());
            popupStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Affiche une fenêtre de confirmation de suppression de l'événement lorsque le bouton Supprimer est cliqué.
     *
     * @param event
     */

    @FXML
    public void onSupr(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/PageValidationSuppressionView.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png")));

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

    /**
     * Supprime l'événement lorsque le bouton Valider de la fenêtre de confirmation de suppression est cliqué.
     *
     * @param event
     */
    @FXML
    public void onValiderSupre(Event event) {
        try {
            genevent.getEvenements().remove(evenement);
            mainControleur.getEvenements().remove(evenement);
            evenement.setSalle(null);
            Stage stage = (Stage) btnValiderSupre.getScene().getWindow();
            stage.close();
            Stage stage1 = (Stage) btnRetour.getScene().getWindow();
            stage1.close();
            mainControleur.initialize();
            log.info("Evenement " + evenement.getNom() + " supprimé");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Ferme la fenêtre de confirmation de suppression de l'événement lorsque le bouton Annuler est cliqué.
     *
     * @param event l'événement Event qui a déclenché la fermeture de la fenêtre de confirmation.
     */

    @FXML
    public void onAnnulerSupre(Event event) {
        try {
            Stage stage = (Stage) btnAnnulerSupre.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche la fenêtre de modification d'un événement lorsque le bouton Modifier est cliqué.
     *
     * @throws IOException
     */
    @FXML
    public void onButtonModifierClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/ModificationEvenementView.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Stage stage = mainControleur.getStage();

        tfNom.setText(evenement.getNom());
        dpDebut.setValue(evenement.getDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dpFin.setValue(evenement.getFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        ObservableList<Salle> listeSalle = FXCollections.observableArrayList(mainControleur.getSalles());
        cbSalle.setItems(listeSalle);
        cbSalle.setValue(evenement.getSalle());
        tfPrixTicket.setText(String.valueOf(evenement.getPrixTickets()));

        //Bouton spec qui s'active que si l'event est une pdt ou un concert.
        btnSpec.setDisable(!(evenement instanceof PieceDeTheatre | evenement instanceof Concert));

        Stage popupPrecedent = (Stage) btnModifier.getScene().getWindow();
        popupPrecedent.close();

        stage.setScene(new Scene(root));
    }

    /**
     * Met à jour les listes des participants (personnel, artistes et spectateurs) de l'événement.
     */

    private void actualisationListe() {

        //test
        ObservableList<Participant> listePersonnel = FXCollections.observableArrayList(evenement.getPersonnels());
        lvPersonnel.setItems(listePersonnel);

        ObservableList<Participant> listeArtiste = FXCollections.observableArrayList(evenement.getArtistes());
        lvArtiste.setItems(listeArtiste);

        ObservableList<Spectateur> listeSpectateur = FXCollections.observableArrayList(evenement.getSpectateurs());
        lvSpectateur.setItems(listeSpectateur);
    }

    /**
     * Affiche la fenêtre d'association d'un participant à l'événement en fonction du type de participant spécifié.
     *
     * @param typePersonne le type de participant à associer à l'événement.
     * @throws IOException
     */

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

    private  void onCreerAssocierClick(Event event){

    }

    /**
     * Affiche la fenêtre d'association d'un spectateur à l'événement lorsque le bouton Spectateur est cliqué.
     *
     * @throws IOException
     */

    @FXML
    private void onSpectateurClick() throws IOException {
        ouvertureAssociationPage("spectateur");
    }

    /**
     * Affiche la fenêtre d'association d'un artiste à l'événement lorsque le bouton Artiste est cliqué.
     *
     * @throws IOException
     */

    @FXML
    private void onArtisteClick() throws IOException {
        ouvertureAssociationPage("artiste");
    }

    /**
     * Affiche la fenêtre d'association d'un membre du personnel à l'événement lorsque le bouton Personnel est cliqué.
     *
     * @throws IOException
     */
    @FXML
    private void onPersonnelClick() throws IOException {
        ouvertureAssociationPage("personnel");
    }

    /**
     * Ferme la fenêtre d'association d'un participant à l'événement lorsque le bouton Annuler est cliqué.
     */
    @FXML
    private void onAnnulerClick() {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }

    /**
     * Affiche la fenêtre de création d'un participant en fonction du type de participant spécifié.
     *
     * @param event
     * @throws Exception
     */

    @FXML
    private void onCreerClick(ActionEvent event) throws Exception {
        String typepersonne= lbAssocier.getText();
        String[] words = typepersonne.split(" ");
        ArrayList<String> tmp=new ArrayList<>();
        for (String word : words) {
            tmp.add(word);
        }
        ouvertureBonnePageCreer(tmp.get(2),event);
    }

    /**
     * Affiche la fenêtre de création d'un participant en fonction du type de participant spécifié.
     *
     * @param typepersonne le type de participant à créer.
     * @param event
     * @throws Exception
     */

    private  void ouvertureBonnePageCreer(String typepersonne,ActionEvent event) throws Exception {
        Stage satge = (Stage) btnCreerAssocier.getScene().getWindow();
        satge.close();
        if (typepersonne.compareToIgnoreCase("spectateur")==0){
            mainControleur.onButtonCreerSpectateur(event);
            ouvertureAssociationPage("Spectateur");
            try {
                cbAssocier.setValue(mainControleur.getSpectateurs().get(mainControleur.getSpectateurs().size()-1));
            } catch (Exception e){
                log.error("Aucun spectateurs n'est présent dans la liste");
            }
        } else if (typepersonne.compareToIgnoreCase("personnel")==0) {
            mainControleur.onButtonCreerPersonnel(event);
            ouvertureAssociationPage("Personnel");
            try {
                cbAssocier.setValue(mainControleur.getPersonnels().get(mainControleur.getPersonnels().size()-1));
            } catch (Exception e){
                log.error("Aucun salarié n'est présent dans la liste");
            }
        }else {
            mainControleur.onButtonCreerArtiste(event);
            ouvertureAssociationPage("Artiste");
            try {
                cbAssocier.setValue(mainControleur.getArtistes().get(mainControleur.getArtistes().size()-1));
            } catch (Exception e){
                log.error("Aucun artiste n'est présent dans la liste");
            }
        }
    }

    /**
     * Associe un participant à l'événement lorsque le bouton Valider de la fenêtre d'association est cliqué.
     */

    @FXML
    private void onValiderAssociationClick() {
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


    /**
     * Met à jour les informations de l'événement avec les modifications saisies dans l'interface utilisateur.
     *
     * @throws CreateException
     * @throws IOException
     */
    @FXML
    private void onValiderModificationClick() throws Exception {
        boolean aucuneErreur = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/AccueilView.fxml"));
        loader.setController(mainControleur);
        Parent root = loader.load();

        Stage stage = (Stage) btnModificationValider.getScene().getWindow();


        Date debut = Date.from(dpDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(dpFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (!tfNom.getText().equalsIgnoreCase(evenement.getNom())){
            log.info("L'événement " + evenement.getNom() + " a été renommé en " + tfNom.getText());
            evenement.setNom(tfNom.getText());

        }
        if (!debut.equals(evenement.getDebut()) || !fin.equals(evenement.getFin())){
            log.info("L'événement " + evenement.getNom() + " a désormais lieu du " + debut + " au " + fin + ".");
            Salle temp = evenement.getSalle();
            evenement.setSalle(null);
            try {
                evenement.definirDates(temp, debut, fin);
            }
            catch (CreateException e){
                aucuneErreur = false;
                mainControleur.afficherFenetreErreur(e.getMessage());
            }
            evenement.setSalle(temp);
        }
        if (!cbSalle.getValue().equals(evenement.getSalle())){
            log.info("L'événement " + evenement.getNom() + " sera désormais dans la salle " + cbSalle.getValue().getNom() + ".");
            try {
                evenement.setSalle(cbSalle.getValue());
            }
            catch (CreateException e){
                aucuneErreur = false;
                mainControleur.afficherFenetreErreur(e.getMessage());
            }
        }
        if ((Float.parseFloat(tfPrixTicket.getText()) != evenement.getPrixTickets())){
            log.info("Le prix des tickets de l'événement " + evenement.getNom() + " a été modifié de " + evenement.getPrixTickets() + " à " + Float.parseFloat(tfPrixTicket.getText()) + ".");
            evenement.setPrixTickets(Float.parseFloat(tfPrixTicket.getText()));
        }

        if(aucuneErreur){
            stage.setScene(new Scene(root));
        }
    }

    //Bouton Spec accessoires

    @FXML
    private Button btnValiderAjoutAccessoire;
    @FXML
    private Button btnAjouterAccessoire;
    @FXML
    private ListView<Accessoire> lvAccessoire;
    @FXML
    private Button btnValiderAccessoire;
    @FXML
    private TextField tfNomAccessoire;
    @FXML
    private Button btnRetourAjoutAccessoire;
    @FXML
    private TextField tfGenre;
    @FXML
    private Button btnRetourGenre;
    @FXML
    private Button btnValiderGenre;

    /**
     * Se déclanche lors du clic sur le bouton "modif spec", qui est accessible seulement si l'événement modifié est un concert ou une PDT,
     * Si l'événement est un concert, cela ouvre une page de modif du genre, et si c'est une PDT, cela ouvre une page de modif d'accessoires.
     * @param event
     */
    @FXML
    public void onSpecClick(ActionEvent event){
        try{
            String type = "";
            if(evenement instanceof Concert){
                type="Genre";
            }else {
                type = "Accessoire";
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/Modif"+type+"View.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent
            Scene scene = new Scene(root);

            if(evenement instanceof Concert){
                popupStage.setTitle("Modif genre de : "+evenement.getNom());
                tfGenre.setText(((Concert) evenement).getGenre());
            }else {
                ObservableList<Accessoire> listeAccessoire = FXCollections.observableArrayList(new ArrayList<>(evenement.getAccessoires()));
                lvAccessoire.setItems(listeAccessoire);
                popupStage.setTitle("Ajout à : "+evenement.getNom());
            }

            popupStage.setScene(scene);
            popupStage.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ferme le pop up actuel
     * @param event
     */
    @FXML
    public void onValiderAjoutAccessoireClick(ActionEvent event){
        Stage stage = (Stage) btnValiderAjoutAccessoire.getScene().getWindow();
        stage.close();
    }

    /**
     * Ouvre une nouvelle page permettant de créer et ajouter un nouvel accessoire.
     * @param event
     */
    @FXML
    public void onAjouterAccessoireClick(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/CreationAccessoireView.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent
            Scene scene = new Scene(root);

            popupStage.setScene(scene);
            popupStage.setTitle("Ajout à : "+evenement);
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de valider la creation et l'ajout d'un nouvel accessoire dans une PDT.
     * @param event
     */
    @FXML
    public void onValiderAccessoireClick(ActionEvent event){
        evenement.addAccessoire(tfNomAccessoire.getText());
        ObservableList<Accessoire> listeAccessoire = FXCollections.observableArrayList(new ArrayList<>(evenement.getAccessoires()));
        lvAccessoire.setItems(listeAccessoire);

        Stage stage = (Stage) btnValiderAccessoire.getScene().getWindow();
        stage.close();
    }

    /**
     * Ferme le pop up actuel
     * @param event
     */
    @FXML
    public void onRetourAjoutAccessoireClick(ActionEvent event){
        Stage stage = (Stage) btnRetourAjoutAccessoire.getScene().getWindow();
        stage.close();
    }

    /**
     * Ferme le pop up actuel
     * @param event
     */
    @FXML
    public void onRetourGenreClick(ActionEvent event){
        Stage stage = (Stage) btnRetourGenre.getScene().getWindow();
        stage.close();
    }

    /**
     * Permet de mettre à jour le genre d'un événement
     * @param event
     */
    @FXML
    public void onValiderGenreClick(ActionEvent event){
        if(!Objects.equals(tfGenre.getText(), "")){
            ((Concert) evenement).setGenre(tfGenre.getText());
        }
        Stage stage = (Stage) btnValiderGenre.getScene().getWindow();
        stage.close();
    }

    //=====================================

    /**
     * Permet de valider la suppression d'une personne, check à quelle catégorie elle appartient, puis la supprime de l'événement. Ferme également le pop up
     * @param event
     */
    @FXML
    private void onValiderSuprPersonne(ActionEvent event) {

        if (personne instanceof Spectateur) {
            Spectateur spectateur = (Spectateur) personne;
            evenement.removeTicket(spectateur);
        } else {
            Participant participant = (Participant) personne;
            evenement.removeParticipant(participant);
        }

        btnValiderSupre.setOnAction(this::onValiderSupre);
        log.info("Suppression de " + personne);

        Stage stage = (Stage) btnValiderSupre.getScene().getWindow();
        stage.close();

        Stage popprecedent = (Stage) btnSuprPersonne.getScene().getWindow();
        popprecedent.close();

        initialize();
    }

    /**
     * Fait apparaitre un pop up, permettant de demander à l'utilisateur si il est sur de sa suppréssion.
     * @param event
     */
    @FXML
    private void onSuprPersonne(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/PageValidationSuppressionView.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Stage popupStage = new Stage();

            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png")));
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent

            Scene scene = new Scene(root, 498, 245);
            btnValiderSupre.setOnAction(this::onValiderSuprPersonne);

            popupStage.setScene(scene);
            popupStage.setTitle("Supprimer de " + evenement.getNom() + " ?");
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //modification personne
    @FXML
    private TextField tfNomPersonne, tfPrenomPersonne, tfSalaire;
    @FXML
    private ComboBox<Integer> cbPopularite;
    @FXML
    private Label lbCreation;
    @FXML
    private Button btCreer;

    /**
     * Ouvre un pop up de modification d'une personne, adapté au type de la personne (artiste etc...)
     * @throws IOException
     */
    @FXML
    private void onButtonModifierPersonne() throws IOException {
        FXMLLoader loader;
        Parent root;

        if (personne instanceof Artiste) {
            loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/CreationArtisteView.fxml"));
            loader.setController(this);
            root = loader.load();
            Artiste artiste = (Artiste) personne;

            tfNomPersonne.setText(artiste.getNom());
            tfPrenomPersonne.setText(artiste.getPrenom());
            tfSalaire.setText(String.valueOf(artiste.getSalaire()/ artiste.getPopularite()));
            ObservableList<Integer> options = FXCollections.observableArrayList(1, 2, 3, 4, 5);
            cbPopularite.setItems(options);
            cbPopularite.setValue((int) artiste.getPopularite());
            btCreer.setText("Valider");
            btCreer.setOnAction(event -> onButtonModifierPersonneValider(event, "Artiste", artiste));
            lbCreation.setText("Modification d'un artiste");
        } else if (personne instanceof Personnel) {
            loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/CreationPersonnelView.fxml"));
            loader.setController(this);
            root = loader.load();
            Personnel personnel = (Personnel) personne;

            tfNomPersonne.setText(personnel.getNom());
            tfPrenomPersonne.setText(personnel.getPrenom());
            tfSalaire.setText(String.valueOf(personnel.getSalaire()));
            btCreer.setText("Valider");
            btCreer.setOnAction(event -> onButtonModifierPersonneValider(event, "Personnel", personnel));
            lbCreation.setText("Modification d'un personnel");
        } else {
            loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/CreationSpectateurView.fxml"));
            loader.setController(this);
            root = loader.load();
            tfNomPersonne.setText(personne.getNom());
            tfPrenomPersonne.setText(personne.getPrenom());
            btCreer.setText("Valider");
            btCreer.setOnAction(event -> onButtonModifierPersonneValider(event, "Spectateur", personne));
            lbCreation.setText("Modification d'un spectateur");
        }

        Stage stage = new Stage();
        Stage popupPrecedent = (Stage) btnModifierPersonne.getScene().getWindow();

        stage.setScene(new Scene(root));
        popupPrecedent.close();
        stage.show();
    }

    /**
     * Permet de valider la modification d'une personne, en fonction de son type, modifie ses attributs.
     * @param event
     * @param type le type de personne
     * @param personne la personne modifiée
     */
    private void onButtonModifierPersonneValider(ActionEvent event, String type, Object personne) {
        // Vérifiez le type et effectuez les opérations de modification nécessaires
        if (type.equals("Artiste") && personne instanceof Artiste) {
            Artiste artiste = (Artiste) personne;
            artiste.setNom(tfNomPersonne.getText());
            artiste.setPrenom(tfPrenomPersonne.getText());
            artiste.setSalaire(Float.parseFloat(tfSalaire.getText()));
            artiste.setPopularite(cbPopularite.getValue());
            Stage stage = (Stage) btCreer.getScene().getWindow();
            stage.close();
            initialize();

            log.info("Modification de " + artiste + " réussie");
        } else if (type.equals("Personnel") && personne instanceof Personnel) {
            Personnel personnel = (Personnel) personne;
            personnel.setNom(tfNomPersonne.getText());
            personnel.setPrenom(tfPrenomPersonne.getText());
            personnel.setSalaire(Float.parseFloat(tfSalaire.getText()));
            Stage stage = (Stage) btCreer.getScene().getWindow();
            stage.close();
            initialize();

            log.info("Modification de " + personnel + " réussie");
        } else if (type.equals("Spectateur") && personne != null) {
            Spectateur spectateur = (Spectateur) personne;
            spectateur.setNom(tfNomPersonne.getText());
            spectateur.setPrenom(tfPrenomPersonne.getText());
            Stage stage = (Stage) btCreer.getScene().getWindow();
            stage.close();
            initialize();

            log.info("Modification de " + personne + " réussie");
        } else {
            System.err.println("Type non reconnu ou personne invalide.");
        }
    }

    /**
     * Ouvre une page de modification de la salle actuelle.
     * @throws IOException
     */
    @FXML
    private void onButtonModifierSalle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/CreationSalleView.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        tfNomSalle.setText(salle.getNom());
        tfAdresse.setText(salle.getAdresse());
        tfCapaciteMax.setText(String.valueOf(salle.getCapacite_max()));
        taTags.setText("");
        for(String string : salle.getTags()){
            if(string != salle.getTags().first()){
                taTags.appendText(",");
            }
            taTags.appendText(string);
        }

        btCreer.setText("Valider");
        btCreer.setOnAction(this::onButtonValiderModifierSalle);

        Stage stage = new Stage();
        Stage popupPrecedent = (Stage) btnModifierSalle.getScene().getWindow();

        stage.setScene(new Scene(root));
        popupPrecedent.close();
        stage.show();
    }

    /**
     * Valide la modification d'une salle, en mettant à jour ses attributs. Ferme le pop up.
     * @param event
     */
    @FXML
    private void onButtonValiderModifierSalle(ActionEvent event){
        log.info("Modification réussi. \n Nom :" + salle.getNom() + " -> " + tfNomSalle.getText() + "\n Adresse : " + salle.getAdresse() + " -> " + tfAdresse.getText() + "\n Capacité maximale : " + salle.getCapacite_max() + " -> " + tfCapaciteMax.getText());

        salle.setAdresse(tfAdresse.getText());
        salle.setNom(tfNomSalle.getText());
        salle.setCapacite_max(Integer.parseInt(tfCapaciteMax.getText()));
        salle.setTags(taTags.getText());

        Stage stage= (Stage) btCreer.getScene().getWindow();
        stage.close();

        mainControleur.initialize();
    }

    /**
     * Permet de valider la suppression d'une salle. Et ferme le pop up.
     * @param event
     */
    @FXML
    private void onButtonValiderSuprSalle(ActionEvent event) throws CreateException {
        if(salle.getEvenementsFuturs().size() != 0){
            throw new CreateException("Impossible de supprimer la salle, des événements vont avoir lieu dedans");
        }
        else {
            mainControleur.removeSalle(salle);
            genevent.getSalles().remove(salle);
        log.info(salle.toString()+ "supprimé.");

            Stage stage =(Stage) btnValiderSupre.getScene().getWindow();
            stage.close();

            Stage popupprecedent = (Stage) btnSuprSalle.getScene().getWindow();
            popupprecedent.close();

            mainControleur.initialize();
        }
    }

    /**
     * Ouvre le pop up de validation de la suppression de la salle.
     * @param event
     */
    @FXML
    private void onSuprSalle(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/PageValidationSuppressionView.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Stage popupStage = new Stage();

            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/uga/iut2/genevent/vue/logo/logo-lsm.png")));
            popupStage.initModality(Modality.APPLICATION_MODAL); // Bloc l'interaction avec la fenêtre parent jusqu'à ce que le popup soit fermé
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Définit la fenêtre parent

            Scene scene = new Scene(root, 498, 245);
            btnValiderSupre.setOnAction(event1 -> {
                try {
                    onButtonValiderSuprSalle(event1);
                } catch (CreateException e) {
                    try {
                        mainControleur.afficherFenetreErreur(e.getMessage());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            popupStage.setScene(scene);
            popupStage.setTitle("Supprimer " + salle.getNom() + " ?");
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onPlanningSalle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/uga/iut2/genevent/vue/PlanningSalleView.fxml"));

        loader.setController(this);
        Parent root = loader.load();

        ObservableList<Evenement> listeEvenements = FXCollections.observableArrayList(salle.getHistoriqueEvenements());
        lvEvenementSalle.setItems(listeEvenements);

        lvEvenementSalle.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Evenement selectedEvenement = lvEvenementSalle.getSelectionModel().getSelectedItem();
                if (selectedEvenement != null) {
                    try {
                        mainControleur.ouvrirOptionEvenement(selectedEvenement);
                        Stage stage = (Stage) lvEvenementSalle.getScene().getWindow();
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Stage stageSalle = mainControleur.getStageLvSalle();
        stageSalle.close();

        Stage stage = new Stage();
        stage.setTitle("Planning de la salle");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        Stage popupprecedent = (Stage) btnPlanningSalle.getScene().getWindow();
        popupprecedent.close();
    }


}