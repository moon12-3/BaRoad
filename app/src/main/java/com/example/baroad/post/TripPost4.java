package com.example.baroad.post;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baroad.R;


public class TripPost4 extends Fragment {

    public TripPost4() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new TripPost4();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_post4, container, false);
    }
}