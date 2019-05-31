package com.example.apptcc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.apptcc.R;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText edtEmail;
    private AppCompatEditText edtSenha;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputSenha;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.edtEmail = this.findViewById(R.id.edtEmail);
        this.edtSenha = this.findViewById(R.id.edtSenha);
        this.textInputEmail = this.findViewById(R.id.textInputEmail);
        this.textInputSenha = this.findViewById(R.id.textInputSenha);
    }

    public void entrar(View view) {
        if (this.__validarCampos()) {
            this.__configAlert("Autenticando...");

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    },
                    2000);
        }
    }

    private void __configAlert(String message) {
        this.alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(message)
                .build();
        this.alertDialog.show();
    }

    private boolean __validarCampos() {
        String email = String.valueOf(edtEmail.getText());
        String senha = String.valueOf(edtSenha.getText());
        boolean isValido = true;

        if (email.isEmpty()) {
            textInputEmail.setErrorEnabled(true);
            textInputEmail.setError("Preencha o seu e-mail");
            isValido = false;
        } else {
            textInputEmail.setErrorEnabled(false);
        }

        if (senha.isEmpty()) {
            textInputSenha.setErrorEnabled(true);
            textInputSenha.setError("Preencha a sua senha");
            isValido = false;
        } else {
            textInputSenha.setErrorEnabled(false);
        }

        return isValido;
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        startActivity(intent);
    }
}
