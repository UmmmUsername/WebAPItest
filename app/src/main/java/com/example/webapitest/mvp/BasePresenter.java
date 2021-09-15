package com.example.webapitest.mvp;

import android.content.SharedPreferences;
import android.util.Log;

import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import moxy.MvpView;

public abstract class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    private final SharedPreferences preferences;
    protected Disposable disposable;

    public BasePresenter(SharedPreferences preferences) {
        super();
        this.preferences = preferences;
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    protected SharedPreferences getPreferences() {
        return preferences;
    }

    protected void log(String message, Throwable throwable) {
        Log.d(getClass().getSimpleName(), message, throwable);
    }
}
