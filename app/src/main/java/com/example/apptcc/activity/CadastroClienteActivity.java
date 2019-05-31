package com.example.apptcc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptcc.R;
import com.example.apptcc.config.FirebaseConfig;
import com.example.apptcc.helper.Base64Custom;
import com.example.apptcc.helper.Helper;
import com.example.apptcc.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import dmax.dialog.SpotsDialog;
import io.reactivex.annotations.NonNull;

public class CadastroClienteActivity extends AppCompatActivity {

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
    /*private TextInputLayout textInputCpf;
    private TextInputLayout textInputEndereco;
    private TextInputLayout textInputBairro;
    private TextInputLayout textInputTelefone;*/
    private AlertDialog alertDialog;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        Toolbar toolbar = findViewById(R.id.toolbarCadastroCliente);
        toolbar.setTitle("Cadastre sua conta");
        setSupportActionBar(toolbar);

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
        /*textInputCpf = findViewById(R.id.textInputLayout3);
        textInputEndereco = findViewById(R.id.textInputLayout4);
        textInputBairro = findViewById(R.id.textInputLayout5);
        textInputTelefone = findViewById(R.id.textInputLayout6);*/
        usuario = new Usuario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (this.__validarCampos()) {
            cadastrarUsuario();
        }
        return super.onOptionsItemSelected(item);
    }

    public void cadastrarUsuario() {
        FirebaseAuth autenticacao = FirebaseConfig.getFirebaseAuth();
        __configAlert("Cadastrando conta...");
        Helper.hideKeyboard(CadastroClienteActivity.this);
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String idUsuario = Base64Custom.codificar(usuario.getEmail());
                            usuario.setId(idUsuario);
                            usuario.salvar();

                            startActivity(new Intent(CadastroClienteActivity.this, MainActivity.class));
                            Toast.makeText(CadastroClienteActivity.this, "Conta cadastra com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            __hideAlert();
                            String excecao;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Por favor, insira uma senha mais forte";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Por favor, insira um e-mail válido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "E-mail já está em uso, tente outro!";
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Snackbar.make(findViewById(R.id.layout_cadastro_cliente), excecao, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void __hideAlert() {
        this.alertDialog.hide();
    }

    private void __configAlert(String message) {
        this.alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(message)
                .build();
        this.alertDialog.show();
    }

    private boolean __validarCampos() {
        String nome = String.valueOf(this.edtNome.getText());
        String email = String.valueOf(edtEmail.getText());
        String senha = String.valueOf(edtSenha.getText());
        String cpf = String.valueOf(edtCpf.getText());
        String endereco = String.valueOf(edtEndereco.getText());
        String bairro = String.valueOf(edtBairro.getText());
        String telefone = String.valueOf(edtTelefone.getText());

        if (nome.isEmpty()) {
            textInputNome.setErrorEnabled(false);
            textInputNome.setErrorEnabled(true);
            textInputNome.setError("Preencha o seu nome");
            return false;
        } else {
            textInputNome.setErrorEnabled(false);
        }

        if (email.isEmpty()) {
            textInputEmail.setErrorEnabled(false);
            textInputEmail.setErrorEnabled(true);
            textInputEmail.setError("Preencha o seu e-mail");
            return false;
        } else {
            textInputEmail.setErrorEnabled(false);
        }

        if (senha.isEmpty()) {
            textInputSenha.setErrorEnabled(false);
            textInputSenha.setErrorEnabled(true);
            textInputSenha.setError("Preencha a sua senha");
            return false;
        } else {
            textInputSenha.setErrorEnabled(false);
        }

        /*if (cpf.isEmpty()) {
            textInputCpf.setErrorEnabled(false);
            textInputCpf.setErrorEnabled(true);
            textInputCpf.setError("Preencha o seu cpf");
            return false;
        } else {
            textInputCpf.setErrorEnabled(false);
        }

        if (endereco.isEmpty()) {
            textInputEndereco.setErrorEnabled(false);
            textInputEndereco.setErrorEnabled(true);
            textInputEndereco.setError("Preencha o seu endereço");
            return false;
        } else {
            textInputEndereco.setErrorEnabled(false);
        }

        if (bairro.isEmpty()) {
            textInputBairro.setErrorEnabled(false);
            textInputBairro.setErrorEnabled(true);
            textInputBairro.setError("Preencha o seu bairro");
            return false;
        } else {
            textInputBairro.setErrorEnabled(false);
        }

        if (telefone.isEmpty()) {
            textInputTelefone.setErrorEnabled(false);
            textInputTelefone.setErrorEnabled(true);
            textInputTelefone.setError("Preencha o seu telefone");
            isValido = false;
        } else {
            textInputTelefone.setErrorEnabled(false);
        }*/

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setCpf(cpf);
        usuario.setEndereco(endereco);
        usuario.setBairro(bairro);
        usuario.setTelefone(telefone);

        return true;
    }
}
