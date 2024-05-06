package com.example.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/customers")
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }
}
