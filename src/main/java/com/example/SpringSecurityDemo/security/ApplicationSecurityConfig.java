package com.example.SpringSecurityDemo.security;

import static com.example.SpringSecurityDemo.security.ApplicationUserRole.STUDENT;

import com.example.SpringSecurityDemo.auth.ApplicationUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
            ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // String url = "/management/api/**";
        // .antMatchers(HttpMethod.DELETE, url)
        // .hasAuthority(COURSE_WRITE.getPermission())
        // .antMatchers(HttpMethod.POST, url)
        // .hasAuthority(COURSE_WRITE.getPermission())
        // .antMatchers(HttpMethod.PUT, url)
        // .hasAuthority(COURSE_WRITE.getPermission())
        // .antMatchers(HttpMethod.GET, url)
        // .hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())

        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()).anyRequest()
                .authenticated().and().formLogin().loginPage("/login")
                .permitAll().defaultSuccessUrl("/courses", true);
        // .and().rememberMe()
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
