
package ma.projet.service;
import java.util.ArrayList;
import java.util.Date;
import ma.projet.dao.IDao;
import ma.projet.classes.Commande;
import java.util.List;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import  ma.projet.util.HibernateUtil;
import org.hibernate.Query;
 
public class CommandeService implements IDao<Commande> {


    @Override
    public boolean create(Commande o) {
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
    public boolean delete(Commande o) {
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
    public boolean update(Commande o) {
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
    public Commande findById(int id) {
        Session session = null;
        Commande e  = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Commande) session.get(Commande.class, id);
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
    public List<Commande> findAll() {
        Session session = null;
        List<Commande>  cc = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            cc = session.createQuery("from Commande").list();
            session.getTransaction().commit();
            return cc;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return cc ;
    }
  

    // Méthode simple pour afficher les produits dans une commande donnée
   
    public List<Produit> afficherProduitsDansCommande(int commandeId) {
    Session session = null;
    List<Produit> produits = null;
    List<LigneCommandeProduit> lignes = null;
    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        lignes = session.createQuery("FROM LigneCommandeProduit l WHERE l.commande.id = :commandeId")
                .setParameter("commandeId", commandeId)
                .list();

        // Récupérer les produits depuis les lignes de commande
        produits = new ArrayList<>();
        for (LigneCommandeProduit ligne : lignes) {
            produits.add(ligne.getProduit());
        }

        // Affichage des produits
        System.out.println("Produits de la commande " + commandeId + ":");
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

  
    

    



