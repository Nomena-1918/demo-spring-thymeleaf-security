package com.example.controller;

import com.example.services.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        UserDetails u = userDetailsService.getCurrentUserDetails();
        model.addAttribute("message", "User connecté : "+u.getUsername()+"\n\n"+session.toString());
        return "pages/home";
    }
}
