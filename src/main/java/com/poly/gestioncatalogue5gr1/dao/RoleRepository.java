package com.poly.gestioncatalogue5gr1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.gestioncatalogue5gr1.entities.AppRole;

public interface RoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByNom(String nom);
}
