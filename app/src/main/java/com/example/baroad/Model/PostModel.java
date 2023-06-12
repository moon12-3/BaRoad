package com.example.baroad.Model;

import java.util.ArrayList;

public class PostModel {
    public String season;
    public String pName;
    public long date;

    public PostModel(long date, String pName, String season) {
        this.date = date;
        this.pName = pName;
        this.season = season;
    }

    public PostModel() {
        season = "#봄 #여름 #가을 #겨울";
        pName = "나고야 여행 추천 코스";
        date = System.currentTimeMillis();
    }
}
