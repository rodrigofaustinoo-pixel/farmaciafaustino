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

    public Long getId() { return id; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getCaminho() { return caminho; }
    public void setCaminho(String caminho) { this.caminho = caminho; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
