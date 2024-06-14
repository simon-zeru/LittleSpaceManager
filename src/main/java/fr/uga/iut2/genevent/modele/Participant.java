package fr.uga.iut2.genevent.modele;

import java.util.ArrayList;

public abstract class Participant extends Personne {

    private ArrayList<Evenement> evenements = new ArrayList<>();
    private float salaire;

    //CONSTRUCTEUR
    public Participant(String nom,String prenom,float salaire){
        super(nom,prenom);
        setSalaire(salaire);
    }
    //SETTER
    /**
     * Définit la valeur du salaire d'un participant. Si le salaire du participant est négatif, alors le salaire est égal à 0.
     * @param salaire Réel à virgule qui représente le salaire du participant.
     */
    public void setSalaire(float salaire) {
        if (salaire<0){
            this.salaire=0;
        }else {
        this.salaire = salaire;}
    }

    //GETTER
    public float getSalaire() {
        return salaire;
    }
    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }
    //METHODE

    /**
     * Ajoute un événement à la liste des événements auxquels participe le participant, en vérifiant s'il n'est pas déjà présent, et informe l'événement qu'il fait partie de ses participants.
     * @param evenement Un événement auquel les participants participent.
     */
    public void addEvenement(Evenement evenement){
        if (!evenements.contains(evenement)) {
            this.evenements.add(evenement);
            evenement.addParticipant(this);
        }
    }

    public void removeEvenement(Evenement evenement){
        if (evenements.contains(evenement)){
            evenement.removeParticipant(this);
        }
    }
}
