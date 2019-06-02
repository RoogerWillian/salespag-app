package com.example.apptcc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.apptcc.R;

import mehdi.sakout.aboutpage.AboutPage;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View sobre = new AboutPage(this)
                .setDescription("Sobre o SalesPag")
                .addGroup("Fale conosco")
                .addEmail("salespag.com.br", "Envie um e-mail")
                .addWebsite("https://www.google.com/", "Acesse nosso site")
                .addGroup("Acesse nossas redes sociais")
                .addFacebook("adriano.junior.566", "Facebook")
                .addInstagram("adrianojbcs", "Instagram")
                .isRTL(false)
                .create();

        setContentView(sobre);
    }
}
