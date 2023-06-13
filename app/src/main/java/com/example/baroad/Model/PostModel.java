package com.example.baroad.Model;

import java.util.ArrayList;

public class PostModel {
    public String season;
    public String pName;
    public long date;

    public int postIdx;

    public PostModel(long date, String pName, String season, int postIdx) {
        this.date = date;
        this.pName = pName;
        this.season = season;
        this.postIdx = postIdx;
    }

    public PostModel() {
        season = "#봄 #여름 #가을 #겨울";
        pName = "나고야 여행 추천 코스";
        date = System.currentTimeMillis();
        postIdx = 1;
    }
}
