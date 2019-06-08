package com.example.apptcc.model;

import java.math.BigDecimal;

public class ItemPedido {

    private Produto produto;
    private int quantidade = 0;
    private BigDecimal valor;

    public ItemPedido() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                '}';
    }
}
