package com.farmacia.controller;

import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/login")
    public String login(@RequestBody Usuario user) {

        Usuario u = repository.findByLogin(user.getLogin());

        if (u != null && u.getSenha().equals(user.getSenha())) {
            return "OK";
        }

        return "ERRO";
    }
}
