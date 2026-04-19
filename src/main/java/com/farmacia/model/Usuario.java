package com.farmacia.model;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String senha;
    private String perfil;

    // getters e setters
    public Long getId() { return id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}
