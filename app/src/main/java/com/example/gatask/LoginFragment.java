package com.example.gatask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.preference.PreferenceManager;

public class LoginFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private RegViewModel viewModel;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = view.findViewById(R.id.editTextTextPassword3);
        buttonLogin = view.findViewById(R.id.button3);
        buttonSignUp = view.findViewById(R.id.button2);

        // Initialize SharedPreferences
        sharedPreferences =requireContext().getSharedPreferences("mypref",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Check if a user is already logged in
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (!TextUtils.isEmpty(savedEmail) && !TextUtils.isEmpty(savedPassword)) {
            // If user is already logged in, navigate to homeFragment
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_loginFragment_to_homeFragment2);
             // Exit the method to avoid further execution
        }

        // Use the shared ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RegViewModel.class);

        // Observers
        viewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String email) {
                if (email != null) {
                    editTextEmail.setText(email);
                }
            }
        });

        viewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String password) {
                if (password != null) {
                    editTextPassword.setText(password);
                }
            }
        });

        // Set click listeners
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();


                // Save email and password to SharedPreferences
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                UserEntity user = DB.getDB(requireContext()).userDao().getUserByEmailAndPassword(email, password);

                if (user != null) {
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_loginFragment_to_homeFragment2);
                } else {
                    Toast.makeText(getActivity(), "User doesn't exist. Please sign up.", Toast.LENGTH_SHORT).show();
                }

        });

        buttonSignUp.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment));
    }
}
