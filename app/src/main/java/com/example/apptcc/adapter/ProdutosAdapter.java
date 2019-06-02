package com.example.apptcc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptcc.R;
import com.example.apptcc.model.Produto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.MyViewHolder> {

    private List<Produto> produtos;
    Context context;

    public ProdutosAdapter(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        holder.descricao.setText(produto.getDescricao());
        String preco = this.__formatarMoeda(String.valueOf(produto.getPreco()));
        holder.valor.setText(preco);
        String marcaTamanho = produto.getMarca() + " - " + produto.getTamanho();
        holder.marca.setText(marcaTamanho);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    private String __formatarMoeda(String valorParaConverter) {
        BigDecimal valor = new BigDecimal(valorParaConverter);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView descricao, valor, marca;

        public MyViewHolder(View itemView) {
            super(itemView);

            descricao = itemView.findViewById(R.id.textAdapterDescricao);
            valor = itemView.findViewById(R.id.textAdapterValor);
            marca = itemView.findViewById(R.id.textAdapterMarca);
        }

    }
}
