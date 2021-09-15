package com.example.webapitest.screens.auth;

import android.content.SharedPreferences;

import com.example.webapitest.MainActivity;
import com.example.webapitest.R;
import com.example.webapitest.mvp.BasePresenter;
import com.example.webapitest.web.ApiRequester;
import com.example.webapitest.web.RxRequests;
import com.example.webapitest.web.responses.AuthResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;
import retrofit2.Call;
import retrofit2.Response;

@InjectViewState
public class AuthorizationPresenter extends BasePresenter<AuthorizationView> {

    public AuthorizationPresenter(SharedPreferences preferences) {
        super(preferences);
    }

    public void authorize(String username, String password) {
        disposable = Completable.fromAction(() -> getViewState().showProgress())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .andThen(RxRequests.getAuthResponseSingle(username, password))
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(response -> {
                    AuthResponse responseBody = response.body();

                    if (response.isSuccessful() && responseBody != null) {
                        getPreferences().edit()
                                .putString(MainActivity.ACCESS_TOKEN_KEY, responseBody.accessToken)
                                .apply();

                        return Completable.complete();
                    } else {
                        String errorMessage = "Code: " + response.code();
                        Throwable error = new RuntimeException(errorMessage);
                        return Completable.error(error);
                    }
                })
                .subscribe(
                        () -> getViewState().moveToMainScreen(),
                        (error) -> {
                            log("Could not authorize", error);
                            getViewState().showError(R.string.something_went_wrong);
                            getViewState().hideProgress();
                        }
                );
    }
}
