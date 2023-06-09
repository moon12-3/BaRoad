package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baroad.databinding.FragmentLookAroundBinding;

public class LookAround extends Fragment {



    public LookAround() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_around, container, false);

        FragmentLookAroundBinding binding = FragmentLookAroundBinding.bind(view);

        Button aroud_post1 = view.findViewById(R.id.aroud_post1);
        aroud_post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost1.newInstance());
            }
        });
        Button aroud_post2 = view.findViewById(R.id.aroud_post2);
        aroud_post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost2.newInstance());
            }
        });
        Button aroud_post3 = view.findViewById(R.id.aroud_post3);
        aroud_post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost3.newInstance());
            }
        });
        Button aroud_post4 = view.findViewById(R.id.aroud_post4);
        aroud_post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost4.newInstance());
            }
        });

        return view;
    }

}