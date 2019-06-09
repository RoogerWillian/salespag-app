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
import com.example.apptcc.adapter.PedidosAdapter;
import com.example.apptcc.model.Pedido;
import com.example.apptcc.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFragment extends Fragment {

    private Toolbar toolbar;
    private PedidosAdapter pedidosAdapter;
    private List<Pedido> pedidos = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public void onStart() {
        super.onStart();
        this.__recuperarPedidos();
    }

    private void __recuperarPedidos() {
        pedidos.add(new Pedido(new Usuario("Pedro Augusto Guimarães"), "R$ 140,30", "Cartão de crédito MasterCard - 3x R$ 46,75"));
        pedidos.add(new Pedido(new Usuario("Natalia Pereira Oliveira"), "R$ 50,00", "Cartão de crédito Visa - 5x R$ 25,00"));
        pedidos.add(new Pedido(new Usuario("Patricia Nascimento"), "R$ 1.500,00", "Boleto bancário - a vista"));
        pedidos.add(new Pedido(new Usuario("Renato Gonçalves da Silva"), "R$ 3.500,00", "Boleto bancário - a vista"));
        pedidos.add(new Pedido(new Usuario("Fernanda Alvez Cardoso"), "R$ 30,00", "Boleto bancário - a vista"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.pedidos));
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        this.__inicializarComponentes(view);
        this.__configurarRecyclerView();

        return view;
    }

    private void __configurarRecyclerView() {
        recyclerView.setAdapter(pedidosAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void __inicializarComponentes(View view) {
        this.recyclerView = view.findViewById(R.id.recyclerPedidos);
        this.pedidosAdapter = new PedidosAdapter(pedidos, getActivity());
    }
}
