package com.example.gatask;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class SignUpFragment extends Fragment {

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private Button registerButton;
    private RegViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        editTextFullName = view.findViewById(R.id.editTextText);
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextPassword = view.findViewById(R.id.editTextTextPassword);
        registerButton = view.findViewById(R.id.button);


        // Use the shared ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RegViewModel.class);

       // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // Handle registration button click


                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();

                if (fullName.isEmpty() || !fullName.matches("[a-zA-Z]+")) {
                    editTextFullName.setError("Name must have only characters");
                    return;
                }

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Invalid email address");
                    return;
                }
                if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
                    editTextPhone.setError("Invalid mobile number");
                    return;
                }

                if (password.isEmpty() || password.length() < 8) {
                    editTextPassword.setError("Password must have minimum 8 characters");
                    return;
                }

                UserEntity existingUser = DB.getDB(requireContext()).userDao().getUserByEmail(email);

                if(existingUser != null) {
                    Toast.makeText(getActivity(), "user already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    UserEntity user = new UserEntity(fullName, phone, email, password);
                    DB.getDB(requireContext()).userDao().adduser(user);

                    // Set the values in the shared ViewModel
                    viewModel.setUsername(fullName);
                    viewModel.setEmail(email);
                    viewModel.setMob(phone);
                    viewModel.setPassword(password);

                    // Navigate to the next fragment or perform other actions
                    NavController navController = Navigation.findNavController(v);
                    navController.navigateUp();
                }
            }

        });

    }
}

