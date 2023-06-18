package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.baroad.post.TripPost1;
import com.example.baroad.post.TripPost2;
import com.example.baroad.post.TripPost3;
import com.example.baroad.post.TripPost4;
import com.example.baroad.post.TripPost5;
import com.example.baroad.post.TripPost6;

public class MainFragment extends Fragment {

    MainActivity activity;
    public static Fragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //특정 글씨 강조
        TextView textView = view.findViewById(R.id.edit_search_text);
        String content = textView.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word ="여행";
        int start = content.indexOf(word);
        int end = start+word.length();
//        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5252A1")),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.setText(spannableString);

        Button localBtn = view.findViewById(R.id.localBtn);
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(Select_local.newInstance());
            }
        });

        Button seasonBtn = view.findViewById(R.id.seasonBtn);
        seasonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(Select_season.newInstance());
            }
        });

        ImageButton post1 = view.findViewById(R.id.post1);
        post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost1.newInstance());
            }
        });
        ImageButton post2 = view.findViewById(R.id.post2);
        post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost2.newInstance());
            }
        });
        ImageButton post3 = view.findViewById(R.id.post3);
        post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost3.newInstance());
            }
        });
        ImageButton post4 = view.findViewById(R.id.post4);
        post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost4.newInstance());
            }
        });
        ImageButton post5 = view.findViewById(R.id.post5);
        post5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost5.newInstance());
            }
        });
        ImageButton post6 = view.findViewById(R.id.post6);
        post6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost6.newInstance());
            }
        });


        Button gotoMyplan = view.findViewById(R.id.gotoMyplan);
        gotoMyplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(MyPlan.newInstance());
            }
        });

        return view;


    }
}