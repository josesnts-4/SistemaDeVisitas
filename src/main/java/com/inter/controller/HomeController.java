// src/main/java/com/inter/SistemaDeVisitas/controller/HomeController.java
package com.inter.SistemaDeVisitas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")      public String root()  { return "login"; }
  @GetMapping("/login") public String login() { return "login"; }
  @GetMapping("/home")  public String home()  { return "home"; }
}
