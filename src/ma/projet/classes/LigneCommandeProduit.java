/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.classes;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author a
 */

@Entity
public class LigneCommandeProduit {
    
    @EmbeddedId
    private ProduitCommandePK pk;
    @ManyToOne
    @JoinColumn(name = "commande", insertable = false, updatable = false)
    private Commande commande;
    @ManyToOne
    @JoinColumn(name = "produit", insertable = false, updatable = false)
    private Produit produit;
    @Column(name = "quantite", insertable = false, updatable = false)
    private int quantite;

    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public ProduitCommandePK getPk() {
        return pk;
    }

    public void setPk(ProduitCommandePK pk) {
        this.pk = pk;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

   
    
}
