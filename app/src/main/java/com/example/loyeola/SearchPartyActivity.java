package com.example.loyeola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.loyeola.databinding.ActivitySearchPartyBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPartyActivity extends AppCompatActivity {
    private ActivitySearchPartyBinding binding;
    private Intent selectRaidIntent;
    String title;
    ProgressDialog progressDialog;
    Disposable selectPartiesTask;

    private PartyListviewAdapter adapter;
    ArrayList<Party> parties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_party);
        binding.setActivity(this);

        progressDialog = new ProgressDialog(this);
        // 로딩창 투명하게
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
        );
        progressDialog.setCancelable(false); // 눌러도 로딩창 안없어지게

        selectRaidIntent = getIntent();

        title = selectRaidIntent.getStringExtra("title");

        binding.tvTitle.setText(title);

        adapter = new PartyListviewAdapter();

        selectPartiesTask();


    }

    // backgroundTask를 실행하는 메소드 -> 파티들 가져오기
    public void selectPartiesTask() {
        // task에서 반환할 Hashmap
        HashMap<String, String> map = new HashMap<>();

        //onPreExecute(task 시작 전 실행될 코드 여기에 작성)
        if(!progressDialog.isShowing()) progressDialog.show();

        selectPartiesTask = Observable.fromCallable(() -> {
            //doInBackground(task에서 실행할 코드 여기에 작성)
            try {

                parties = PHPRequest.selectAllPartys(selectRaidIntent.getStringExtra("raid_id"));
                map.put("result","success");

            }catch (Exception e){
                e.printStackTrace();
                map.put("result","failed");
            }

            return map;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HashMap<String, String>>() {
            @Override
            public void accept(HashMap<String, String> map) {
                //onPostExecute(task 끝난 후 실행될 코드 여기에 작성)

                if (map.get("result").equals("success")){

                    progressDialog.dismiss();
                    if(parties!=null){
                        adapter.setItems(parties);
                        binding.listviewParty.setAdapter(adapter);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"오류가 발생했습니다. 다시 진행해 주세요.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}