package com.poly.gestioncatalogue5gr1;

import com.poly.gestioncatalogue5gr1.dao.CategorieRepository;
import com.poly.gestioncatalogue5gr1.dao.ProduitRepository;
import com.poly.gestioncatalogue5gr1.entities.Categorie;
import com.poly.gestioncatalogue5gr1.entities.Produit;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
@AllArgsConstructor
public class GestionCatalogue5Gr1Application implements CommandLineRunner {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    public static void main(String[] args) {

        SpringApplication.run(GestionCatalogue5Gr1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
         * categorieRepository.save(new Categorie(null, "informatique", null));
         * categorieRepository.save(Categorie.builder().nom("electronique").build());
         * produitRepository.save(new Produit(null, "imprimante", 500, 10,
         * categorieRepository.findById(1L).get()));
         * produitRepository.save(new Produit(null, "machine a laver", 500, 10,
         * categorieRepository.findById(2L).get()));
         * produitRepository.save(new Produit(null, "clavier", 50, 100,
         * categorieRepository.findById(1L).get()));
         * produitRepository.save(new Produit(null, "souris", 50, 100,
         * categorieRepository.findById(1L).get()));
         */
    }
}
