package com.example.appdatdoan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdatdoan.databinding.ActivityAdminDashboardBinding;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnManageOrders.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, ManageOrdersActivity.class));
        });

        binding.btnManageFoods.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, ManageFoodsActivity.class));
        });

        binding.btnStatistics.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, StatisticsActivity.class));
        });

        binding.ivLogoutAdmin.setOnClickListener(v -> {
            getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}
