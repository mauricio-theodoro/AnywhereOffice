package com.example.office.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    // Mapeia a página principal do blog
    @GetMapping("/profile")
    public String profile() {
        return "profile"; // Serve "profile.html"
    }
}

