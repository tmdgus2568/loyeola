package com.example.loyeola;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.Disposable;


public class PHPRequest {
    static String phpServer = "http://192.168.219.100:8888/";

    // (nickname, item_level, level, user_id, is_main, role, class)
    public static String insertCharacter(Character c){
        try {
            String params = "nickname="+c.getNickname()
                    +"&item_level="+c.getItem_level()
                    +"&level="+c.getLevel()
                    +"&user_id="+ Character.getUser_id()
                    +"&is_main="+c.isIs_main()
                    +"&role="+c.getRole()
                    +"&class="+c.getClassname();
            URL url = new URL(phpServer + "insert_character_info.php?"+params);
            String result = httpConnect(url, params);
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            e.printStackTrace();
            return null;
        }

    }

    public static String insertUser(String id){
        try {
            String params = "id="+id;
            URL url = new URL(phpServer + "insert_user.php?"+params);
            String result =httpConnect(url, params);
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<Character> selectCharacters(String user_id){
        ArrayList<Character> characters = new ArrayList<>();
        String tag_json = "result";
        String tag_id = "id";
        String tag_nickname = "nickname";
        String tag_item_level = "item_level";
        String tag_level = "level";
        String tag_is_main = "is_main";
        String tag_role = "role";
        String tag_class = "class";
        String url = "select_character_info.php";
        String params = "user_id="+user_id;


        try{
            JSONObject jsonObject = new JSONObject(selectAll(url, params));

            JSONArray jsonArray = jsonObject.getJSONArray(tag_json);

            if(jsonArray == null){
                return null;
            }


            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i); // i번째 객체를 가져옴

                int id = item.getInt(tag_id);
                String nickname = item.getString(tag_nickname);
                String item_level = item.getString(tag_item_level);
                String level = item.getString(tag_level);
                int is_main = item.getInt(tag_is_main);
                String role = item.getString(tag_role);
                String classname = item.getString(tag_class);

                characters.add(new Character(id,nickname,item_level,level, is_main, role,classname));

            }

            return characters;


        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String httpConnect(URL url, String params) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(params.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
        String result = readStream(conn.getInputStream());
        conn.disconnect();
        return result;
    }


    private static String readStream(InputStream in) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    private static String selectAll(String urlString,String params){
        try {
            URL url = new URL(phpServer + urlString + "?" + params);
            String result = httpConnect(url, params);
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            e.printStackTrace();
            return null;
        }
    }


}
