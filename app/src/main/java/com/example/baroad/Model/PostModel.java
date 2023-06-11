package com.example.baroad.Model;

import java.util.ArrayList;

public class PostModel {
    public ArrayList<String> season;
    public String pName;
    public long date;

    public PostModel(long date, String pName) {
        this.date = date;
        this.pName = pName;
    }

    public PostModel() {
        season = new ArrayList<>();
        pName = "나고야 여행 추천 코스";
        date = System.currentTimeMillis();
    }
}
