package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MapLocation_detail extends Fragment {

    TextView LocName, LocDetail, Url, Phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map_location_detail, container, false);
        LocName = view.findViewById(R.id.loc_name);
        LocDetail = view.findViewById(R.id.loc_icon_text);
        Url = view.findViewById(R.id.loc_link_text);
        Phone = view.findViewById(R.id.loc_call_text);

        Bundle bundle = getArguments();
        LocName.setText(bundle.getString("name"));
        LocDetail.setText(bundle.getString("detail"));
        Url.setText(bundle.getString("link"));
        Phone.setText(bundle.getString("phone"));
        return view;
    }
}