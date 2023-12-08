package com.poly.gestioncatalogue5gr1.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.poly.gestioncatalogue5gr1.dao.RoleRepository;
import com.poly.gestioncatalogue5gr1.dao.UserRepository;
import com.poly.gestioncatalogue5gr1.entities.AppRole;
import com.poly.gestioncatalogue5gr1.entities.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ServiceAccount implements IServiceAccount, UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findAppUserByUserName(username);
        if (appUser == null)
            throw new UsernameNotFoundException("User not found");
        List<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(e -> authorities.add(new SimpleGrantedAuthority(e.getNom())));
        return new User(appUser.getUserName(), appUser.getPassword(), authorities);
    }

    @Override
    public void addUser(String userName, String password, String mail) {
        AppUser user = userRepository.findAppUserByUserName(userName);
        // if (user != null) {
        // throw new RuntimeException("cet utilisateur existe");
        // }
        userRepository.save(AppUser.builder().id(UUID.randomUUID().toString())
                .userName(userName).email(mail).password(passwordEncoder.encode(password)).build());
    }

    @Override
    public void addRole(String nom) {
        AppRole role = roleRepository.findByNom(nom);
        // if (role != null) {
        // throw new RuntimeException("ce role existe");
        // }

        roleRepository.save(AppRole.builder().nom(nom).build());

    }

    @Override
    public void addRoleToUser(String userName, String nameRole) {
        AppUser user = userRepository.findAppUserByUserName(userName);
        AppRole role = roleRepository.findByNom(nameRole);

        user.getRoles().add(role);
    }

    @Override
    public void deleteRoleToUser(String userName, String nameRole) {

    }

    @Override
    public AppUser getAppUser(String userName) {
        return userRepository.findAppUserByUserName(userName);
    }

}
