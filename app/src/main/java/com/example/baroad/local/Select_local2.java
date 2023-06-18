package com.example.baroad.local;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baroad.MainActivity;
import com.example.baroad.R;

public class Select_local2 extends Fragment {

    public Select_local2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_select_local2, container, false);
        rootView.findViewById(R.id.select_btn).setOnClickListener(v-> {
            Toast.makeText(getActivity(), "나고야(을)를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).changeFragment(4);
        });
        return rootView;
    }
}