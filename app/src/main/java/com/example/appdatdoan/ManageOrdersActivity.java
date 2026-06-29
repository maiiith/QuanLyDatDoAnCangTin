package com.example.appdatdoan;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appdatdoan.adapters.AdminOrderAdapter;
import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityManageOrdersBinding;
import com.example.appdatdoan.models.Order;

import java.util.List;

public class ManageOrdersActivity extends AppCompatActivity {

    private ActivityManageOrdersBinding binding;
    private DatabaseHelper databaseHelper;
    private AdminOrderAdapter adapter;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnBack.setOnClickListener(v -> finish());

        loadOrders();
    }

    private void loadOrders() {
        orders = databaseHelper.getAllOrders();
        adapter = new AdminOrderAdapter(this, orders, (order, position) -> {
            boolean success = databaseHelper.updateOrderStatus(order.getId(), "Completed");
            if (success) {
                order.setStatus("Completed");
                adapter.notifyItemChanged(position);
                Toast.makeText(ManageOrdersActivity.this, "Order Approved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageOrdersActivity.this, "Failed to approve order", Toast.LENGTH_SHORT).show();
            }
        });
        binding.rvAdminOrders.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAdminOrders.setAdapter(adapter);
    }
}
