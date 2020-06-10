package com.example.gleaming.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gleaming.ProductActivity;
import com.example.gleaming.R;
import com.example.gleaming.model.Prenda;

import java.util.ArrayList;

public class PrendaAdapter extends RecyclerView.Adapter<PrendaAdapter.ViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<Prenda> prendas;

    public PrendaAdapter(Context context, ArrayList<Prenda> prendas) {
        this.context = context;
        this.prendas = prendas;
    }

    public PrendaAdapter(Context context) {
        this.context = context;
        prendas = new ArrayList<>();
    }

    @NonNull
    @Override
    public PrendaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PrendaAdapter.ViewHolder holder, int position) {
        Prenda prenda = prendas.get(position);

        Glide.with(context)
                .load("http://gleaming.nickgonzalezs.com/storage/images/" + prenda.getImg())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.prendaImg);
    }

    @Override
    public int getItemCount() {
        return prendas != null ? prendas.size() : 0;
    }

    public void addPrendas(ArrayList<Prenda> prendas) {
        this.prendas.addAll(prendas);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {

        Intent i = new Intent(context, ProductActivity.class);

        Prenda prenda = prendas.get(position);
        i.putExtra(context.getString(R.string.prenda_id), prenda.getId());
        i.putExtra(context.getString(R.string.product_img), prenda.getImg());

        context.startActivity(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView prendaImg;
        private ItemClickListener listener;


        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            prendaImg = itemView.findViewById(R.id.product_img);
            prendaImg.setOnClickListener(this);
            prendaImg.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(context,"clicccccccckkkkkk", Toast.LENGTH_LONG).show();
            return true;
        }
    }

}

interface ItemClickListener {
    void onClick(View view, int position);
}

interface ItemLongClickListener {
    void onLongClick(View view, int position);

}