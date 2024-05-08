package com.example.controller;

import com.example.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    public HomeController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "index";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/home")
    public String home(Model model) {
        UserDetails u = userDetailsService.getCurrentUserDetails();
        model.addAttribute("message", u.getUsername());
        return "pages/home";
    }
}
