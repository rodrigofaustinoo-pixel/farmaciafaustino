package com.farmacia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String senha;
    private String perfil;
}
