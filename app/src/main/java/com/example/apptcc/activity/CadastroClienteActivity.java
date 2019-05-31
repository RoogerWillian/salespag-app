package com.example.apptcc.activity;

import android.app.AlertDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptcc.R;

public class CadastroClienteActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText edtNome;
    private AppCompatEditText edtEmail;
    private AppCompatEditText edtSenha;
    private AppCompatEditText edtCpf;
    private AppCompatEditText edtEndereco;
    private AppCompatEditText edtBairro;
    private AppCompatEditText edtTelefone;
    private TextInputLayout textInputNome;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputSenha;
    private TextInputLayout textInputCpf;
    private TextInputLayout textInputEndereco;
    private TextInputLayout textInputBairro;
    private TextInputLayout textInputTelefone;
    private Button buttonCadastrarCliente;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        edtNome = findViewById(R.id.edtNomeCliente);
        edtEmail = findViewById(R.id.edtEmailCliente);
        edtSenha = findViewById(R.id.edtCadastroClienteSenha);
        edtCpf = findViewById(R.id.edtCadastroCpf);
        edtEndereco = findViewById(R.id.edtEnderecoCliente);
        edtBairro = findViewById(R.id.edtCadastroBairro);
        edtTelefone = findViewById(R.id.edtCadastroClienteTelefone);
        textInputNome = findViewById(R.id.textInputLayout);
        textInputEmail = findViewById(R.id.textInputLayout2);
        textInputSenha = findViewById(R.id.textInputSenhaCliente);
        textInputCpf = findViewById(R.id.textInputLayout3);
        textInputEndereco = findViewById(R.id.textInputLayout4);
        textInputBairro = findViewById(R.id.textInputLayout5);
        textInputTelefone = findViewById(R.id.textInputLayout6);
        buttonCadastrarCliente = findViewById(R.id.buttonCadastrarCliente);
        buttonCadastrarCliente.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private boolean __validarCampos() {
        boolean retorno = false;
//        String nome =


        return retorno;
    }
}
