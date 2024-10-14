package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache o) {
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
    public boolean delete(EmployeTache o) {
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
    public boolean update(EmployeTache o) {
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
    public EmployeTache findById(int id) {
        Session session = null;
        EmployeTache et = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            et = (EmployeTache) session.get(EmployeTache.class, id);
            session.getTransaction().commit();
            return et;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return et;
    }

    @Override
    public List<EmployeTache> findAll() {
        Session session = null;
        List<EmployeTache> employeTaches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            employeTaches = session.createQuery("from EmployeTache").list();
            session.getTransaction().commit();
            return employeTaches;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return employeTaches;
    }

    // Méthode pour associer un employé à une tâche
   
    public boolean assignEmployeToTache(int employeId, int tacheId) {
    Session session = null;
    boolean isAssigned = false;
    
    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Requête HQL pour récupérer l'employé et la tâche
        String hql = "FROM Employe e WHERE e.id = :employeId";
        Employe employe = (Employe) session.createQuery(hql)
                                           .setParameter("employeId", employeId)
                                           .uniqueResult();
        
        hql = "FROM Tache t WHERE t.id = :tacheId";
        Tache tache = (Tache) session.createQuery(hql)
                                     .setParameter("tacheId", tacheId)
                                     .uniqueResult();

        // Vérifier si l'employé et la tâche existent
        if (employe != null && tache != null) {
            // Assigner l'employé à la tâche via la table EmployeTache
            EmployeTache employeTache = new EmployeTache(employe, tache);
            session.persist(employeTache);  // Utilisation de persist pour enregistrer l'association

            // Commit des transactions
            session.getTransaction().commit();
            isAssigned = true;  // Si tout se passe bien, la tâche est assignée
            System.out.println("Employé " + employe.getNom() + " a bien été assigné à la tâche : " + tache.getNom());
        } else {
            // Si l'employé ou la tâche n'ont pas été trouvés
            System.out.println("Employé ou tâche non trouvée.");
        }
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

    return isAssigned;  // Retourner true si l'employé a été assigné, sinon false
}



    // Méthode pour dissocier un employé d'une tâche
    public boolean removeEmployeFromTache(Employe employe, Tache tache) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            EmployeTache employeTache = (EmployeTache) session.createQuery(
                "from EmployeTache where employe.id = :empId and tache.id = :tacheId")
                .setParameter("empId", employe.getId())
                .setParameter("tacheId", tache.getId())
                .uniqueResult();
            if (employeTache != null) {
                session.delete(employeTache);
                session.getTransaction().commit();
                return true;
                
            }System.out.println("remove employe .");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    // Méthode pour obtenir toutes les tâches d'un employé
    public List<Tache> getTachesByEmploye(Employe employe) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            EmployeTache employeTache = (EmployeTache) session.createQuery(
                "from EmployeTache where employe.id = :empId")
                .setParameter("empId", employe.getId())
                .list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taches;
    }
}
