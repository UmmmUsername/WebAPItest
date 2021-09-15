package com.example.webapitest.web;

import com.example.webapitest.web.responses.AuthResponse;
import com.example.webapitest.web.responses.UserResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;

public class RxRequests {

    public static Single<Response<AuthResponse>> getAuthResponseSingle(String username, String password) {
        return Single.fromCallable(() -> {
            Call<AuthResponse> request = ApiRequester.getService().authorize(username, password);
            return request.execute();
        });
    }

    public static Single<Response<UserResponse>> getUserResponseSingle(String accessToken) {
        return Single.fromCallable(() -> {
            String fullHeader = WebService.getAuthHeader(accessToken);
            Call<UserResponse> request = ApiRequester.getService().getUser(fullHeader);
            return request.execute();
        });
    }
}
