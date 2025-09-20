package com.inter.SistemaDeVisitas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String root() {
    // opcional: pode redirecionar para /login
    return "login";
    // ou use: return "redirect:/login";
  }

  @GetMapping("/login")
  public String login() {
    // renderiza templates/login.html
    return "login";
  }

  @GetMapping("/home")
  public String home() {
    // view protegida por login
    return "home";
  }
}
