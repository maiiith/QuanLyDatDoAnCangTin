package com.example.appdatdoan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appdatdoan.adapters.CategoryAdapter;
import com.example.appdatdoan.adapters.FoodAdapter;
import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityMainBinding;
import com.example.appdatdoan.models.Category;
import com.example.appdatdoan.models.Food;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;
    private List<Food> allFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullname = prefs.getString("fullname", "User");
        binding.tvWelcome.setText("Xin chào, " + fullname);

        // Load Hero Banner
        ImageView ivBanner = findViewById(R.id.ivBanner);
        com.bumptech.glide.Glide.with(this)
            .load("https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=800&auto=format&fit=crop")
            .into(ivBanner);

        setupCategories();
        setupFoods();

        binding.ivLogout.setOnClickListener(v -> {
            getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
        
        binding.ivCart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        binding.ivHistory.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
        });
    }

    private void setupCategories() {
        List<Category> categories = databaseHelper.getAllCategories();
        // Prepend "Tất cả" category
        categories.add(0, new Category(0, "Tất cả", "https://cdn-icons-png.flaticon.com/512/1046/1046784.png"));

        categoryAdapter = new CategoryAdapter(this, categories, category -> {
            if (category.getId() == 0) {
                // Show all foods
                foodAdapter.updateData(databaseHelper.getAllFoods());
            } else {
                // Filter foods by category
                List<Food> filteredFoods = databaseHelper.getFoodsByCategory(category.getId());
                foodAdapter.updateData(filteredFoods);
            }
            binding.rvFoods.scrollToPosition(0);
        });

        binding.rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategories.setAdapter(categoryAdapter);
    }

    private void setupFoods() {
        allFoods = databaseHelper.getAllFoods();
        foodAdapter = new FoodAdapter(this, allFoods, food -> {
            Intent intent = new Intent(MainActivity.this, FoodDetailActivity.class);
            intent.putExtra("food", food);
            startActivity(intent);
        });

        binding.rvFoods.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvFoods.setAdapter(foodAdapter);
    }
}