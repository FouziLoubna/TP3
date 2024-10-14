package ma.projet.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String telephone;

    @ManyToMany(mappedBy = "employes")
    private Set<Tache> taches;

    @OneToMany(mappedBy = "chefDeProjet", fetch = FetchType.LAZY)
    private Set<Projet> projets;

    public Employe() {
    }

    public Employe(String nom, String prenom, String telephone, Set<Tache> taches, Set<Projet> projets) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.taches = taches;
        this.projets = projets;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public void setTaches(Set<Tache> taches) {
        this.taches = taches;
    }

    public Set<Projet> getProjets() {
        return projets;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }

    
}

