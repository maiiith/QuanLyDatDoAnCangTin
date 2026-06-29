package com.example.appdatdoan;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appdatdoan.adapters.OrderAdapter;
import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityOrderHistoryBinding;
import com.example.appdatdoan.models.Order;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private ActivityOrderHistoryBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnBack.setOnClickListener(v -> finish());

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId != -1) {
            List<Order> orders = databaseHelper.getOrdersByUser(userId);
            OrderAdapter adapter = new OrderAdapter(this, orders);
            binding.rvOrders.setLayoutManager(new LinearLayoutManager(this));
            binding.rvOrders.setAdapter(adapter);
        }
    }
}
