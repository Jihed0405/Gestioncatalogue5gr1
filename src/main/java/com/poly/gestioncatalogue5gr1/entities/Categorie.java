package com.poly.gestioncatalogue5gr1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    List<Produit> produits;

    @Override
    public String toString() {
        return "Categorie [id=" + id + ", nom=" + nom + "]";
    }

}
