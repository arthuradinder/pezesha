package com.arthurprojects.pezesha.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.arthurprojects.pezesha.entity.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    ACCOUNT_VIEW,  // User can view account
                    ACCOUNT_USE,    // User can use account
                    ACCOUNT_UPDATE  // User can update their account
            )
    ),

    ADMIN(
            Set.of(
                    ACCOUNT_CREATE,  // Admin can create account
                    ACCOUNT_UPDATE,  // Admin can update account
                    ACCOUNT_DELETE,  // Admin can delete account
                    ACCOUNT_VIEW,    // Admin can view account
                    USER_CREATE,     // Admin can create users
                    USER_UPDATE,     // Admin can update users
                    USER_DELETE,     // Admin can delete users
                    USER_VIEW        // Admin can view user details
            )
    ),

    MANAGER(
            Set.of(
                    ACCOUNT_VIEW,    // Manager can view accounts
                    ACCOUNT_UPDATE,  // Manager can update accounts
                    USER_VIEW        // Manager can view user details
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        // Map each permission to a SimpleGrantedAuthority and include the role name (e.g., ROLE_USER)
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        // Add the role itself as an authority (e.g., ROLE_USER)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
