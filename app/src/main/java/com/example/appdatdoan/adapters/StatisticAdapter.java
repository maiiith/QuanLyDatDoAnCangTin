package com.example.appdatdoan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdatdoan.R;
import com.example.appdatdoan.models.RevenueStat;

import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatViewHolder> {

    private Context context;
    private List<RevenueStat> statList;

    public StatisticAdapter(Context context, List<RevenueStat> statList) {
        this.context = context;
        this.statList = statList;
    }

    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_statistic, parent, false);
        return new StatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatViewHolder holder, int position) {
        RevenueStat stat = statList.get(position);
        holder.tvStatPeriod.setText("Tháng " + stat.getPeriod());
        holder.tvStatRevenue.setText(String.format("%,.0f ₫", stat.getTotalRevenue()));
        
        String topFoods = stat.getTopFoods();
        if (topFoods != null && !topFoods.isEmpty()) {
            holder.tvTopFoods.setText(topFoods);
        } else {
            holder.tvTopFoods.setText("Chưa có dữ liệu món ăn");
        }
    }

    @Override
    public int getItemCount() {
        return statList.size();
    }

    public static class StatViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatPeriod, tvStatRevenue, tvTopFoods;

        public StatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatPeriod = itemView.findViewById(R.id.tvStatPeriod);
            tvStatRevenue = itemView.findViewById(R.id.tvStatRevenue);
            tvTopFoods = itemView.findViewById(R.id.tvTopFoods);
        }
    }
}
