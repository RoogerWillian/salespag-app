package com.example.apptcc.model;

import android.util.Log;

import com.example.apptcc.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

public class Produto implements Serializable {

    @Exclude
    public static final String NODE = "produtos";
    private String id;
    private String descricao;
    private String tamanho;
    private Double preco;
    private String marca;

    public Produto() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Exclude
    public void salvar() {
        DatabaseReference reference = FirebaseConfig.getFirebaseDatabase();
        reference.child(NODE)
                .child(this.id)
                .setValue(this);
    }

    public void alterar() {
        DatabaseReference reference = FirebaseConfig.getFirebaseDatabase();
        DatabaseReference productRef = reference.child(NODE).child(this.id);
        productRef.updateChildren(this.__toMap());
    }

    public void excluir() {
        DatabaseReference reference = FirebaseConfig.getFirebaseDatabase();
        DatabaseReference productRef = reference.child(NODE).child(this.id);
        productRef.removeValue();
    }

    private HashMap<String, Object> __toMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", this.id);
        map.put("descricao", this.descricao);
        map.put("marca", this.marca);
        map.put("preco", this.preco);
        map.put("tamanho", this.tamanho);

        return map;
    }

    @Override
    @Exclude
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", preco=" + preco +
                ", marca='" + marca + '\'' +
                '}';
    }
}
