
package ma.projet.service;
import java.util.ArrayList;
import java.util.Date;
import ma.projet.dao.IDao;
import ma.projet.classes.Produit;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import  ma.projet.util.HibernateUtil;
import ma.projet.classes.LigneCommandeProduit;
public class ProduitService implements IDao<Produit> {


    @Override
    public boolean create(Produit o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(Produit o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public boolean update(Produit o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public Produit findById(int id) {
        Session session = null;
        Produit e  = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Produit) session.get(Produit.class, id);
            session.getTransaction().commit();
            return e;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return e;
    }

    @Override
    public List<Produit> findAll() {
        Session session = null;
        List<Produit>  pr = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            pr = session.createQuery("from Produit").list();
            session.getTransaction().commit();
            return pr;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return pr;
    }
      public List<Produit> findByCategorie(int categorieId) {
    Session session = null;
    List<Produit> produits = null;

    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();

        // Requête HQL pour récupérer les produits d'une catégorie donnée
        produits = session.createQuery(
                "FROM Produit p WHERE p.categorie.id = :categorieId")
                .setParameter("categorieId", categorieId)
                .list();

        // Affichage des produits
        System.out.println("Produits de la catégorie " + categorieId + ":");
        for (Produit produit : produits) {
            System.out.printf("Référence: %s, Prix: %.2f DH%n", produit.getReference(), produit.getPrix());
        }
    } catch (Exception e) {
        e.printStackTrace();  // Afficher les erreurs si elles surviennent
    } finally {
        if (session != null) {
            session.close();  // Fermer la session dans le bloc finally
        }
    }

    return produits;  // Retourner la liste des produits
}
    

    // Méthode pour trouver les produits par catégorie
      // Retourner la liste des produits ou null si une erreur est survenue
    
    public List<Produit> findProduitsCommandesBetweenDates(Date debut, Date fin) {
    Session session = null;
    List<Produit> produits = null;

    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Requête HQL pour récupérer les produits commandés entre deux dates
        List<LigneCommandeProduit> lignes = session.createQuery(
                "FROM LigneCommandeProduit l WHERE l.commande.dateCommande BETWEEN :debut AND :fin")
                .setParameter("debut", debut)
                .setParameter("fin", fin)
                .list();
        // Récupérer les produits depuis les lignes de commande
        produits = new ArrayList<>();
        for (LigneCommandeProduit ligne : lignes) {
            produits.add(ligne.getProduit());
        }
        // Affichage des produits
        System.out.println("Produits commandés entre " + debut + " et " + fin + ":");
        for (Produit produit : produits) {
            System.out.printf("Référence: %s, Prix: %.2f DH%n", produit.getReference(), produit.getPrix());
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

    return produits;  // Retourner la liste des produits
}

  
public List<Produit> trouverProduitsAvecPrixSuperieurA100() {
    Session session = null;
    List<Produit> produits = null;

    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Requête HQL pour récupérer les produits dont le prix est supérieur à 100 DH
        produits = session.createQuery(
                "FROM Produit p WHERE p.prix > 100")
                .list();

        // Affichage des produits
        System.out.println("Produits avec un prix supérieur à 100 DH:");
        for (Produit produit : produits) {
            System.out.printf("Référence: %s, Prix: %.2f DH%n", produit.getReference(), produit.getPrix());
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

    return produits;  // Retourner la liste des produits
}

}





