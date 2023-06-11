package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Mypage_plan extends Fragment {

    public Mypage_plan() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new Mypage_plan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage_plan, container, false);
    }
}