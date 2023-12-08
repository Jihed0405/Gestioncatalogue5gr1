package com.poly.gestioncatalogue5gr1.service;

import com.poly.gestioncatalogue5gr1.dao.ProduitRepository;
import com.poly.gestioncatalogue5gr1.entities.Produit;

import lombok.AllArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.lang.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ServiceProduit implements IServiceProduit {
    private ProduitRepository produitRepository;

    @Override
    public void saveProduct(Produit p, MultipartFile mf) throws IOException {
        if (!mf.isEmpty()) {
            p.setNomImage(saveImage(mf));
            produitRepository.save(p);
        }

    }

    @Override
    public List<Produit> getAllProducts() {
        return produitRepository.findAll();
    }

    @Override
    public Page<Produit> getProductsByMC(String mc, Pageable p) {
        return produitRepository.findByNomContains(mc, p);
    }

    @Override
    public List<Produit> getProductsBCat(Long idCat) {
        return produitRepository.getProductsByCat(idCat);
    }

    @Override
    public void deleteProduct(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit getProduct(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    private String saveImage(MultipartFile mf) throws IOException {
        String nomFile = mf.getOriginalFilename();
        String tab[] = nomFile.split("\\.");
        String newName = tab[0] + System.currentTimeMillis() + "." + tab[1];
        File file = new ClassPathResource("static/photos").getFile();
        String chemin = file.getAbsolutePath();
        Path p = Paths.get(chemin, newName);
        Files.write(p, mf.getBytes());
        return newName;
    }
}
