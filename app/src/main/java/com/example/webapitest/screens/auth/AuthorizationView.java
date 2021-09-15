package com.example.webapitest.screens.auth;

import androidx.annotation.StringRes;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.OneExecution;
import moxy.viewstate.strategy.alias.Skip;

@OneExecution
public interface AuthorizationView extends MvpView {

    void showError(@StringRes int message);

    void showProgress();

    void hideProgress();

    @Skip
    void moveToMainScreen();
}
