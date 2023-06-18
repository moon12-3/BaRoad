package com.example.baroad.season;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baroad.MainActivity;
import com.example.baroad.Model.UserModel;
import com.example.baroad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Select_season4 extends Fragment {


    public Select_season4() {
        // Required empty public constructor
    }

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_select_season4, container, false);
        rootView.findViewById(R.id.select_btn).setOnClickListener(v-> {
            Toast.makeText(getActivity(), "계절 : 겨울", Toast.LENGTH_SHORT).show();
            DatabaseReference docRef = db.child("users").child(auth.getCurrentUser().getUid());
            docRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        userModel.setSeason("겨울");
                        db.child("users").child(auth.getCurrentUser().getUid()).setValue(userModel);
                        ((MainActivity)getActivity()).changeFragment(4);
                    } else {
                        Log.d("my_tag", "No such document");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("my_tag", "get failed with " + error.getMessage());
                }
            });
            ((MainActivity)getActivity()).changeFragment(4);
        });
        return rootView;
    }
}