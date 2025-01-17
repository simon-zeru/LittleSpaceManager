package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.LittleSpaceManager_Utilitaire;

import java.io.Serializable;

/**
 * Cette Class permet de créer des objets de type Personne. Cette class est abstraite car nous l'utilisons pour créer des Spectateurs et des Participants.
 */
public abstract class Personne implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final GenEvent genevent;
    private int identifiant;
    private String nom;
    private String prenom;

    //CONSTRUCTEUR

    /**
     * Pour créer une personne, il faut renseigner son nom et son prenom. L'id de la personne est initialisé automatiquement.
     * @param nom le nom de la personne
     * @param prenom le prénom de la personne
     */
    public Personne(GenEvent genevent,String nom, String prenom) {
        this.genevent=genevent;
        setIdentifiant();
        setNom(nom);
        setPrenom(prenom);
    }

    //SETTER

    /**
     * Créer un identifiant unique pour la personne.
     */
    public void setIdentifiant() {
        identifiant = LittleSpaceManager_Utilitaire.newId();
    }

    /**
     * Uniformiser le nom pour qu'il soit en minuscule, sauf la première lettre qui sera en majuscule.
     *
     * @param nom chaine de caractère formant le nom.
     */
    public void setNom(String nom) {
        this.nom = nom.toUpperCase();
    }

    /**
     * Uniformiser le prénom pour qu'il soit en minuscule, sauf la première lettre qui sera en majuscule.
     *
     * @param prenom chaine de caractère formant le prénom.
     */
    public void setPrenom(String prenom) {
        this.prenom = LittleSpaceManager_Utilitaire.capitalize(prenom);
    }

    //GUETTER

    /**
     * Cette fonction renvoie l'identifiant de la Personne.
     * @return l'identifiant unique
     */
    public int getIdentifiant() {
        return identifiant;
    }
    /**
     * Cette fonction renvoie le nom de la Personne.
     * @return le nom de famille
     */

    public String getNom() {
        return nom;
    }
    /**
     * Cette fonction renvoie le penom de la Personne.
     * @return
     */

    public String getPrenom() {
        return prenom;
    }
    /**
     * Cette fonction permet d'afficher le nom et le prenom d'une Personne lors de l'appel de l'objet.
     * @return
     */

    /**
     * Fonction d'affichage d'une peersonne
     * @return nom - prenom
     */
    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
