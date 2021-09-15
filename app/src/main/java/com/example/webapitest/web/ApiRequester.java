package com.example.webapitest.web;

import com.example.webapitest.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequester {

    private static final String BASE_PATH = "http://smart.eltex-co.ru:8271/api/v1/";

    private static WebService service;

    private ApiRequester() {
    }

    public static WebService getService() {
        if (service == null) {
            initService();
        }

        return service;
    }

    private static void initService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson));

        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging);

            builder.client(httpClient.build());
        }

        Retrofit retrofit = builder.build();

        service = retrofit.create(WebService.class);
    }
}
