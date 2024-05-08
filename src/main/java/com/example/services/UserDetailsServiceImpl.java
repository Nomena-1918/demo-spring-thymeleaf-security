package com.example.services;

import com.example.models.security.CustomUser;
import com.example.repositories.security.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomUserRepository customUserRepository;

    @Autowired
    public UserDetailsServiceImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = customUserRepository.findFirstByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        GrantedAuthority g = new SimpleGrantedAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Set.of(g));
    }

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}

