package com.hendro.android_java_api_crud;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder dialog;
    EditText etID, etNama, etAlamat, etTelp, etEmail, etPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("Hendro ", "onCreate: load main");

        //load fragment pertama kali
        Cons.ACTIVE_FRAGMENT="FirstFragment";
        loadFragment(new FirstFragment());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog.Builder(MainActivity.this);

                if ( Cons.ACTIVE_FRAGMENT.equals("FirstFragment") ) { //cek fragment mana yang aktif?
                    //tarik layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.form_input_cutomer_layout,
                            null);

                    dialog.setView(view);
                    dialog.setCancelable(true);

                    //definisi objek
                    etID = (EditText) view.findViewById(R.id.et_id);
                    etNama = (EditText) view.findViewById(R.id.et_nama);
                    etTelp = (EditText) view.findViewById(R.id.et_telp);

                    dialog.setPositiveButton("SIMPAN",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String id, nama, telp;

                                    id = etID.getText().toString();
                                    nama = etNama.getText().toString();
                                    telp = etTelp.getText().toString();

                                    //simpan customer
                                    simpanCustomer(id, nama, telp);
                                }
                            });

                    dialog.setNegativeButton("BATAL",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    dialog.show();
                }else if( Cons.ACTIVE_FRAGMENT.equals("SecondFragment")) {
                    //tarik layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.form_input_supplier_layout,
                            null);

                    dialog.setView(view);
                    dialog.setCancelable(true);

                    //definisi objek
                    etID = (EditText) view.findViewById(R.id.et_id);
                    etNama = (EditText) view.findViewById(R.id.et_nama);
                    etAlamat = (EditText) view.findViewById(R.id.et_alamat);
                    etTelp = (EditText) view.findViewById(R.id.et_telp);
                    etEmail = (EditText) view.findViewById(R.id.et_email);
                    etPIC = (EditText) view.findViewById(R.id.et_pic);

                    dialog.setPositiveButton("SIMPAN",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String id, nama, alamat, telp, email, pic;

                                    id = etID.getText().toString();
                                    nama = etNama.getText().toString();
                                    alamat = etAlamat.getText().toString();
                                    telp = etTelp.getText().toString();
                                    email = etEmail.getText().toString();
                                    pic = etPIC.getText().toString();

                                    //simpan supplier
                                    simpanSupplier(id, nama, alamat, telp, email, pic);
                                }
                            });

                    dialog.setNegativeButton("BATAL",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.d("Hendro ", "onResume: "+ Cons.ACTIVE_FRAGMENT);

            //cek fragment mana yang sebelumnya aktif
            if( Cons.ACTIVE_FRAGMENT.equals("FirstFragment")) {
                loadFragment(new FirstFragment());
            }else if( Cons.ACTIVE_FRAGMENT.equals("SecondFragment")){
                loadFragment(new SecondFragment());
            }
        }catch (Exception e){
            Log.d("Hendro ", "onResume: "+ e.getMessage() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

        if (id == R.id.m_customer) {
            Cons.ACTIVE_FRAGMENT = "FirstFragment";
            loadFragment(new FirstFragment());
            return true;
        }else if (id == R.id.m_supplier) {
            Cons.ACTIVE_FRAGMENT = "SecondFragment";
            loadFragment(new SecondFragment());
            return true;
        }else if (id == R.id.m_barang) {
            Cons.ACTIVE_FRAGMENT = "ThirdFragment";
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void simpanSupplier(String id, String nama, String alamat, String telp, String email, String pic){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://kodeasik.000webhostapp.com/supplier-000.php?action=simpan&id="+ id
                +"&nama="+ Uri.encode( nama ) +"&alamat="+ Uri.encode( alamat )
                +"&telp="+ Uri.encode( telp ) +"&email="+ Uri.encode( email )
                +"&pic="+ Uri.encode( pic );

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String id, nama, telp;

                        Log.d("Hendro ", "onResponse: "+ response.toString());

                        if (response.optString("result").equals("true")){
                            Toast.makeText(getApplicationContext(), "Yeay, data pertambah!", Toast.LENGTH_SHORT).show();

                            //panggil fungsi load pada fragment
                            loadFragment(new SecondFragment());

                        }else{
                            Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                        }                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Events: ", error.toString());

                Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }

    void simpanCustomer(String id, String nama, String telp){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://kodeasik.000webhostapp.com/customer-000.php?action=simpan&id="+ id +"&nama="+ Uri.encode( nama ) +"&telp="+ Uri.encode( telp );

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String id, nama, telp;

                        Log.d("Hendro ", "onResponse: "+ response.toString());

                        if (response.optString("result").equals("true")){
                            Toast.makeText(getApplicationContext(), "Yeay, data pertambah!", Toast.LENGTH_SHORT).show();

                            //panggil fungsi load pada fragment
                            loadFragment(new FirstFragment());

                        }else{
                            Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                        }                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Events: ", error.toString());

                Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        };
    }
}