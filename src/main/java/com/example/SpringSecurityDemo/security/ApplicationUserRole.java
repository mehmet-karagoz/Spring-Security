package com.example.SpringSecurityDemo.security;

import static com.example.SpringSecurityDemo.security.ApplicationUserPermission.*;

import java.util.Set;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()), ADMIN(Sets.newHashSet(STUDENT_READ,
            STUDENT_WRITE, COURSE_READ, COURSE_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

}
