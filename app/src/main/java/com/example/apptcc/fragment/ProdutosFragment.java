package com.example.apptcc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptcc.R;
import com.example.apptcc.adapter.ProdutosAdapter;
import com.example.apptcc.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutosFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProdutosAdapter produtoAdapter;
    private List<Produto> produtos = new ArrayList<>();

    public ProdutosFragment() {
        // Required empty public constructor
    }


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
        this.produtos.add(new Produto("Playstation 4", "100cm x 40cm", 1700.00, "Sony Entertainment"));
        this.produtos.add(new Produto("Horizon Zero Dawn", "20cm x 10cm", 129.90, "Sony Entertainment"));
        this.produtos.add(new Produto("The Last of Us", "20cm x 10cm", 79.90, "Naughty Dog"));
        this.produtos.add(new Produto("Notebook Envy", "17''", 3500.50, "HP"));
        this.produtos.add(new Produto("Smartphone MI a2", "17''", 1500.80, "Xiaomi"));
        this.produtos.add(new Produto("Monitor Wirescreen", "23''", 558.80, "Dell"));
        this.produtos.add(new Produto("Xbox One", "300cm x 70cm", 2000.00, "Microsoft"));
        this.produtos.add(new Produto("Mouse Optico", "20cm x 10cm", 80.00, "Dell"));
        this.produtos.add(new Produto("Impressora Multifuncional", "168cm x 65cm", 1359.90, "Epson"));
    }

    private void __configurarRecyclerView() {
        recyclerView.setAdapter(produtoAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void __inicializarComponentes(View view) {
        this.recyclerView = view.findViewById(R.id.recyclerProdutos);
        this.produtoAdapter = new ProdutosAdapter(produtos, getActivity());
    }
}
