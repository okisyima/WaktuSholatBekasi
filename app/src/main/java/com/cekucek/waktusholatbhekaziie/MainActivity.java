package com.cekucek.waktusholatbhekaziie;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cekucek.waktusholatbhekaziie.api.ApiSerive;
import com.cekucek.waktusholatbhekaziie.api.ApiUrl;
import com.cekucek.waktusholatbhekaziie.model.ModelJadwal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView _lokasi, _fajr, _shurooq, _duhr, _ashr, _magrib, _isha;
    private FloatingActionButton _reflesh;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Waktu Sholat Bekasi Ajah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _lokasi     = findViewById(R.id.lokasi);
        _fajr       = findViewById(R.id.fajr);
        _shurooq    = findViewById(R.id.shurooq);
        _duhr       = findViewById(R.id.duhr);
        _ashr       = findViewById(R.id.ashr);
        _magrib     = findViewById(R.id.magrib);
        _isha       = findViewById(R.id.isha);
        _reflesh    = findViewById(R.id.reflesh_fab);

        dapatJadwal();

        _reflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dapatJadwal();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if ( id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void dapatJadwal () {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Sabarin Bre..");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiSerive apiService = retrofit.create(ApiSerive.class);
        Call<ModelJadwal> call = apiService.getJadwal();

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    _lokasi.setText(response.body().getCity() + ", " + response.body().getItems().get(0).getDateFor());
                    _fajr.setText(response.body().getItems().get(0).getFajr());
                    _shurooq.setText(response.body().getItems().get(0).getShurooq());
                    _duhr.setText(response.body().getItems().get(0).getDhuhr());
                    _ashr.setText(response.body().getItems().get(0).getAsr());
                    _magrib.setText(response.body().getItems().get(0).getMaghrib());
                    _isha.setText(response.body().getItems().get(0).getIsha());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Cek Kuota Bre..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
