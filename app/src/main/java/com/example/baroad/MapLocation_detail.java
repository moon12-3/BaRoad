package com.example.baroad;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MapLocation_detail extends Fragment {

    TextView LocName, LocDetail, Url, Phone;
    ImageButton BackBtn;
    Bundle mybundle;
    ImageView loc_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Integer imges[] = new Integer[]{ R.drawable.loc_img1,  R.drawable.loc_img2,  R.drawable.loc_img3,
                R.drawable.loc_img4, R.drawable.loc_img5, R.drawable.loc_img6, R.drawable.loc_img7, R.drawable.loc_img8};
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map_location_detail, container, false);
        LocName = view.findViewById(R.id.loc_name);
        LocDetail = view.findViewById(R.id.loc_icon_text);
        Url = view.findViewById(R.id.loc_link_text);
        Phone = view.findViewById(R.id.loc_call_text);
        loc_img = view.findViewById(R.id.loc_img);

        loc_img.setImageResource(imges[(int) (Math.random()*9)]);

        Bundle bundle = getArguments();
        LocName.setText(bundle.getString("name"));
        LocDetail.setText(bundle.getString("detail"));
        Url.setText(bundle.getString("link"));
        Phone.setText(bundle.getString("phone"));
        mybundle= bundle.getBundle("mybundle");


        return view;
    }
}