package com.example.apptcc.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.apptcc.R;
import com.example.apptcc.config.FirebaseConfig;
import com.example.apptcc.fragment.PedidosFragment;
import com.example.apptcc.fragment.ProdutosFragment;
import com.example.apptcc.helper.FragmentHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final Integer PRODUTOS_RESULT_CODE = 100;
    public static final Integer PEDIDOS_RESULT_CODE = 200;
    private FirebaseAuth autenticacao;
    private TextView txtNomeUsuarioLogado;
    private TextView txtEmailUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pedidos");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        autenticacao = FirebaseConfig.getFirebaseAuth();
        txtNomeUsuarioLogado = navigationView.getHeaderView(0).findViewById(R.id.txtNomeUsuarioLogado);
        txtEmailUsuarioLogado = navigationView.getHeaderView(0).findViewById(R.id.txtEmailUsuarioLogado);

        FragmentHelper.show(this, R.id.container, new PedidosFragment());
    }

    @Override
    protected void onStart() {
        this.__carregarDadosUsuarioLogado();
        super.onStart();
    }

    private void __carregarDadosUsuarioLogado() {
        FirebaseUser currentUser = autenticacao.getCurrentUser();
        Log.i("TESTEUSER", currentUser.getDisplayName());
        if (currentUser != null) {
            txtNomeUsuarioLogado.setText(currentUser.getDisplayName());
            txtEmailUsuarioLogado.setText(currentUser.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.acao_sair) {
            autenticacao.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int container = R.id.container;

        FragmentHelper.show(this, container, new PedidosFragment());

        if (id == R.id.nav_sales) {
            FragmentHelper.show(this, container, new PedidosFragment());
        } else if (id == R.id.nav_products) {
            FragmentHelper.show(this, container, new ProdutosFragment());
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, SobreActivity.class));
        } else if (id == R.id.nav_contact) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","contato@salespag.com.br", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato SalesPag");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("TESTECODE", "requestCode: " + requestCode);
        Log.i("TESTECODE", "resultCode: " + resultCode);
        if (requestCode == PRODUTOS_RESULT_CODE) {
            FragmentHelper.show(this, R.id.container, new ProdutosFragment());
        } else if (requestCode == PEDIDOS_RESULT_CODE) {
            FragmentHelper.show(this, R.id.container, new PedidosFragment());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void validarFragmentAtivo(View view) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof ProdutosFragment) {
            startActivity(new Intent(this, CadastroProdutoActivity.class));
        } else if (currentFragment instanceof PedidosFragment) {
            startActivity(new Intent(this, CadastroPedidoActivity.class));
        }
    }
}
