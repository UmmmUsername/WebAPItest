package com.example.webapitest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.webapitest.screens.auth.AuthorizationFragment;
import com.example.webapitest.screens.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    public static final String ACCESS_TOKEN_KEY = "accessToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();

        if (manager.getFragments().size() == 0) {
            SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
            Fragment fragment;

            if (preferences.contains(ACCESS_TOKEN_KEY)) {
                fragment = MainFragment.getInstance();
            } else {
                fragment = AuthorizationFragment.getInstance();
            }

            navigate(manager, fragment);
        }
    }

    public static void navigate(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }
}
