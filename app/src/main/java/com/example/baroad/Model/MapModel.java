package com.example.baroad.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

public class MapModel implements Parcelable {
    public LatLng location;
    public String name, detail, url, phone;
    public Long time;

    public MapModel(String name, String detail, LatLng location, String url, String phone, Long time) {
        this.name = name;
        this.detail = detail;
        this.time = time;
        this.url = url;
        this.phone = phone;
        this.location = location;
    }

    public MapModel() {
        this.name = "";
        this.detail = "";
        this.time = System.currentTimeMillis();
        this.url = "";
        this.phone = "";
        this.location = new LatLng(0, 0);
    }

    protected MapModel(Parcel in) {
        name = in.readString();
        detail = in.readString();
        time = in.readLong();
        phone = in.readString();
        url = in.readString();
        double latitude = in.readDouble();
        double longitude = in.readDouble();
        location = new LatLng(latitude, longitude);
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
        dest.writeDouble(location.latitude);
        dest.writeDouble(location.longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

