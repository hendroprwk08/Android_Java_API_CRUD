package com.hendro.android_java_api_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DetilCustomer extends AppCompatActivity {

    ProgressBar pb;
    EditText etID,etNama, etTelp;
    Button btHapus, btUbah;
    String id, nama, telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_customer);

        pb = (ProgressBar) findViewById(R.id.pb);
        etID = (EditText) findViewById(R.id.et_id);
        etNama = (EditText) findViewById(R.id.et_nama);
        etTelp = (EditText) findViewById(R.id.et_telp);
        btUbah = (Button) findViewById(R.id.bt_ubah);
        btHapus = (Button) findViewById(R.id.bt_hapus);

        //tombol back
        getSupportActionBar().setTitle("Data Peserta"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action back

        //tangkap bundle
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();

        //letakkan isi bundle
        id = bundle.getString("b_id");
        nama = bundle.getString("b_nama");
        telp = bundle.getString("b_telp");

        //letakkan pada textview
        etID.setText(id);
        etNama.setText(nama);
        etTelp.setText(telp);

        //operasi ubah data
        btUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama =  etNama.getText().toString();
                telp =  etTelp.getText().toString();

                pb.setVisibility(ProgressBar.VISIBLE); //munculkan progressbar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://kodeasik.000webhostapp.com/customer-000.php?action=ubah&id="+ id +"&nama="+ Uri.encode(nama) + "&telp="+ Uri.encode(telp);

                Log.d("Hendro ", "onClick: " + url);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                String id, nama, telp;

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data terubah!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                    }
                });

                queue.add(jsObjRequest);
            }
        });

        btHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(ProgressBar.VISIBLE); //tampilkan progress bar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://kodeasik.000webhostapp.com/customer-000.php?action=hapus&id="+ id;

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                String id, nama, telp;

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data terhapus!", Toast.LENGTH_SHORT).show();

                                    MainActivity m = new MainActivity();
                                    m.loadFragment(new FirstFragment()); //refresh fragment

                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsObjRequest);
            }
        });
    }
}