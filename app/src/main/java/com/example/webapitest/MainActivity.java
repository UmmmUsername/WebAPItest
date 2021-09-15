package com.example.webapitest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.webapitest.fragments.AuthorizationFragment;
import com.example.webapitest.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    public static final String ACCESS_TOKEN_KEY = "accessToken";
    public static final String REFRESH_TOKEN_KEY = "refreshToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
        Fragment fragment;

        if (preferences.contains(ACCESS_TOKEN_KEY)) {
            fragment = AuthorizationFragment.getInstance();
        } else {
            fragment = MainFragment.getInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_view, fragment)
                .commit();
    }
}
