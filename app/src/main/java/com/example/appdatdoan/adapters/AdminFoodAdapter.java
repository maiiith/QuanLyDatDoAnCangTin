package com.example.appdatdoan.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdatdoan.R;
import com.example.appdatdoan.models.Food;

import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.AdminFoodViewHolder> {

    private Context context;
    private List<Food> foodList;

    public interface OnFoodActionListener {
        void onEdit(Food food, int position);
        void onDelete(Food food, int position);
    }

    private OnFoodActionListener listener;

    public AdminFoodAdapter(Context context, List<Food> foodList, OnFoodActionListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_food, parent, false);
        return new AdminFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvAdminFoodName.setText(food.getName());
        holder.tvAdminFoodPrice.setText(String.format("%,.0f ₫", food.getPrice()));

        Glide.with(context)
                .load(food.getImageResource())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivAdminFood);

        holder.btnEditFood.setOnClickListener(v -> listener.onEdit(food, position));

        holder.btnDeleteFood.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa món ăn")
                    .setMessage("Bạn có chắc chắn muốn xóa " + food.getName() + "?")
                    .setPositiveButton("Xóa", (dialog, which) -> listener.onDelete(food, position))
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    
    public void updateData(List<Food> newFoodList) {
        this.foodList = newFoodList;
        notifyDataSetChanged();
    }

    public static class AdminFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAdminFood, btnDeleteFood, btnEditFood;
        TextView tvAdminFoodName, tvAdminFoodPrice;

        public AdminFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdminFood = itemView.findViewById(R.id.ivAdminFood);
            btnDeleteFood = itemView.findViewById(R.id.btnDeleteFood);
            btnEditFood = itemView.findViewById(R.id.btnEditFood);
            tvAdminFoodName = itemView.findViewById(R.id.tvAdminFoodName);
            tvAdminFoodPrice = itemView.findViewById(R.id.tvAdminFoodPrice);
        }
    }
}
