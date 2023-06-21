package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class MapLocation_detail extends Fragment {

    TextView LocName, LocDetail, Url, Phone;
    ImageButton BackBtn;
    Bundle mybundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map_location_detail, container, false);
        LocName = view.findViewById(R.id.loc_name);
        LocDetail = view.findViewById(R.id.loc_icon_text);
        Url = view.findViewById(R.id.loc_link_text);
        Phone = view.findViewById(R.id.loc_call_text);
        BackBtn = view.findViewById(R.id.back_btn);

        Bundle bundle = getArguments();
        LocName.setText(bundle.getString("name"));
        LocDetail.setText(bundle.getString("detail"));
        Url.setText(bundle.getString("link"));
        Phone.setText(bundle.getString("phone"));
        mybundle= bundle.getBundle("mybundle");

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                Myplan_map map = new Myplan_map();
                Bundle b = new Bundle();
                b.putString("date", mybundle.getString("date"));
                b.putString("local", mybundle.getString("local"));
                map.setArguments(b);
                transaction.replace(R.id.frame, map);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}