package com.example.baroad;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baroad.databinding.FragmentMypageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mypage extends Fragment {

    public static Mypage newInstance() {
        return new Mypage();
    }

    private DatabaseReference db;
    private FirebaseAuth auth;

    private String word;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mypage, container, false);
        //특정문자강조
        TextView textView = view.findViewById(R.id.myPage_id_txt);
        String content = textView.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = auth.getCurrentUser();

        // DB에서 User정보 가져오기
        DatabaseReference docRef = db.child("users").child(currentUser.getUid());
        docRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    word = userModel.getName();
                    Log.d("mytag", word);
                    TextView text = view.findViewById(R.id.myPage_id_txt);
                    text.setText(word+" 님 \n현재 나고야 여행중");
                } else {
                    Log.d("my_tag", "No such document");
                    word ="임재연";
                }
//                int start = content.indexOf(word);
//                int end = start+word.length();
//                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5252A1")),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("my_tag", "get failed with " + error.getMessage());
            }
        });

        // 로그아웃
        view.findViewById(R.id.log_out).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getActivity(), "로그아웃 되셨습니다.", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).endActivity();
        });

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