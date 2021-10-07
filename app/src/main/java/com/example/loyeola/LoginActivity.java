package com.example.loyeola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.loyeola.databinding.ActivityLoginBinding;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "사용자";
    private ActivityLoginBinding binding;
    Session session;
    private SessionCallback sessionCallback = new SessionCallback();
    private static Intent intent;
    Disposable selectCharactersTask;
    ArrayList<Character> characters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);

        Log.d("GET_KEYHASH", getKeyHash(LoginActivity.this));

//        btnLogin = findViewById(R.id.btnLogin);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        if (Session.getCurrentSession().checkAndImplicitOpen()){

            selectCharactersTask();


        }



        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Session.getCurrentSession().checkAndImplicitOpen()) {
                    Log.d(TAG, "onClick: 로그인 세션살아있음");
                    // 카카오 로그인 시도 (창이 안뜬다.)
                    sessionCallback.requestMe();
                    System.out.println("user_id !!! "+Character.getUser_id());
                    selectCharactersTask();
                    if(characters != null){
                        intent = new Intent(getApplicationContext(), SelectRaidActivity.class);
                        startActivity(intent);
                    }
                    else{
                        intent = new Intent(getApplicationContext(), SearchCharacterActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Log.d(TAG, "onClick: 로그인 세션끝남");
                    // 카카오 로그인 시도 (창이 뜬다.)
                    session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
                }


            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
                return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // backgroundTask를 실행하는 메소드
    private void selectCharactersTask() {
        // task에서 반환할 Hashmap
        HashMap<String, String> map = new HashMap<>();

        //onPreExecute(task 시작 전 실행될 코드 여기에 작성)


        selectCharactersTask = Observable.fromCallable(() -> {
            //doInBackground(task에서 실행할 코드 여기에 작성)
            try{
                characters = PHPRequest.selectCharacters(Character.getUser_id());
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

                if(map.get("result").equals("failed")){
                    characters = null;
                }

                System.out.println("user_id !!! "+Character.getUser_id());
                if(characters != null){
                    intent = new Intent(getApplicationContext(), SelectRaidActivity.class);
                    startActivity(intent);
                }
                else{
                    intent = new Intent(getApplicationContext(), SearchCharacterActivity.class);
                    startActivity(intent);
                }
                selectCharactersTask.dispose();
            }
        });
    }
}