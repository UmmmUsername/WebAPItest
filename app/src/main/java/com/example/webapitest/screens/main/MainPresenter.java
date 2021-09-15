package com.example.webapitest.screens.main;

import android.content.SharedPreferences;

import com.example.webapitest.MainActivity;
import com.example.webapitest.R;
import com.example.webapitest.mvp.BasePresenter;
import com.example.webapitest.web.RxRequests;
import com.example.webapitest.web.responses.UserResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(SharedPreferences preferences) {
        super(preferences);
    }

    @Override
    protected void onFirstViewAttach() {
        String accessToken = getPreferences().getString(MainActivity.ACCESS_TOKEN_KEY, null);

        if (accessToken == null) {
            getViewState().moveToAuthorization();
        }

        disposable = RxRequests.getUserResponseSingle(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (response) -> {
                            UserResponse responseBody = response.body();

                            if (response.isSuccessful() && responseBody != null) {
                                getViewState().showInfo(responseBody.roleId, responseBody.username, responseBody.email, responseBody.permissions);
                            } else {
                                logout();
                            }
                        },
                        (error) -> {
                            log("Could not get user", error);
                            getViewState().showError(R.string.something_went_wrong);
                            getViewState().hideProgressWithError();
                        }
                );
    }

    void logout() {
        getPreferences().edit().remove(MainActivity.ACCESS_TOKEN_KEY).apply();
        getViewState().moveToAuthorization();
    }
}
