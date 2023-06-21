package com.example.baroad.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class MapModel implements Parcelable {
    public double latitude;
    public double longitude;
    public String name, detail, url, phone;
    public Long time;

    public MapModel(String name, String detail, double latitude, double longitude, String url, String phone, Long time) {
        this.name = name;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.url = url;
        this.phone = phone;
    }

    public MapModel() {
        this.name = "";
        this.detail = "";
        this.latitude = 0;
        this.longitude = 0;
        this.time = System.currentTimeMillis();
        this.url = "";
        this.phone = "";
    }

    protected MapModel(Parcel in) {
        name = in.readString();
        detail = in.readString();
        time = in.readLong();
        phone = in.readString();
        url = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<MapModel> CREATOR = new Creator<MapModel>() {
        @Override
        public MapModel createFromParcel(Parcel in) {
            return new MapModel(in);
        }

        @Override
        public MapModel[] newArray(int size) {
            return new MapModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeLong(time);
        dest.writeString(phone);
        dest.writeString(url);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

//    public LatLng getLocation() {
//        return new LatLng(latitude, longitude);
//    }
}

