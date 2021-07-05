package com.example.SpringSecurityDemo.security;

import static com.example.SpringSecurityDemo.security.ApplicationUserPermission.COURSE_READ;
import static com.example.SpringSecurityDemo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.SpringSecurityDemo.security.ApplicationUserPermission.STUDENT_READ;
import static com.example.SpringSecurityDemo.security.ApplicationUserPermission.STUDENT_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ,
            COURSE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;

    private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissionList = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(
                        permission.name()))
                .collect(Collectors.toSet());
        permissionList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissionList;
    }
}
