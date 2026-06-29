package com.example.appdatdoan;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityRegisterBinding;
import com.example.appdatdoan.models.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnRegister.setOnClickListener(v -> {
            String fullname = binding.etFullname.getText().toString().trim();
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String phone = binding.etPhone.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();

            if (fullname.isEmpty() || username.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(0, username, password, fullname, phone, address, "user");
            boolean success = databaseHelper.registerUser(newUser);

            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish(); // Go back to login
            } else {
                Toast.makeText(this, "Registration failed, username might already exist", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvLogin.setOnClickListener(v -> {
            finish();
        });
    }
}
