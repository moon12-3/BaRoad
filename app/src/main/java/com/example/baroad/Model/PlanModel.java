package com.example.baroad.Model;

public class PlanModel {
    public String date;
    public String local;
    public String time;
    public PlanModel(String date, String local) {
        this.date = date;
        this.local = local;
        this.time = date+System.currentTimeMillis();
    }

    public PlanModel() {
        this.date = "";
        this.local = "";
        this.time = date+System.currentTimeMillis();
    }
}
