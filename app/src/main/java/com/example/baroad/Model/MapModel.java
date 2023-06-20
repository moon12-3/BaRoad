package com.example.baroad.Model;

import com.google.android.gms.maps.model.LatLng;

public class MapModel {
    public LatLng location;
    public String name, detail, url, phone;

    public MapModel( String name, String detail, LatLng location, String url, String phoen){
        this.name = name;
        this.detail = detail;
        this.location = location;
        this.url = url;
        this.phone = phoen;
    }
}
