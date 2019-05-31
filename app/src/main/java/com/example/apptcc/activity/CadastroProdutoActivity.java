package com.example.apptcc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.apptcc.R;

public class CadastroProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        Toolbar toolbar = findViewById(R.id.toolbarCadastroProduto);
        toolbar.setTitle("Novo produto");
        setSupportActionBar(toolbar);
    }
}
