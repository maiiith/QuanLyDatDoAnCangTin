package com.example.appdatdoan;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appdatdoan.adapters.CartAdapter;
import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityCartBinding;
import com.example.appdatdoan.models.CartItem;
import com.example.appdatdoan.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private DatabaseHelper databaseHelper;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnBack.setOnClickListener(v -> finish());

        List<CartItem> cartItems = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(this, cartItems);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCart.setAdapter(adapter);

        updateTotalPrice();

        binding.btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }

            String address = binding.etDeliveryAddress.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                binding.etDeliveryAddress.requestFocus();
                return;
            }

            double total = CartManager.getInstance().getTotalPrice();
            double finalTotal = total > 0 ? total + 15000 : 0;

            // Simulate payment
            new AlertDialog.Builder(this)
                    .setTitle("Thanh toán giả lập")
                    .setMessage("Xác nhận thanh toán số tiền " + String.format("%,.0f ₫", finalTotal) + "?")
                    .setPositiveButton("Thanh toán", (dialog, which) -> {
                        processOrder(cartItems, finalTotal, address);
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    private void updateTotalPrice() {
        double total = CartManager.getInstance().getTotalPrice();
        if (total > 0) {
            binding.tvSubtotal.setText(String.format("%,.0f ₫", total));
            binding.tvTotal.setText(String.format("%,.0f ₫", total + 15000)); // adding 15000 delivery
        } else {
            binding.tvSubtotal.setText("0 ₫");
            binding.tvTotal.setText("0 ₫");
        }
    }

    private void processOrder(List<CartItem> cartItems, double finalTotal, String address) {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId != -1) {
            List<OrderDetail> details = new ArrayList<>();
            for (CartItem item : cartItems) {
                double unitPrice = "L".equals(item.getSize()) ? item.getFood().getPriceL() : item.getFood().getPrice();
                details.add(new OrderDetail(0, 0, item.getFood().getId(), item.getQuantity(), unitPrice, item.getSize()));
            }

            long orderId = databaseHelper.createOrder(userId, finalTotal, address, details);
            if (orderId != -1) {
                CartManager.getInstance().clearCart();
                Toast.makeText(this, "Đặt hàng thành công! Đã thanh toán.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Đặt hàng thất bại.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
        }
    }
}
