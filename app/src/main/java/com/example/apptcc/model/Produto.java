package com.example.apptcc.model;

public class Produto {

    private int id;
    private String descricao;
    private String tamanho;
    private double preco;
    private String marca;

    public Produto() {
    }

    public Produto(String descricao, String tamanho, double preco, String marca) {
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.preco = preco;
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
