package com.poly.gestioncatalogue5gr1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.poly.gestioncatalogue5gr1.service.IServiceAccount;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        // httpSecurity.formLogin(formlogin ->
        // formlogin.loginPage("/login").permitAll());
        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/user/**").hasAuthority("USER"));
        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/admin/**").hasAuthority("ADMIN"));
        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        httpSecurity.formLogin(formlogin -> formlogin.permitAll())
                .csrf((csrf) -> csrf.disable());
        httpSecurity.userDetailsService(userDetailsService);
        httpSecurity.exceptionHandling(exception -> exception.accessDeniedPage("/notAuthorized"));
        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder().encode("123456")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234567")).roles("USER", "ADMIN")
                        .build()

        );
    }

    /*
     * @Bean
     * public CommandLineRunner commandUser(IServiceAccount serviceAccount) {
     * return args -> {
     * serviceAccount.addRole("ADMIN");
     * serviceAccount.addRole("USER");
     * serviceAccount.addUser("user", "12345", "user@gmail.com");
     * serviceAccount.addUser("user1", "123456", "user1@gmail.com");
     * serviceAccount.addUser("admin", "123456789", "admin@gmail.com");
     * serviceAccount.addRoleToUser("user", "USER");
     * serviceAccount.addRoleToUser("user1", "USER");
     * serviceAccount.addRoleToUser("admin", "ADMIN");
     * serviceAccount.addRoleToUser("admin", "USER");
     * };
     * }
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
