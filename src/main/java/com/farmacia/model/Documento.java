package com.farmacia.model;

import jakarta.persistence.*;

@Entity
public class Documento {

    @Id
    @GeneratedValue
    private Long id;

    private String nomeArquivo;
    private String caminho;

    @ManyToOne
    private Cliente cliente;

    // getters e setters
}
