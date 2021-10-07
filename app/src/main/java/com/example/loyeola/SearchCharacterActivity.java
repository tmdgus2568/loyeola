package com.example.loyeola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.loyeola.databinding.ActivitySearchCharacterBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/* 본캐 닉네임을 입력 후,
*  자신의 계정이 맞는지 확인 */

public class SearchCharacterActivity extends AppCompatActivity{
    private ActivitySearchCharacterBinding binding;
    private String mainChrName;
    private List<Character> searchCharacters;
    HashSet<String> supporterKinds = new HashSet(Arrays.asList("바드","홀리나이트"));
    private String userId;
    private SearchListviewAdapter adapter;

    private BtnSearchOnClickListener btnSearchOnClickListener;
    private BtnNextOnClickListener btnNextOnClickListener;
    ProgressDialog progressDialog;
    Disposable searchCharacterTask;
    Disposable searchDetailTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_character);
        binding.setActivity(this);

        NetworkUtil.setNetworkPolicy();

        // 키보드 밀려나지 않게 하기 위해 (editText때문)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        progressDialog = new ProgressDialog(this);
        // 로딩창 투명하게
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
        );
        progressDialog.setCancelable(false); // 눌러도 로딩창 안없어지게


        mainChrName = "";
        adapter = new SearchListviewAdapter();


        btnSearchOnClickListener = new BtnSearchOnClickListener();
        btnNextOnClickListener = new BtnNextOnClickListener();

        binding.btnSearch.setOnClickListener(btnSearchOnClickListener);
        binding.btnNext.setOnClickListener(btnNextOnClickListener);

    }

    // 캐릭터 조회 버튼 리스너
    class BtnSearchOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            mainChrName = binding.editSearch.getText().toString();

            searchCharacters = new ArrayList<>();
            searchCharacterTask();

        }



    }

    // 데이터베이스에 저장
    class BtnNextOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            searchDetailTask();

        }
    }


    // backgroundTask를 실행하는 메소드 -> 메인캐릭터의 닉네임으로 그 계정의 모든 캐릭터 조회
    public void searchCharacterTask() {
        // task에서 반환할 Hashmap
        HashMap<String, String> map = new HashMap<>();

        //onPreExecute(task 시작 전 실행될 코드 여기에 작성)
        if(!progressDialog.isShowing()) progressDialog.show();


        searchCharacterTask = Observable.fromCallable(() -> {
            //doInBackground(task에서 실행할 코드 여기에 작성)
            Document document = null;

            try {
                document = Jsoup.connect("https://lostark.game.onstove.com/Profile/Character/" + mainChrName).get();
                System.out.println("2");
                Character chr = null;

                Elements subChrs = document.select(".profile-character-list__char").select("li");
                for (Element subChr : subChrs) {
                    String name = subChr.select("span").select("button").select("span").text(); // 이름찾기
                    String level = (subChr.select("span").select("button").text()).replace(name, ""); // 레벨찾기
                    String classname = subChr.select("span").select("button").select("img").attr("alt");

                    int isMain = 0;
                    String role = "딜러";
                    if (mainChrName.equals(name)) isMain = 1;

                    if (supporterKinds.contains(classname)) role = "서포터";

                    chr = new Character(name, level, classname, isMain, role);
                    searchCharacters.add(chr);
                }


                for (Character c : searchCharacters) {
                    System.out.println(c.toString());
                }

                map.put("result","success");

            } catch (Exception e) {
                e.printStackTrace();
                map.put("result","failed");
            }



            return map;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HashMap<String, String>>() {
            @Override
            public void accept(HashMap<String, String> map) {
                //onPostExecute(task 끝난 후 실행될 코드 여기에 작성)
                if(map.get("result").equals("success")){
                    adapter.setItems((ArrayList<Character>) searchCharacters);
                    binding.listviewSearch.setAdapter(adapter);
                    if(progressDialog != null && progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                        System.out.println("progress off....");
                    }

                }

                searchCharacterTask.dispose();
            }
        });
    }

    // backgroundTask를 실행하는 메소드 -> 캐릭터들의 아이템레벨 저장하기 위해 다시 크롤링
     public void searchDetailTask() {
        // task에서 반환할 Hashmap
        HashMap<String, String> map = new HashMap<>();

        //onPreExecute(task 시작 전 실행될 코드 여기에 작성)
        if(!progressDialog.isShowing()) progressDialog.show();

        searchDetailTask = Observable.fromCallable(() -> {
            //doInBackground(task에서 실행할 코드 여기에 작성)
            try {
                for(Character c:searchCharacters){
                    Document document = null;
                    document = Jsoup.connect("https://lostark.game.onstove.com/Profile/Character/" + c.getNickname()).get();
                    Elements elements = document.select(".level-info2__expedition").select("span");
                    String item_level = elements.get(1).text();
                    c.setItem_level(item_level);


                    PHPRequest.insertCharacter(c);
                    map.put("result","success");

                }


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
                    Intent intent = new Intent(getApplicationContext(),SelectRaidActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"오류가 발생했습니다. 다시 진행해 주세요.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}