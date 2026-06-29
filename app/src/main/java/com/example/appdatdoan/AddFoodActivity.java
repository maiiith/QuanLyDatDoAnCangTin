package com.example.appdatdoan;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityAddFoodBinding;
import com.example.appdatdoan.models.Category;
import com.example.appdatdoan.models.Food;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    private ActivityAddFoodBinding binding;
    private DatabaseHelper databaseHelper;
    private List<Category> categories;
    private int foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnBack.setOnClickListener(v -> finish());

        categories = databaseHelper.getAllCategories();
        List<String> categoryNames = new ArrayList<>();
        for (Category c : categories) {
            categoryNames.add(c.getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategory.setAdapter(spinnerAdapter);

        foodId = getIntent().getIntExtra("food_id", -1);
        if (foodId != -1) {
            binding.tvTitle.setText("Sửa thông tin món ăn");
            Food foodToEdit = databaseHelper.getFoodById(foodId);
            if (foodToEdit != null) {
                binding.etFoodName.setText(foodToEdit.getName());
                // Remove decimal for VND display if it ends with .0
                String priceStr = String.valueOf(foodToEdit.getPrice());
                if (priceStr.endsWith(".0")) priceStr = priceStr.replace(".0", "");
                binding.etFoodPrice.setText(priceStr);

                String priceLStr = String.valueOf(foodToEdit.getPriceL());
                if (priceLStr.endsWith(".0")) priceLStr = priceLStr.replace(".0", "");
                binding.etFoodPriceL.setText(priceLStr);

                binding.etFoodDescription.setText(foodToEdit.getDescription());
                binding.etFoodImage.setText(foodToEdit.getImageResource());

                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getId() == foodToEdit.getCategoryId()) {
                        binding.spinnerCategory.setSelection(i);
                        break;
                    }
                }
            }
        }

        binding.btnAddFood.setOnClickListener(v -> {
            String name = binding.etFoodName.getText().toString().trim();
            String priceStr = binding.etFoodPrice.getText().toString().trim();
            String priceLStr = binding.etFoodPriceL.getText().toString().trim();
            String description = binding.etFoodDescription.getText().toString().trim();
            String image = binding.etFoodImage.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || priceLStr.isEmpty() || description.isEmpty() || image.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = 0;
            double priceL = 0;
            try {
                price = Double.parseDouble(priceStr);
                priceL = Double.parseDouble(priceLStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPosition = binding.spinnerCategory.getSelectedItemPosition();
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                return;
            }

            int categoryId = categories.get(selectedPosition).getId();

            boolean success;
            if (foodId != -1) {
                Food food = new Food(foodId, name, categoryId, price, priceL, description, image);
                success = databaseHelper.updateFood(food);
            } else {
                Food food = new Food(0, name, categoryId, price, priceL, description, image);
                success = databaseHelper.addFood(food);
            }

            if (success) {
                Toast.makeText(this, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
