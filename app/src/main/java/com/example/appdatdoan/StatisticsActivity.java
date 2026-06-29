package com.example.appdatdoan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appdatdoan.adapters.StatisticAdapter;
import com.example.appdatdoan.database.DatabaseHelper;
import com.example.appdatdoan.databinding.ActivityStatisticsBinding;
import com.example.appdatdoan.models.RevenueStat;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private ActivityStatisticsBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btnBack.setOnClickListener(v -> finish());

        List<RevenueStat> stats = databaseHelper.getMonthlyRevenue();
        StatisticAdapter adapter = new StatisticAdapter(this, stats);
        
        binding.rvStatistics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvStatistics.setAdapter(adapter);
    }
}
