package com.example.webapitest.web;

import com.example.webapitest.web.responses.AuthResponse;
import com.example.webapitest.web.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {

    @POST("oauth/token?grant_type=password")
    @Headers("Authorization: Basic YW5kcm9pZC1jbGllbnQ6cGFzc3dvcmQ=")
    Call<AuthResponse> authorize(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("user")
    Call<UserResponse> getUser(@Header("Authorization") String accessToken);

    static String getAuthHeader(String accessToken) {
        return "Bearer " + accessToken;
    }
}
