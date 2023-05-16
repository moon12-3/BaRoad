package com.example.baroad;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Mypage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mypage, container, false);
        //특정문자강조
        TextView textView = view.findViewById(R.id.myPage_id_txt);
        String content = textView.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word ="임재연";
        int start = content.indexOf(word);
        int end = start+word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5252A1")),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        String word2 ="나고야";
        int start2 = content.indexOf(word2);
        int end2 = start2+word2.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5252A1")),start2,end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start2, end2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        return view;
    }
}