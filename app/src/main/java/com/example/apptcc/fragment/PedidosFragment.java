package com.example.apptcc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.apptcc.R;
import com.example.apptcc.adapter.PedidosAdapter;
import com.example.apptcc.config.FirebaseConfig;
import com.example.apptcc.model.Pedido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        this.__recuperarPedidos();
    }

    private void __recuperarPedidos() {
        pedidos.clear();
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseConfig.getFirebaseDatabase();
        DatabaseReference pedidosRef = databaseReference.child("pedidos");
        pedidosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pedido pedido = snapshot.getValue(Pedido.class);
                    pedidos.add(pedido);
                    pedidosAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        this.progressBar = view.findViewById(R.id.progressPedidos);
        this.pedidosAdapter = new PedidosAdapter(pedidos, getActivity());
    }
}
