package com.example.models.security;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "children", fetch = FetchType.LAZY)
    private Set<Role> parents = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_hierarchy",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    private Set<Role> children = new HashSet<>();

    // Getters and setters
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public Role setRole(String role) {
        this.role = role;
        return this;
    }

    public Set<Role> getChildren() {
        return children;
    }

    public Role setChildren(Set<Role> children) {
        this.children = children;
        return this;
    }

    public Set<Role> getParents() {
        return parents;
    }

    public Role setParents(Set<Role> parents) {
        this.parents = parents;
        return this;
    }
}
