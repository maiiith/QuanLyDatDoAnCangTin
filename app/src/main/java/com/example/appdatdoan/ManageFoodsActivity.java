package com.example.appdatdoan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appdatdoan.adapters.AdminFoodAdapter;
import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityManageFoodsBinding;
import com.example.appdatdoan.models.Food;

import java.util.List;

public class ManageFoodsActivity extends AppCompatActivity {

    private ActivityManageFoodsBinding binding;
    private DatabaseHelper databaseHelper;
    private AdminFoodAdapter adapter;
    private List<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnBack.setOnClickListener(v -> finish());
        
        binding.btnAddFoodTop.setOnClickListener(v -> {
            startActivity(new Intent(ManageFoodsActivity.this, AddFoodActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFoods();
    }

    private void loadFoods() {
        foods = databaseHelper.getAllFoods();
        adapter = new AdminFoodAdapter(this, foods, new AdminFoodAdapter.OnFoodActionListener() {
            @Override
            public void onEdit(Food food, int position) {
                Intent intent = new Intent(ManageFoodsActivity.this, AddFoodActivity.class);
                intent.putExtra("food_id", food.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Food food, int position) {
                boolean success = databaseHelper.deleteFood(food.getId());
                if (success) {
                    foods.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(ManageFoodsActivity.this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageFoodsActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.rvAdminFoods.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAdminFoods.setAdapter(adapter);
    }
}
