package com.poly.gestioncatalogue5gr1.controleur;

import com.poly.gestioncatalogue5gr1.dao.CategorieRepository;
import com.poly.gestioncatalogue5gr1.entities.Produit;
import com.poly.gestioncatalogue5gr1.service.IServiceProduit;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProduitControleur {

    private IServiceProduit serviceProduit;
    private CategorieRepository categorieRepository;

    @GetMapping({ "/user/index" })
    public String getAllProducts(Model m,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "mc", defaultValue = "") String mc) {
        if (page == 0)
            return "redirect:/user/index";
        // List<Produit> liste=serviceProduit.getAllProducts();
        Page<Produit> listePage = serviceProduit.getProductsByMC(mc, PageRequest.of(page - 1, size));
        m.addAttribute("data", listePage.getContent());
        m.addAttribute("pages", new int[listePage.getTotalPages()]);
        m.addAttribute("current", listePage.getNumber());
        m.addAttribute("mc", mc);

        return "vue";
    }

    @GetMapping("/admin/delete/{id}")
    public String deletePRoduct(@PathVariable("id") Long idProduit) {
        serviceProduit.deleteProduct(idProduit);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formproduit")
    public String formAjout(Model m) {
        m.addAttribute("categories", categorieRepository.findAll());
        m.addAttribute("produit", new Produit());
        return "ajouterProduit";
    }

    @PostMapping("/admin/save")
    public String saveProduct(@Valid Produit p, BindingResult bindingResult, @RequestParam("file") MultipartFile mf,
            Model m) throws IOException {
        System.out.println("here are some errors" + bindingResult);
        if (bindingResult.hasErrors()) {
            m.addAttribute("categories", categorieRepository.findAll());
            return "ajouterProduit";
        }
        serviceProduit.saveProduct(p, mf);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/update/{id}")
    public String updateProduit(@PathVariable Long id, Model m) {
        Produit p = serviceProduit.getProduct(id);
        m.addAttribute("produit", p);
        m.addAttribute("categories", categorieRepository.findAll());
        return "ajouterProduit";
    }

    @GetMapping({ "/notAuthorized" })
    public String unauthorized() {
        return "notAuthorized";
    }

}
