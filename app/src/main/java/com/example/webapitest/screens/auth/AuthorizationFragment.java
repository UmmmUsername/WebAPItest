package com.example.webapitest.screens.auth;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.webapitest.MainActivity;
import com.example.webapitest.R;
import com.example.webapitest.databinding.FragmentAuthorizationBinding;
import com.example.webapitest.screens.main.MainFragment;
import com.google.android.material.textfield.TextInputLayout;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AuthorizationFragment extends MvpAppCompatFragment implements AuthorizationView {

    private static final String USERNAME_KEY = "username";

    private FragmentAuthorizationBinding binding;

    @InjectPresenter
    public AuthorizationPresenter presenter;

    @ProvidePresenter
    public AuthorizationPresenter providePresenter() {
        SharedPreferences preferences = requireActivity().getPreferences(Activity.MODE_PRIVATE);
        return new AuthorizationPresenter(preferences);
    }

    public AuthorizationFragment() {
        super(R.layout.fragment_authorization);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorizationBinding.bind(view);

        binding.button.setOnClickListener((buttonView) -> {
            String username = getText(binding.nameTextField);
            String password = getText(binding.passwordTextField);

            if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
                presenter.authorize(username, password);
            } else {
                Toast.makeText(requireContext(), R.string.fill_in_fields, Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState != null) {
            String previousName = savedInstanceState.getString(USERNAME_KEY);
            EditText nameEditText = binding.nameTextField.getEditText();

            if (previousName != null && !previousName.isEmpty() && nameEditText != null) {
                nameEditText.setText(previousName);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(USERNAME_KEY, getText(binding.nameTextField));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showError(int message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        binding.button.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        binding.button.setEnabled(true);
    }

    @Override
    public void moveToMainScreen() {
        MainFragment fragment = MainFragment.getInstance();
        MainActivity.navigate(getParentFragmentManager(), fragment);
    }

    public static AuthorizationFragment getInstance() {
        return new AuthorizationFragment();
    }

    @Nullable
    private static String getText(TextInputLayout textInput) {
        EditText nameEditText = textInput.getEditText();

        if (nameEditText != null) {
            return nameEditText.getText().toString();
        } else {
            return null;
        }
    }
}
