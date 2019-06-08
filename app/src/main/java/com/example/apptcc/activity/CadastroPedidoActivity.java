package com.example.apptcc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.apptcc.R;
import com.example.apptcc.adapter.ItemPedidoAdapter;
import com.example.apptcc.config.FirebaseConfig;
import com.example.apptcc.model.ItemPedido;
import com.example.apptcc.model.Produto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import br.com.uol.pslibs.checkout_in_app.PSCheckout;
import br.com.uol.pslibs.checkout_in_app.wallet.util.PSCheckoutConfig;
import br.com.uol.pslibs.checkout_in_app.wallet.vo.PSCheckoutRequest;
import br.com.uol.pslibs.checkout_in_app.wallet.vo.PagSeguroResponse;

public class CadastroPedidoActivity extends AppCompatActivity {

    private RecyclerView listItensVenda;
    private List<ItemPedido> itens = new ArrayList<>();
    private ItemPedidoAdapter itemPedidoAdapter;
    private ChildEventListener childEventListener;
    private ConstraintLayout pedidoLayoutPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        Toolbar toolbar = findViewById(R.id.toolbarCadastroProduto);
        toolbar.setTitle("Novo pedido");
        setSupportActionBar(toolbar);

        pedidoLayoutPrincipal = findViewById(R.id.pedidoLayoutPrincipal);
        listItensVenda = findViewById(R.id.listItensVenda);
        __configuraRecyclerView();
        __inicializarPagSeguro();
    }

    private void __configuraRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listItensVenda.setLayoutManager(layoutManager);
        listItensVenda.setHasFixedSize(true);
        itemPedidoAdapter = new ItemPedidoAdapter(itens, this);
        listItensVenda.setAdapter(itemPedidoAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        pedidoLayoutPrincipal.setVisibility(View.VISIBLE);
        itens.clear();
        DatabaseReference database = FirebaseConfig.getFirebaseDatabase();
        childEventListener = database.child(Produto.NODE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Produto produto = dataSnapshot.getValue(Produto.class);
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setProduto(produto);
                itens.add(itemPedido);
                itemPedidoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PSCheckout.onHomeButtonPressed(this); //Controle Lib Home Button
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enviarParaPagamento() {
        PSCheckoutRequest psCheckoutRequest = new PSCheckoutRequest().withReferenceCode("123");
        for (ItemPedido itemPedido : itens) {
            if (itemPedido != null) {
                int quantidade = itemPedido.getQuantidade();
                Produto produto = itemPedido.getProduto();
                Double preco = produto.getPreco();
                if (quantidade > 0) {
                    psCheckoutRequest.withNewItem(produto.getDescricao(), String.valueOf(quantidade), preco, produto.getId());
                }
            }
        }
        pedidoLayoutPrincipal.setVisibility(View.GONE);
        PSCheckout.payWallet(psCheckoutRequest, new br.com.uol.pslibs.checkout_in_app.wallet.listener.PSCheckoutListener() {
            @Override
            public void onSuccess(PagSeguroResponse pagSeguroResponse, Context context) {
                //Sucesso na transação
                Toast.makeText(context, "Transação realizada com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(PagSeguroResponse pagSeguroResponse, Context context) {
                //Falha na transação
                Log.v("Pagsuro", "Falha na transação, " + " Erro: " + pagSeguroResponse.getErrorCode() + ", " + pagSeguroResponse.getMessage());
            }

            @Override
            public void onProgress(Context context) {
                Toast.makeText(context, "Solicitando pagamento", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCloseProgress(Context context) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Fornece controle para LIB de Activity results
        PSCheckout.onActivityResult(this, requestCode, resultCode, data);//Controle Lib Activity Life Cycle
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Android 6+ fornece controle para LIB para request de permissões
        PSCheckout.onRequestPermissionsResult(this, requestCode, permissions, grantResults);//Controle Lib Activity Life Cycle
    }

    @Override
    public void onBackPressed() {
        if (PSCheckout.onBackPressed(this)) { //Controle Lib Button back
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PSCheckout.onDestroy(); //Controle Lib Activity Life Cycle
    }

    @Override
    protected void onStop() {
        super.onStop();

        DatabaseReference database = FirebaseConfig.getFirebaseDatabase();
        database.removeEventListener(childEventListener);
    }

    private void __inicializarPagSeguro() {
        //Inicialização a lib com parametros necessarios
        PSCheckoutConfig psCheckoutConfig = new PSCheckoutConfig();
        psCheckoutConfig.setSellerEmail("adrianojrbarbato@yahoo.com.br");
        psCheckoutConfig.setSellerToken("a55304ef-020b-4231-b66e-1e924a2e94d2c6afe8d94ea0addeb0df5d53230b5cbd02aa-2950-4385-af38-ee52b0f5690d");
        //Informe o fragment container
        psCheckoutConfig.setContainer(R.id.framePagamento);

        //Inicializa apenas os recursos de pagamento transparente e boleto
        PSCheckout.initTransparent(this, psCheckoutConfig);

        //Caso queira inicializar todos os recursos da lib
        PSCheckout.init(this, psCheckoutConfig);
    }

    public void pagarComPagseguro(View view) {
        Toast.makeText(this, "Implementar pagamento fake", Toast.LENGTH_SHORT).show();
    }
}
