package com.example.controller;

import com.example.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/accueil")
    public String index(Model model) {
        UserDetails u = userDetailsService.getCurrentUserDetails();
        model.addAttribute("message", u.toString());
        return "accueil";
    }
}
