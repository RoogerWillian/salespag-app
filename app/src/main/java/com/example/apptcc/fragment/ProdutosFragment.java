package com.example.apptcc.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apptcc.R;
import com.example.apptcc.RecyclerItemClickListener;
import com.example.apptcc.activity.CadastroProdutoActivity;
import com.example.apptcc.adapter.ProdutosAdapter;
import com.example.apptcc.config.FirebaseConfig;
import com.example.apptcc.model.Produto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutosFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProdutosAdapter produtoAdapter;
    private ProgressBar progressBar;
    private List<Produto> produtos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produtos, container, false);

        this.toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.produtos));
        this.__inicializarComponentes(view);
        this.__configurarRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.__recuperarProdutos();
    }

    private void __recuperarProdutos() {
        produtos.clear();
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseConfig.getFirebaseDatabase();
        DatabaseReference produtosRef = databaseReference.child(Produto.NODE);
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Produto produto = snapshot.getValue(Produto.class);
                    produtos.add(produto);
                }
                produtoAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        childEventListener = produtosRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Produto produto = dataSnapshot.getValue(Produto.class);
//                produtos.add(produto);
//                produtoAdapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }

    private void __configurarRecyclerView() {
        recyclerView.setAdapter(produtoAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity().getApplicationContext(),
                this.recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Produto produto = produtos.get(position);
                        Intent intent = new Intent(getActivity(), CadastroProdutoActivity.class);
                        intent.putExtra("produto", produto);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Produto produto = produtos.get(position);
                        __prepararExclusao(produto);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }

    private void __prepararExclusao(final Produto produto) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Exclusão produto");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setMessage("Tem certeza que deseja excluir o produto " + produto.getDescricao() + " ?");
        dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog = new SpotsDialog.Builder()
                        .setContext(getActivity())
                        .setMessage("Excluindo produto...")
                        .build();
                ((AlertDialog) dialog).show();

                final DialogInterface finalDialog = dialog;
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                produto.excluir();
                                __recuperarProdutos();
                                ((AlertDialog) finalDialog).hide();
                                Toast.makeText(getActivity(), "Produto excluído com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        },
                        1500);
            }
        });
        dialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void __inicializarComponentes(View view) {
        this.recyclerView = view.findViewById(R.id.recyclerProdutos);
        this.progressBar = view.findViewById(R.id.progressProdutos);
        this.produtoAdapter = new ProdutosAdapter(produtos, getActivity());
    }
}
