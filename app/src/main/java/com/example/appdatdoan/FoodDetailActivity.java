package com.example.appdatdoan;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdatdoan.databinding.ActivityFoodDetailBinding;
import com.example.appdatdoan.models.Food;

public class FoodDetailActivity extends AppCompatActivity {

    private ActivityFoodDetailBinding binding;
    private Food food;
    private int quantity = 1;
    private String selectedSize = "M";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackDetail.setOnClickListener(v -> finish());
        
        food = (Food) getIntent().getSerializableExtra("food");

        if (food != null) {
            binding.tvFoodNameDetail.setText(food.getName());
            binding.tvFoodPriceDetail.setText(String.format("%,.0f ₫", food.getPrice()));
            binding.tvFoodDescription.setText(food.getDescription());
            
            binding.rbSizeM.setText("Size M: " + String.format("%,.0f ₫", food.getPrice()));
            binding.rbSizeL.setText("Size L: " + String.format("%,.0f ₫", food.getPriceL()));

            com.bumptech.glide.Glide.with(this)
                    .load(food.getImageResource())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(binding.ivFoodDetail);
        }

        binding.rgSize.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSizeL) {
                selectedSize = "L";
                binding.tvFoodPriceDetail.setText(String.format("%,.0f ₫", food.getPriceL()));
            } else {
                selectedSize = "M";
                binding.tvFoodPriceDetail.setText(String.format("%,.0f ₫", food.getPrice()));
            }
        });

        binding.btnPlus.setOnClickListener(v -> {
            quantity++;
            binding.tvQuantity.setText(String.valueOf(quantity));
        });

        binding.btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addItem(food, quantity, selectedSize);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
