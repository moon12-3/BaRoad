package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Select_local1 extends Fragment {

    public Select_local1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_select_local1, container, false);
        rootView.findViewById(R.id.select_btn).setOnClickListener(v-> {
            Toast.makeText(getActivity(), "COMING SOON!", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }
}