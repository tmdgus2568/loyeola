package com.example.loyeola;

public class Character {
    private int id;
    private String nickname;
    private String item_level;
    private String level;
    private static String user_id;
    private int is_main = 0;
    private String role = "딜러";
    private String classname;

    public Character(String nickname, String level, String classname, int is_main, String role) {
        this.nickname = nickname;
//        this.user_id = user_id;
        this.level = level;
        this.classname = classname;
        this.is_main = is_main;
        this.role = role;
    }

    public Character(String nickname, String level, String classname){
        this.nickname = nickname;
        this.level = level;
        this.classname = classname;
    }

    // id가 저장되어있는 character라면,
    // 데이터베이스에 등록되어 있다.
    public Character(int id, String nickname, String item_level, String level, int is_main, String role,String classname) {
        this.id = id;
        this.nickname = nickname;
//        this.user_id = user_id;
        this.item_level = item_level;
        this.level = level;
        this.classname = classname;
        this.is_main = is_main;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getItem_level() {
        return item_level;
    }

    public void setItem_level(String item_level) {
        this.item_level = item_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        Character.user_id = user_id;
    }

    public int isIs_main() {
        return is_main;
    }

    public void setIs_main(int is_main) {
        this.is_main = is_main;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }


    @Override
    public String toString() {
        return "Character{" +
                "nickname='" + nickname + '\'' +
                ", item_level='" + item_level + '\'' +
                ", level='" + level + '\'' +
                ", user_id='" + user_id + '\'' +
                ", is_main=" + is_main +
                ", role='" + role + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }


}
