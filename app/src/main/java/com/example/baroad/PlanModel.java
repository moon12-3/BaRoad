package com.example.baroad;

public class PlanModel {
    public String date;
    public String local;
    public PlanModel(String date, String local) {
        this.date = date;
        this.local = local;
    }

    public PlanModel() {
        this.date = "";
        this.local = "";
    }
}
