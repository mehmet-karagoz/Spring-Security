package com.example.SpringSecurityDemo.security;

import static com.example.SpringSecurityDemo.security.ApplicationUserRole.ADMIN;
import static com.example.SpringSecurityDemo.security.ApplicationUserRole.STUDENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll().antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest().authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails mehmet = User.builder().username("mehmet")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name()).build();

        UserDetails melis = User.builder().username("melis")
                .password(passwordEncoder.encode("1234")).roles(ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(mehmet, melis);
    }

}
