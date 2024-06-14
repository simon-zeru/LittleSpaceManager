package fr.uga.iut2.genevent.modele;

import java.util.Date;

public abstract class Evenement {

    //ATTRIBUTIONS

    private int idEvent;
    private String nom;
    private int capaciteParticipants;
    private int capaciteSpectateur;
    private float coutInitial;
    private float prixTickets;
    private Date debut;
    private Date fin;

    //CONSTRUCTEUR(S)

    public Evenement(String nom, int capaciteParticipants, int capaciteSpectateur, float coutInitial, float prixTickets, Date debut, Date fin) {
        this.idEvent=newId();
        this.nom = nom;
        this.capaciteParticipants = capaciteParticipants;
        this.capaciteSpectateur = capaciteSpectateur;
        this.coutInitial = coutInitial;
        this.prixTickets = prixTickets;
        this.debut = debut;
        this.fin = fin;
    }

}
