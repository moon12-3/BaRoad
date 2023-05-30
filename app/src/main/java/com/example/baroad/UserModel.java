package com.example.baroad;

public class UserModel {
    String profile = "";
    String name = "";

    public UserModel(String name, String profile) {
        this.name = name;
        this.profile = profile;
    }

    public UserModel() {
        this.name = "";
        this.profile = "";
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }
}
