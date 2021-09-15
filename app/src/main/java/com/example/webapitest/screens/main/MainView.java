package com.example.webapitest.screens.main;

import androidx.annotation.StringRes;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import moxy.viewstate.strategy.alias.OneExecution;
import moxy.viewstate.strategy.alias.Skip;

public interface MainView extends MvpView {

    @AddToEndSingle
    void hideProgressWithError();

    @Skip
    void moveToAuthorization();

    @AddToEndSingle
    void showInfo(String roleId, String username, String email, List<String> permissions);

    @OneExecution
    void showError(@StringRes int message);
}
