package ma.projet.service;

import java.util.ArrayList;
import ma.projet.dao.IDao;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.util.HibernateUtil;

import java.util.List;
import ma.projet.classes.EmployeTache;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(Employe o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean update(Employe o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Employe findById(int id) {
        Session session = null;
        Employe e = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Employe) session.get(Employe.class, id);
            session.getTransaction().commit();
            return e;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return e;
    }

    @Override
    public List<Employe> findAll() {
        Session session = null;
        List<Employe> employes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            employes = session.createQuery("from Employe").list();
            session.getTransaction().commit();
            return employes;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return employes;
    }

    // Méthode pour afficher les projets assignés à un employé
    public List<Projet> afficherProjetsPourEmploye(int employeId) {
    Session session = null;
    List<Projet> projets = null;
    List<EmployeTache> et = null;
    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Requête pour obtenir les lignes de tâches associées à l'employé donné
        et = session.createQuery("FROM EmployeTache e WHERE e.employe.id = :employeId")
                .setParameter("employeId", employeId)
                .list();

        // Récupérer les projets depuis les lignes de tâches
        projets = new ArrayList<>();
        for (EmployeTache employeTache : et) {
        Projet projet = employeTache.getTache().getProjet(); // Supposons que getTache() renvoie une tâche, qui a un projet.
    if (projet != null) {
        projets.add(projet);
    }
 }
        // Commit des transactions
        session.getTransaction().commit();
    } catch (HibernateException e) {
        // Rollback en cas d'exception
        session.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        if (session != null) {
            session.close();  // Fermer la session
        }
    }

    // Retourner la liste des projets
    return projets;
}


    // Méthode pour afficher les tâches assignées à un employé
    public List<Tache> findTachesByEmploye(int employeId) {
    Session session = null;
    List<Tache> taches = null;
    List<EmployeTache> lignes = null;
    
    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Récupérer les lignes correspondant à l'employé
        lignes = session.createQuery("FROM EmployeTache et WHERE et.employe.id = :employeId")
                .setParameter("employeId", employeId)
                .list();

        // Récupérer les tâches associées à l'employé
        taches = new ArrayList<>();
        for (EmployeTache ligne : lignes) {
            taches.add(ligne.getTache());
        }

        // Afficher les tâches réalisées par l'employé
        System.out.println("Tâches réalisées par l'employé " + employeId + ":");
        for (Tache tache : taches) {
            System.out.printf("Tâche: %s, Date Début: %s, Date Fin: %s%n",
                    tache.getNom(), tache.getDateDebut(), tache.getDateFin());
        }

        // Commit des transactions
        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();  // Afficher les erreurs si elles surviennent
    } finally {
        if (session != null) {
            session.close();  // Fermer la session dans le bloc finally
        }
    

    return taches;  // Retourner la liste des tâches
}

}
    }