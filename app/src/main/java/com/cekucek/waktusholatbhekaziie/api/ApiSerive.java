package com.cekucek.waktusholatbhekaziie.api;

import com.cekucek.waktusholatbhekaziie.model.ModelJadwal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiSerive {

    @GET("bekasi.json")
    Call<ModelJadwal> getJadwal();
}
