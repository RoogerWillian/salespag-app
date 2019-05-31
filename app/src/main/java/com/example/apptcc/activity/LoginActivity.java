package com.example.apptcc.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.example.apptcc.R;
import com.example.apptcc.config.FirebaseConfig;
import com.example.apptcc.helper.Helper;
import com.example.apptcc.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import dmax.dialog.SpotsDialog;
import io.reactivex.annotations.NonNull;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText edtEmail;
    private AppCompatEditText edtSenha;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputSenha;
    private AlertDialog alertDialog;
    private Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.edtEmail = this.findViewById(R.id.edtEmail);
        this.edtSenha = this.findViewById(R.id.edtSenha);
        this.textInputEmail = this.findViewById(R.id.textInputEmail);
        this.textInputSenha = this.findViewById(R.id.textInputSenha);
    }

    @Override
    protected void onStart() {
        this.__validarUsuarioLogado();
        super.onStart();
    }

    public void entrar(View view) {
        if (this.__validarCampos()) {
            this.__realizarLogin();
        }
    }

    private void __realizarLogin() {
        FirebaseAuth autenticacao = FirebaseConfig.getFirebaseAuth();
        this.__configAlert("Autenticando usuário...");
        Helper.hideKeyboard(LoginActivity.this);
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this, "Usuario logado com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            __hideAlert();
                            String excecao;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                excecao = "Usuário não está cadastrado.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "E-mail ou senha inválidos.";
                            } catch (Exception e) {
                                excecao = "Erro ao logar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Snackbar.make(findViewById(R.id.layout_login), excecao, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void __hideAlert() {
        this.alertDialog.hide();
    }

    private void __validarUsuarioLogado() {
        FirebaseAuth autenticacao = FirebaseConfig.getFirebaseAuth();
        if (autenticacao.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Usuário logado anteriormente!", Toast.LENGTH_SHORT).show();
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

        if (email.isEmpty()) {
            textInputSenha.setErrorEnabled(false);
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

        usuario.setEmail(email);
        usuario.setSenha(senha);

        return true;
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        startActivity(intent);
    }
}
