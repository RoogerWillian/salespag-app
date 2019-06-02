package com.example.apptcc.activity;

import android.app.AlertDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apptcc.R;
import com.example.apptcc.helper.Helper;
import com.example.apptcc.model.Produto;

import java.io.Serializable;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class CadastroProdutoActivity extends AppCompatActivity {

    private AppCompatEditText edtCadProdutoDescricao;
    private AppCompatEditText edtCadProdutoTamanho;
    private AppCompatEditText edtCadProdutoPreco;
    private AppCompatEditText edtCadProdutoMarca;
    private TextInputLayout textInputCadProdutoDescricao;
    private TextInputLayout textInputCadProdutoTamanho;
    private TextInputLayout textInputCadProdutoPreco;
    private TextInputLayout textInputCadProdutoMarca;
    private boolean isAtualizandoProduto = false;
    private AlertDialog alertDialog;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        _inicializarComponentes();
        Toolbar toolbar = findViewById(R.id.toolbarCadastroProduto);
        // Product to update
        carregaProdutoEdicao();
        if (this.produto.getId() == null) {
            toolbar.setTitle("Novo produto");
        } else {
            toolbar.setTitle("Alterar produto");
        }
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (__validarCampos()) {
            if (!isAtualizandoProduto)
                this.__cadastrarProduto();
            else
                this.__alterarProduto();

        }

        return super.onOptionsItemSelected(item);
    }

    private void __alterarProduto() {
        try {
            Helper.hideKeyboard(this);
            __configAlert("Alterando produto...");

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            produto.alterar();
                            alertDialog.hide();
                            finish();
                            Toast.makeText(CadastroProdutoActivity.this, "Produto alterado com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    1500);
        } catch (Exception e) {
            Toast.makeText(this, "Problema ao salvar produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void __cadastrarProduto() {
        try {
            Helper.hideKeyboard(this);
            __configAlert("Cadastrando produto...");

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            produto.salvar();
                            alertDialog.hide();
                            finish();
                            Toast.makeText(CadastroProdutoActivity.this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    1500);
        } catch (Exception e) {
            Toast.makeText(this, "Problema ao salvar produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean __validarCampos() {
        String descricao = String.valueOf(edtCadProdutoDescricao.getText());
        String tamanho = String.valueOf(edtCadProdutoTamanho.getText());
        String preco = String.valueOf(edtCadProdutoPreco.getText());
        String marca = String.valueOf(edtCadProdutoMarca.getText());

        if (descricao.isEmpty()) {
            textInputCadProdutoDescricao.setErrorEnabled(false);
            textInputCadProdutoDescricao.setErrorEnabled(true);
            textInputCadProdutoDescricao.setError("Preencha a descrição");
            return false;
        } else {
            textInputCadProdutoDescricao.setErrorEnabled(false);
        }

        if (tamanho.isEmpty()) {
            textInputCadProdutoTamanho.setErrorEnabled(false);
            textInputCadProdutoTamanho.setErrorEnabled(true);
            textInputCadProdutoTamanho.setError("Preencha o tamanho");
            return false;
        } else {
            textInputCadProdutoTamanho.setErrorEnabled(false);
        }

        if (preco.isEmpty()) {
            textInputCadProdutoPreco.setErrorEnabled(false);
            textInputCadProdutoPreco.setErrorEnabled(true);
            textInputCadProdutoPreco.setError("Preencha o preço");
            return false;
        } else {
            textInputCadProdutoPreco.setErrorEnabled(false);
        }

        if (marca.isEmpty()) {
            textInputCadProdutoMarca.setErrorEnabled(false);
            textInputCadProdutoMarca.setErrorEnabled(true);
            textInputCadProdutoMarca.setError("Preencha a maraca");
            return false;
        } else {
            textInputCadProdutoMarca.setErrorEnabled(false);
        }

        if (produto.getId() == null)
            produto.setId(UUID.randomUUID().toString());
        produto.setDescricao(descricao);
        produto.setMarca(marca);
        produto.setTamanho(tamanho);
        produto.setPreco(Double.valueOf(preco));

        return true;
    }

    private void __configAlert(String message) {
        this.alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(message)
                .build();
        this.alertDialog.show();
    }

    private void _inicializarComponentes() {
        this.edtCadProdutoDescricao = this.findViewById(R.id.edtCadProdutoDescricao);
        this.edtCadProdutoMarca = this.findViewById(R.id.edtCadProdutoMarca);
        this.edtCadProdutoTamanho = this.findViewById(R.id.edtCadProdutoTamanho);
        this.edtCadProdutoPreco = this.findViewById(R.id.edtCadProdutoPreco);
        this.textInputCadProdutoDescricao = this.findViewById(R.id.textInputCadProdutoDescricao);
        this.textInputCadProdutoMarca = this.findViewById(R.id.textInputCadProdutoMarca);
        this.textInputCadProdutoPreco = this.findViewById(R.id.textInputCadProdutoPreco);
        this.textInputCadProdutoTamanho = this.findViewById(R.id.textInputCadProdutoTamanho);
        this.produto = new Produto();
    }

    private void carregaProdutoEdicao() {
        Serializable productParam = getIntent().getSerializableExtra("produto");
        if (productParam != null) {
            this.produto = (Produto) productParam;
            this.edtCadProdutoDescricao.setText(this.produto.getDescricao());
            this.edtCadProdutoMarca.setText(this.produto.getMarca());
            this.edtCadProdutoTamanho.setText(this.produto.getTamanho());
            this.edtCadProdutoPreco.setText(String.valueOf(this.produto.getPreco()));
            this.isAtualizandoProduto = true;
        }
    }
}
