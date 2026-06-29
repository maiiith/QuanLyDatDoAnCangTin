package com.example.appdatdoan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatdoan.R;
import com.example.appdatdoan.models.Order;
import com.example.appdatdoan.models.User;

import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private OnOrderApproveListener listener;

    public interface OnOrderApproveListener {
        void onApprove(Order order, int position);
    }

    public AdminOrderAdapter(Context context, List<Order> orderList, OnOrderApproveListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_order, parent, false);
        return new AdminOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText("#" + order.getId());
        holder.tvUserId.setText(String.valueOf(order.getUserId()));
        holder.tvOrderTotal.setText(String.format("%,.0f ₫", order.getTotalAmount()));
        holder.tvOrderStatus.setText("Trạng thái: " + order.getStatus());
        
        String addr = order.getAddress();
        holder.tvOrderAddress.setText(addr != null && !addr.isEmpty() ? addr : "Không cung cấp");

        com.example.appdatdoan.database.DatabaseHelper db = new com.example.appdatdoan.database.DatabaseHelper(context);
        holder.tvOrderSummary.setText(db.getOrderSummaryString(order.getId()));

        User user = db.getUserById(order.getUserId());
        if (user != null) {
            holder.tvUserPhone.setText(user.getPhone());
        } else {
            holder.tvUserPhone.setText("Không rõ");
        }

        if ("Completed".equals(order.getStatus())) {
            holder.btnApprove.setEnabled(false);
            holder.btnApprove.setText("Đã duyệt");
        } else {
            holder.btnApprove.setEnabled(true);
            holder.btnApprove.setText("Duyệt");
            holder.btnApprove.setOnClickListener(v -> listener.onApprove(order, position));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvUserId, tvUserPhone, tvOrderTotal, tvOrderStatus, tvOrderAddress, tvOrderSummary;
        Button btnApprove;

        public AdminOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvUserPhone = itemView.findViewById(R.id.tvUserPhone);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvOrderSummary = itemView.findViewById(R.id.tvOrderSummary);
            btnApprove = itemView.findViewById(R.id.btnApprove);
        }
    }
}
