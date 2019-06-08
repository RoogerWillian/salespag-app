package com.example.apptcc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apptcc.R;
import com.example.apptcc.model.ItemPedido;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemPedidoAdapter extends RecyclerView.Adapter<ItemPedidoAdapter.MyViewHolder>{

    private List<ItemPedido> produtos;
    Context context;

    public ItemPedidoAdapter(List<ItemPedido> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_pedido, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ItemPedido itemPedido = produtos.get(position);
        String produtoDescricao = itemPedido.getProduto().getDescricao();
        produtoDescricao += ItemPedidoAdapter.__formatarMoeda(itemPedido.getProduto().getPreco().toString()) + " un.";
        holder.produto.setText(produtoDescricao);

        holder.quantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                String textoConvertido = String.valueOf(text);
                if (!textoConvertido.isEmpty()) {
                    ItemPedido itemPedido1 = produtos.get(position);
                    itemPedido1.setQuantidade(Integer.valueOf(textoConvertido));
                } else {
                    itemPedido.setQuantidade(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    private static String __formatarMoeda(String valorParaConverter) {
        BigDecimal valor = new BigDecimal(valorParaConverter);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView produto;
        EditText quantidade;

        public MyViewHolder(View itemView) {
            super(itemView);

            produto = itemView.findViewById(R.id.textItemPedidoProduto);
            quantidade = itemView.findViewById(R.id.textItemPedidoQuantidade);
        }
    }

}
