package com.example.phonebook.Interface;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMService {

    @Headers({
            "Content-Type: application/json",
            "Authorization: key=BEyhVgrUJo43CL9CTdYBxC9iAksPuKpXM9VNEwO4F4MMMc846-8CKmHx_a9k34gehxSwjSZtRynaMt6V-W7XL3w"
    })
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body JSONObject jsonObject);
}
