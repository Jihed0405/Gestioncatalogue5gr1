package com.poly.gestioncatalogue5gr1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        // httpSecurity.formLogin(formlogin ->
        // formlogin.loginPage("/login").permitAll());
        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/user/**").hasRole("USER"));
        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/admin/**").hasRole("ADMIN"));
        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        httpSecurity.formLogin(formlogin -> formlogin.permitAll())
                .csrf((csrf) -> csrf.disable());
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

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
