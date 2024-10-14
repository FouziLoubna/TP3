package ma.projet.classes;

import javax.persistence.*;

@Entity
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK pk;

    @ManyToOne
    @MapsId("employeId")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")
    private Tache tache;

    private String dateDebutReelle;
    private String dateFinReelle;

    // Getters et Setters

    public EmployeTache() {
    }

    public EmployeTache(Employe employe, Tache tache) {
        this.employe = employe;
        this.tache = tache;
    }

    public EmployeTache(EmployeTachePK pk, Employe employe, Tache tache, String dateDebutReelle, String dateFinReelle) {
        this.pk = pk;
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    public EmployeTachePK getPk() {
        return pk;
    }

    public void setPk(EmployeTachePK pk) {
        this.pk = pk;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public String getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(String dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public String getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(String dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }
    
}

