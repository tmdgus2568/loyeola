package com.example.loyeola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.loyeola.databinding.ActivitySearchPartyBinding;

public class SearchPartyActivity extends AppCompatActivity {
    private ActivitySearchPartyBinding binding;
    private Intent selectRaidIntent;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_party);
        binding.setActivity(this);

        selectRaidIntent = getIntent();

        title = selectRaidIntent.getStringExtra("title");

        binding.tvTitle.setText(title);

    }
}