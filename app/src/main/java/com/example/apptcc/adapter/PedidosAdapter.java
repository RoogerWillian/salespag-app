package com.example.apptcc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptcc.R;
import com.example.apptcc.model.Pedido;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.MyViewHolder> {

    private List<Pedido> pedidos;
    Context context;

    public PedidosAdapter(List<Pedido> pedidos, Context context) {
        this.pedidos = pedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pedidos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.cliente.setText(pedido.getCliente().getNome());
        holder.valor.setText(__formatarMoeda(pedido.getTotal()));
        holder.formaPagto.setText(pedido.getFormaPagamento());
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    private String __formatarMoeda(String valorParaConverter) {
        BigDecimal valor = new BigDecimal(valorParaConverter);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cliente, valor, formaPagto;

        public MyViewHolder(View itemView) {
            super(itemView);

            cliente = itemView.findViewById(R.id.textAdapterCliente);
            valor = itemView.findViewById(R.id.textAdapterValorProduto);
            formaPagto = itemView.findViewById(R.id.textAdapterFormaPagto);
        }

    }
}
