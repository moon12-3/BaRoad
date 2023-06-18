package com.example.baroad.Model;

public class UserModel {
    String profile = "";
    String name = "";
    String season = "";

    public UserModel(String name, String profile, String season) {
        this.name = name;
        this.profile = profile;
        this.season = season;
    }

    public UserModel() {
        this.name = "";
        this.profile = "";
        this.season = "";
    }

    public void setSeason(String season) {this.season = season;}

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String getSeason() {
        return season;
    }
}
