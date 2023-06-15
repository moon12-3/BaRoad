package com.example.baroad.Model;

import com.google.android.gms.maps.model.LatLng;

public class MapModel {
    public LatLng location;
    public String name, detail;

    public MapModel( String name, String detail, LatLng location){
        this.name = name;
        this.detail = detail;
        this.location = location;
    }
}
