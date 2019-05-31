package com.example.apptcc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.apptcc.R;

import java.util.ArrayList;
import java.util.List;

public class CadastroPedidoActivity extends AppCompatActivity {

    private Spinner spinnerCliente;
    private ListView listaItensPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        Toolbar toolbar = findViewById(R.id.toolbarCadastroProduto);
        toolbar.setTitle("Novo pedido");
        setSupportActionBar(toolbar);

        spinnerCliente = findViewById(R.id.spinnerCliente);
        listaItensPedido = findViewById(R.id.listItensVenda);
        listaItensPedido.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.names,
                android.R.layout.simple_list_item_single_choice);
        listaItensPedido.setAdapter(adapter);

        // Spinner Drop down elements
        List<String> clientes = new ArrayList<>();
        clientes.add("Pedro Augusto Guimarães");
        clientes.add("Natalia Pereira Oliveira");
        clientes.add("Patricia Nascimento");
        clientes.add("Renato Gonçalves da Silva");
        clientes.add("Fernanda Alvez Cardoso");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clientes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(dataAdapter);
    }
}
