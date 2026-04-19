package com.farmacia.model;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String cpf;

    // getters e setters
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
