package com.poly.gestioncatalogue5gr1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.gestioncatalogue5gr1.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {
    public AppUser findAppUserByUserName(String userName);
}
