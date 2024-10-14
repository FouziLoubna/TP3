package ma.projet.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nom;
    private String dateDebut;
    private String dateFin;
    
    @OneToMany(mappedBy = "projet", fetch = FetchType.LAZY)
    private Set<Tache> taches;

    @OneToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefDeProjet;

    public Projet() {
    }

    public Projet(String nom, String dateDebut, String dateFin, Set<Tache> taches, Employe chefDeProjet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.taches = taches;
        this.chefDeProjet = chefDeProjet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public void setTaches(Set<Tache> taches) {
        this.taches = taches;
    }

    public Employe getChefDeProjet() {
        return chefDeProjet;
    }

    public void setChefDeProjet(Employe chefDeProjet) {
        this.chefDeProjet = chefDeProjet;
    }

    
}
