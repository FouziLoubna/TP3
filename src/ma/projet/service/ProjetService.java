package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.util.HibernateUtil;

import java.util.List;
import ma.projet.classes.EmployeTache;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
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
    public boolean delete(Projet o) {
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
    public boolean update(Projet o) {
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
    public Projet findById(int id) {
        Session session = null;
        Projet p = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            p = (Projet) session.get(Projet.class, id);
            session.getTransaction().commit();
            return p;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return p;
    }

    @Override
    public List<Projet> findAll() {
        Session session = null;
        List<Projet> projets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            projets = session.createQuery("from Projet").list();
            session.getTransaction().commit();
            return projets;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return projets;
    }

    // Méthode pour afficher la liste des tâches planifiées pour un projet
   public List<Tache> getPlannedTasksByProjet(int projetId) {
    Session session = null;
    List<Tache> taches = null;
    
    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Requête HQL pour récupérer les tâches d'un projet
        String hql = "FROM Tache t WHERE t.projet.id = :projetId";
        taches = session.createQuery(hql)
                        .setParameter("projetId", projetId)
                        .list();

        // Commit des transactions
        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();  // Afficher les erreurs si elles surviennent
        if (session != null) {
            session.getTransaction().rollback();  // Annuler les changements en cas d'erreur
        }
    } finally {
        if (session != null) {
            session.close();  // Fermer la session dans le bloc finally
        }
    }

    return taches;  // Retourner la liste des tâches
}

    // Méthode pour afficher les tâches réalisées dans un projet
    public void displayCompletedTasksByProjet(int projetId) {
    Session session = null;
    
    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // Requête HQL pour récupérer les tâches réalisées liées à un projet
        String hql = "SELECT et FROM EmployeTache et JOIN et.tache t WHERE et.projet.id = :projetId AND et.dateFinReelle IS NOT NULL";
        List<EmployeTache> employeTaches = session.createQuery(hql)
                                                   .setParameter("projetId", projetId)
                                                   .list();

        // Affichage des tâches réalisées
        if (!employeTaches.isEmpty()) {
            System.out.println("Liste des tâches réalisées dans le projet " + projetId + ":");
            for (EmployeTache employeTache : employeTaches) {
                Tache tache = employeTache.getTache();
                System.out.println("Tâche: " + tache.getNom() + " | Date début réelle: " + employeTache.getDateDebutReelle() +
                                   " | Date fin réelle: " + employeTache.getDateFinReelle());
            }
        } else {
            System.out.println("Aucune tâche réalisée trouvée dans le projet.");
        }

        // Commit des transactions
        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();  // Afficher les erreurs si elles surviennent
        if (session != null) {
            session.getTransaction().rollback();  // Annuler les changements en cas d'erreur
        }
    } finally {
        if (session != null) {
            session.close();  // Fermer la session dans le bloc finally
        }
    }
}

}
