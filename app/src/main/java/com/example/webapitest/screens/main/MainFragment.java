package com.example.webapitest.screens.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webapitest.MainActivity;
import com.example.webapitest.R;
import com.example.webapitest.databinding.FragmentMainBinding;
import com.example.webapitest.screens.auth.AuthorizationFragment;
import com.example.webapitest.screens.ui.PermissionAdapter;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MainFragment extends MvpAppCompatFragment implements MainView {

    private FragmentMainBinding binding;

    @InjectPresenter
    public MainPresenter presenter;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @ProvidePresenter
    public MainPresenter providePresenter() {
        SharedPreferences preferences = requireActivity().getPreferences(Activity.MODE_PRIVATE);
        return new MainPresenter(preferences);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.bind(view);
        binding.logOutButton.setOnClickListener((buttonView) -> presenter.logout());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void hideProgressWithError() {
        binding.progressBar.setVisibility(View.GONE);
        binding.errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void moveToAuthorization() {
        AuthorizationFragment fragment = AuthorizationFragment.getInstance();
        MainActivity.navigate(getParentFragmentManager(), fragment);
    }

    @Override
    public void showInfo(String roleId, String username, String email, List<String> permissions) {
        MaterialTextView roleIdTextView = binding.roleIdTextView;
        MaterialTextView usernameTextView = binding.usernameTextView;
        MaterialTextView emailTextView = binding.emailTextView;
        RecyclerView recyclerView = binding.recyclerView;

        binding.progressBar.setVisibility(View.GONE);
        binding.permissionsTextView.setVisibility(View.VISIBLE);
        roleIdTextView.setVisibility(View.VISIBLE);
        usernameTextView.setVisibility(View.VISIBLE);
        emailTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        String na = getString(R.string.na);

        String roleIdText;
        String usernameText;
        String emailText;

        if (roleId == null) {
            roleIdText = na;
        } else {
            roleIdText = roleId;
        }

        if (username == null) {
            usernameText = na;
        } else {
            usernameText = username;
        }

        if (email == null) {
            emailText = na;
        } else {
            emailText = email;
        }

        roleIdTextView.setText(parseHtml(getString(R.string.role_id_replaceable, roleIdText)));
        usernameTextView.setText(parseHtml(getString(R.string.username_replaceable, usernameText)));
        emailTextView.setText(parseHtml(getString(R.string.email_replaceable, emailText)));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new PermissionAdapter(permissions));
    }

    @Override
    public void showError(int message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    private static Spanned parseHtml(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
    }
}
