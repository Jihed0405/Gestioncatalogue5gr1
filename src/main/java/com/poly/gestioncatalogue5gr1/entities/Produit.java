package com.poly.gestioncatalogue5gr1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String nom;
    @Min(1)
    private double prix;
    @Min(0)
    private int quantite;
    private String nomImage;
    @ManyToOne
    @NotNull(message = "la catégorie ne doit pas etre null")
    private Categorie categorie;

    @Override
    public String toString() {
        return "Produit [id=" + id + ", nom=" + nom + ", prix=" + prix + ", quantite=" + quantite + ", categorie="
                + categorie.toString() + "]";
    }

}
