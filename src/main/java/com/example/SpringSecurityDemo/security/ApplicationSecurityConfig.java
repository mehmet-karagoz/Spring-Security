package com.example.SpringSecurityDemo.security;

import static com.example.SpringSecurityDemo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.SpringSecurityDemo.security.ApplicationUserRole.ADMIN;
import static com.example.SpringSecurityDemo.security.ApplicationUserRole.ADMINTRAINEE;
import static com.example.SpringSecurityDemo.security.ApplicationUserRole.STUDENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        String url = "/management/api/**";
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE, url)
                .hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST, url)
                .hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.PUT, url)
                .hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.GET, url)
                .hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()).anyRequest()
                .authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails mehmet = User.builder().username("mehmet")
                .password(passwordEncoder.encode("password"))
                .authorities(STUDENT.getGrantedAuthorities()).build();

        UserDetails melis = User.builder().username("melis")
                .password(passwordEncoder.encode("1234"))
                .authorities(ADMIN.getGrantedAuthorities()).build();
        UserDetails tom = User.builder().username("tom")
                .password(passwordEncoder.encode("1234"))
                .authorities(ADMINTRAINEE.getGrantedAuthorities()).build();
        return new InMemoryUserDetailsManager(mehmet, melis, tom);
    }

}
