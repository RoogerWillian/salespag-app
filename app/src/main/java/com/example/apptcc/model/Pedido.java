package com.example.apptcc.model;

import android.provider.ContactsContract;

import com.example.apptcc.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private static final String NODE = "pedidos";
    private String id;
    private Usuario cliente;
    private List<ItemPedido> itens = new ArrayList<>();
    private String total;
    private String formaPagamento;

    public Pedido() {

    }

    public Pedido(Usuario cliente, String total, String formaPagamento) {
        this.cliente = cliente;
        this.total = total;
        this.formaPagamento = formaPagamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Exclude
    public void salvar() {
        DatabaseReference reference = FirebaseConfig.getFirebaseDatabase();
        DatabaseReference pedidoReference = reference.child(NODE);
        String id = pedidoReference.push().getKey();
        setId(id);

        reference.child(NODE)
                .child(getId())
                .setValue(this);
    }
}
