package com.example.appdatdoan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatdoan.R;
import com.example.appdatdoan.CartManager;
import com.example.appdatdoan.CartActivity;
import com.example.appdatdoan.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartList;

    public CartAdapter(Context context, List<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);
        holder.tvCartFoodName.setText(item.getFood().getName());
        holder.tvCartFoodSize.setText("Size: " + item.getSize());
        double unitPrice = "L".equals(item.getSize()) ? item.getFood().getPriceL() : item.getFood().getPrice();
        holder.tvCartFoodPrice.setText(String.format("%,.0f ₫", unitPrice));
        holder.tvCartQuantity.setText("x" + item.getQuantity());
        holder.tvCartTotal.setText(String.format("%,.0f ₫", item.getTotalPrice()));

        com.bumptech.glide.Glide.with(context)
                .load(item.getFood().getImageResource())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivCartFood);
                
        holder.btnRemoveCart.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                CartManager.getInstance().removeFromCart(currentPos);
                notifyDataSetChanged();
                if (context instanceof CartActivity) {
                    ((CartActivity) context).recreate();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCartFood, btnRemoveCart;
        TextView tvCartFoodName, tvCartFoodSize, tvCartFoodPrice, tvCartQuantity, tvCartTotal;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartFood = itemView.findViewById(R.id.ivCartFood);
            btnRemoveCart = itemView.findViewById(R.id.btnRemoveCart);
            tvCartFoodName = itemView.findViewById(R.id.tvCartFoodName);
            tvCartFoodSize = itemView.findViewById(R.id.tvCartFoodSize);
            tvCartFoodPrice = itemView.findViewById(R.id.tvCartFoodPrice);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvCartTotal = itemView.findViewById(R.id.tvCartTotal);
        }
    }
}
