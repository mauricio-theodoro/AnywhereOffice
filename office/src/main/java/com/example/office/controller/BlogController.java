package com.example.office.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BlogController {

    // Mapeia a página principal do blog
    @GetMapping("/blog")
    public String blog() {
        return "blog"; // Serve "blog.html"
    }

    // Mapeia as páginas dos posts individuais
    @GetMapping("/post01")
    public String post01() {
        return "post01"; // Serve "post01.html"
    }

    @GetMapping("/post02")
    public String post02() {
        return "post02"; // Serve "post02.html"
    }

    @GetMapping("/post03")
    public String post03() {
        return "post03"; // Serve "post03.html"
    }

    @GetMapping("/post04")
    public String post04() {
        return "post04"; // Serve "post04.html"
    }

    @GetMapping("/post05")
    public String post05() {
        return "post05"; // Serve "post05.html"
    }

    @GetMapping("/post06")
    public String post06() {
        return "post06"; // Serve "post06.html"
    }

    @GetMapping("/post07")
    public String post07() {
        return "post07"; // Serve "post07.html"
    }
}

