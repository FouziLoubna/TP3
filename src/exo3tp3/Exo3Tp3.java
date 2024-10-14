/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exo3tp3;
import java.text.SimpleDateFormat;
 import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;

import java.util.Date;


public class Exo3Tp3 {
    public static void main(String[] args) {
        // Créer des instances de service
        ProjetService projetService = new ProjetService();
        EmployeService employeService = new EmployeService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        // Créer des projets
        Projet projet1 = new Projet();
        projet1.setNom("Gestion de stock");
        projet1.setDateDebut("14 Janvier 2013"); // Date début: 14 Janvier 2013
        projetService.create(projet1);

        Projet projet2 = new Projet();
        projet2.setNom("Développement d'application");
        projet2.setDateDebut("5 Mars 2013"); // Date début: 5 Mars 2013
        projetService.create(projet2);

        // Créer des tâches
        Tache tache1 = new Tache();
        tache1.setNom("Analyse");
        tache1.setDateDebut("10 Février 2013"); // Date début réelle: 10 Février 2013
        tache1.setDateFin("20 Février 2013"); // Date fin réelle: 20 Février 2013
        tache1.setPrix(1500);
        tacheService.create(tache1);

        Tache tache2 = new Tache();
        tache2.setNom("Conception");
        tache2.setDateDebut("10 Mars 2013"); // Date début réelle: 10 Mars 2013
        tache2.setDateFin( "15 Mars 2013"); // Date fin réelle: 15 Mars 2013
        tache2.setPrix(1200);
        tacheService.create(tache2);

        Tache tache3 = new Tache();
        tache3.setNom("Développement");
        tache3.setDateDebut("10 Avril 2013"); // Date début réelle: 10 Avril 2013
        tache3.setDateFin( "25 Avril 2013"); // Date fin réelle: 25 Avril 2013
        tache3.setPrix(800);
        tacheService.create(tache3);

        // Créer des employés
        Employe employe1 = new Employe();
        employe1.setNom("Ali");
        employe1.setPrenom("Mohammed");
        employeService.create(employe1);

        Employe employe2 = new Employe();
        employe2.setNom("Sara");
        employe2.setPrenom("Amina");
        employeService.create(employe2);

        // Créer des relations EmployeTache
        EmployeTache employeTache1 = new EmployeTache();
        employeTache1.setEmploye(employe1);
        employeTache1.setTache(tache1);
        employeTache1.setDateFinReelle("14 Avril 2013");
        employeTacheService.create(employeTache1);

        EmployeTache employeTache2 = new EmployeTache();
        employeTache2.setEmploye(employe2);
        employeTache2.setTache(tache2);
        employeTache2.setDateFinReelle("1 Avril 2013");
        employeTacheService.create(employeTache2);

        EmployeTache employeTache3 = new EmployeTache();
        employeTache3.setEmploye(employe1);
        employeTache3.setTache(tache3);
        employeTache3.setDateFinReelle("10 Avril 2013");
        employeTacheService.create(employeTache3);

        // 1. Afficher les tâches réalisées par un employé (Exemple: Employé 1)
        employeService.findTachesByEmploye(1);

        // 2. Afficher les projets gérés par un employé (Exemple: Employé 1)
        employeService.afficherProjetsPourEmploye(1);

        // 3. Afficher les tâches planifiées pour un projet (Exemple: Projet 1)
        System.out.println("Tâches planifiées pour le projet : " + projet1.getNom());
        projetService.getPlannedTasksByProjet(1).forEach(tache -> System.out.println(tache.getNom()));

        // 4. Afficher les tâches réalisées dans un projet (Exemple: Projet 1)
        System.out.println("\nTâches réalisées pour le projet : " + projet1.getNom());
        projetService.displayCompletedTasksByProjet(1);

        // 5. Afficher les tâches dont le prix est supérieur à 1000 DH
        System.out.println("\nTâches avec un prix supérieur à 1000 DH :");
        tacheService.findTasksWithPriceAbove1000().forEach(tache -> System.out.println(tache.getNom() + " " + tache.getPrix()));

        // 6. Afficher les tâches réalisées entre deux dates
        // Création du format de date pour passer les dates sous forme de chaîne
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Convertir les dates au format String
        String dateStart = "01/02/2013";  // 1er Février 2013
        String dateEnd = "31/03/2013";    // 31 Mars 2013

        // Afficher les tâches réalisées entre 1er Février 2013 et 31 Mars 2013
        System.out.println("\nTâches réalisées entre 1er Février 2013 et 31 Mars 2013 :");
         tacheService.findTachesBetweenDates(dateStart, dateEnd).forEach((Tache) -> {
            //Assurez-vous que vous accédez à la tâche et aux dates de l'instance EmployeTache
            System.out.println("Tâche: " + Tache.getNom() + 
                             " - Date Début Réelle: " + Tache.getDateDebut() + 
                              " - Date Fin Réelle: " +Tache.getDateFin());
        });
}
    }
    