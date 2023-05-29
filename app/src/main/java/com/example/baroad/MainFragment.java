package com.example.baroad;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {

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
        return view;



    }
}