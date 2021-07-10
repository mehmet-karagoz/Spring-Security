package com.example.SpringSecurityDemo.auth;

import java.util.List;
import java.util.Optional;

import static com.example.SpringSecurityDemo.security.ApplicationUserRole.*;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser>
            selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        String rawPassword = "password";
        return Lists.newArrayList(
                new ApplicationUser(STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode(rawPassword), "memo", true, true,
                        true, true),
                new ApplicationUser(ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode(rawPassword), "linda", true,
                        true, true, true),
                new ApplicationUser(ADMINTRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode(rawPassword), "tom", true, true,
                        true, true));

    }
}