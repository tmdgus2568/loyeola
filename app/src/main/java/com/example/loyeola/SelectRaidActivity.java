package com.example.loyeola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.loyeola.databinding.ActivitySelectRaidBinding;

import java.util.ArrayList;

public class SelectRaidActivity extends AppCompatActivity {
    private ActivitySelectRaidBinding binding;
    private RaidListviewAdapter adapter;
    ArrayList<Raid> raids;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_raid);
        binding.setActivity(this);

        adapter = new RaidListviewAdapter();

        raids = new ArrayList<>();

        raids.add(new Raid("군단장레이드","발탄",8));
        raids.add(new Raid("군단장레이드","비아키스",8));
        raids.add(new Raid("군단장레이드","쿠크세이튼",4));
        raids.add(new Raid("군단장레이드","아브렐슈드",8));
        raids.add(new Raid("어비스레이드","아르고스",8));

        adapter.setItems((ArrayList<Raid>)raids);

        binding.listviewRaid.setAdapter(adapter);

        // listview의 item onClickListener
        binding.listviewRaid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getApplicationContext(), SearchPartyActivity.class);
                intent.putExtra("title",raids.get(position).getCategory_sub());
                startActivity(intent);
            }
        });

    }
}