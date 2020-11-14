package com.hendro.android_java_api_crud;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.GridViewHolder>  {
    private List<Supplier> suppliers;
    private Context context;

    public SupplierAdapter(Context context, List<Supplier> suppliers) {
        this.suppliers = suppliers;
        this.context = context;
    }

    @NonNull
    @Override
    public SupplierAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_layout, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapter.GridViewHolder holder, int position) {
        final int pos = position;
        final String id = suppliers.get(position).getId();
        final String nama = suppliers.get(position).getNama();
        final String alamat = suppliers.get(position).getAlamat();
        final String telp = suppliers.get(position).getTelp();
        final String email = suppliers.get(position).getEmail();
        final String pic = suppliers.get(position).getPic();

        holder.tvID.setText(id);
        holder.tvNama.setText(nama);
        holder.tvTelp.setText(telp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Operasi data");
                alertDialog.setMessage(id +" - "+ nama);
                alertDialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setNegativeButton("LIHAT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    //beritahu posisi fragment yang sedang aktif adalah FirstFragment
                    Cons.ACTIVE_FRAGMENT = "SecondFragment";

                    Bundle b = new Bundle();
                    b.putString("b_id", id);
                    b.putString("b_nama", nama);
                    b.putString("b_alamat", alamat);
                    b.putString("b_telp", telp);
                    b.putString("b_email", email);
                    b.putString("b_pic", pic);

                    Intent intent = new Intent(context, DetilSupplier.class);
                    intent.putExtras(b);

                    //context.startActivity(intent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ((Activity) context).startActivityForResult(intent, 1, b);
                    }
                    }
                });

                alertDialog.setNeutralButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://kodeasik.000webhostapp.com/supplier-000.php?action=hapus&id="+ id;

                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    String id, nama, telp;

                                    if (response.optString("result").equals("true")){
                                        Toast.makeText(context, "Data terhapus!", Toast.LENGTH_SHORT).show();

                                        suppliers.remove(pos); //hapus baris customers
                                        notifyItemRemoved(pos); //refresh customer list ( ada animasinya )
                                        notifyDataSetChanged();

                                    }else{
                                        Toast.makeText(context, "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("Events: ", error.toString());

                            Toast.makeText(context, "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsObjRequest);
                    }
                });


                AlertDialog dialog = alertDialog.create();
                dialog.show();

                //untuk ubah warna tombol dialog
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvID,tvNama, tvTelp;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = (TextView) itemView.findViewById(R.id.tv_id);
            tvNama = (TextView) itemView.findViewById(R.id.tv_nama);
            tvTelp = (TextView) itemView.findViewById(R.id.tv_telp);
        }
    }
}
