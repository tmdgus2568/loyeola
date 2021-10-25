package com.example.loyeola;

public class Raid {
    private String category_main;
    private String category_sub;
    private int capacity;

    public Raid(String category_main, String category_sub, int capacity) {
        this.category_main = category_main;
        this.category_sub = category_sub;
        this.capacity = capacity;
    }


    public String getCategory_main() {
        return category_main;
    }

    public void setCategory_main(String category_main) {
        this.category_main = category_main;
    }

    public String getCategory_sub() {
        return category_sub;
    }

    public void setCategory_sub(String category_sub) {
        this.category_sub = category_sub;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
