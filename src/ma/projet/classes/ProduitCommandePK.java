/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.classes;

import java.io.Serializable;
import javax.persistence.Embeddable;


@Embeddable
public class ProduitCommandePK implements Serializable{
    
    private int produit;
    private int commande;
    private int quantite;

    public ProduitCommandePK() {
    }

    public ProduitCommandePK(int produit, int commande, int quantite) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
    }

    public int getProduit() {
        return produit;
    }

    public void setProduit(int produit) {
        this.produit = produit;
    }

    public int getCommande() {
        return commande;
    }

    public void setCommande(int commande) {
        this.commande = commande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

  
    
}
