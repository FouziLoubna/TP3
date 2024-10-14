package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Tache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean delete(Tache o) {
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
    public boolean update(Tache o) {
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
    public Tache findById(int id) {
        Session session = null;
        Tache t = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            t = (Tache) session.get(Tache.class, id);
            session.getTransaction().commit();
            return t;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return t;
    }

    @Override
    public List<Tache> findAll() {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            taches = session.createQuery("from Tache").list();
            session.getTransaction().commit();
            return taches;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taches;
    }

    // Méthode pour afficher les tâches avec un prix supérieur à 1000 DH
    

    public List<Tache> findTasksWithPriceAbove1000() {
        Session session = null;
        List<Tache> tasks = null;

        try {
            // Ouvrir la session
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            // Requête HQL pour récupérer les tâches dont le prix est supérieur à 1000
            tasks = session.createQuery("FROM Tache t WHERE t.prix > 1000")
                           .list();

            // Affichage des tâches
            System.out.println("Tâches avec un prix supérieur à 1000 DH:");
            for (Tache tache : tasks) {
                System.out.printf("Nom: %s, Prix: %.2f DH%n", tache.getNom(), tache.getPrix());
            }

            // Commit des transactions
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();  // Afficher les erreurs si elles surviennent
        } finally {
            if (session != null) {
                session.close();  // Fermer la session dans le bloc finally
            }
        }

        return tasks;  // Retourner la liste des tâches
    }

    // Méthode pour afficher les tâches réalisées entre deux dates
    public List<Tache> findTachesBetweenDates(String dateStart, String dateEnd) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            taches = session.createQuery("from Tache where dateDebutReelle between :start and :end")
                    .setParameter("start", dateStart).setParameter("end", dateEnd).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taches;
    }
}
