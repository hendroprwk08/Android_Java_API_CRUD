package com.hendro.android_java_api_crud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.GridViewHolder>  {
    private List<Customer> customers;
    private Context context;

    public CustomerAdapter(Context context, List<Customer> customers) {
        this.customers = customers;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_layout, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.GridViewHolder holder, int position) {
        final String id = customers.get(position).getId();
        final String nama = customers.get(position).getNama();
        final String telp = customers.get(position).getTelp();

        holder.tvNama.setText(nama);
        holder.tvTelp.setText(telp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, nama, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTelp;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = (TextView) itemView.findViewById(R.id.tv_nama);
            tvTelp = (TextView) itemView.findViewById(R.id.tv_telp);
        }
    }
}
