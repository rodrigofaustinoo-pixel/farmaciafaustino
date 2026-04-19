package com.farmacia;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
  public class TesteController {

    @GetMapping("/")
    public String home() {
      return "Farmacia faustino está online";
    }
  }
