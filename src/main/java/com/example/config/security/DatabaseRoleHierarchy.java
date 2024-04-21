package com.example.config.security;

import com.example.models.security.Role;
import com.example.repositories.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DatabaseRoleHierarchy implements RoleHierarchy {
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseRoleHierarchy(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Map<String, Collection<GrantedAuthority>> rolesReachableInOneStepMap = new HashMap<>();
        Map<String, Collection<GrantedAuthority>> rolesReachableInOneOrMoreStepsMap = new HashMap<>();

        // Fetch roles and their children from the database
        List<Role> rolesWithChildren = roleRepository.findAllWithChildren();

        // Build the hierarchy maps
        for (Role role : rolesWithChildren) {
            String roleName = role.getName();
            Collection<GrantedAuthority> authoritiesForRole = authorities.stream()
                    .filter(ga -> ga.getAuthority().equals(roleName))
                    .collect(Collectors.toList());

            rolesReachableInOneStepMap.put(roleName, authoritiesForRole);

            // For roles reachable in one or more steps, add the role itself and its children
            rolesReachableInOneOrMoreStepsMap.put(roleName, new ArrayList<>(authoritiesForRole));
            for (Role child : role.getChildren()) {
                rolesReachableInOneOrMoreStepsMap.get(roleName).addAll(rolesReachableInOneOrMoreStepsMap.getOrDefault(child.getName(), Collections.emptyList()));
            }
        }

        // Combine the reachable authorities from both maps
        Collection<GrantedAuthority> reachableAuthorities = new HashSet<>();
        reachableAuthorities.addAll(rolesReachableInOneStepMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        reachableAuthorities.addAll(rolesReachableInOneOrMoreStepsMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));

        return reachableAuthorities;
    }

}
