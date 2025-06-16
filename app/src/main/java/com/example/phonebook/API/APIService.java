package com.example.phonebook.API;

import com.example.phonebook.Model.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("api/")
    Call<List<Province>> getProvinces(@Query("depth") int depth);
}
