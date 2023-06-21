package com.example.baroad.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baroad.Model.MapModel;
import com.example.baroad.Model.PlanModel;
import com.example.baroad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TripPost4 extends Fragment {

    public TripPost4() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new TripPost4();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_post4, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // WriteBatch 인스턴스 생성
        WriteBatch batch = db.batch();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd");
        String getTime = sdf.format(date);
        PlanModel plan = new PlanModel(getTime, "나고야");

        List<MapModel> docu = new ArrayList<>();
        docu.add(new MapModel("나고야 역", "1 Chome-1-4 Meieki, Nakamura Ward, Nagoya, Aichi", 35.17091527561959, 136.88153439890988, "", "", 1L));
        docu.add(new MapModel("크루즈 나고야", "2 Ungacho, Nakagawa Ward, Nagoya, Aichi 454-0001 일본", 35.16026168738063, 136.8839313181859, "", "", 2L));
        docu.add(new MapModel("레고랜드", "아이치현 나고야시 미나토구 긴조후토 2-2-1", 35.05061525591104, 136.84382929691986, "", "", 3L));
        docu.add(new MapModel("메이커스피어", "나고야시 미나토구 긴조후토 2-7-1", 35.05056566622372, 136.84711367116986, "", "", 4L));

        view.findViewById(R.id.add_cos).setOnClickListener(v->{
            db.collection("users").document(auth.getCurrentUser().getEmail())
                    .collection("plan").add(plan)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            CollectionReference collectionRef = db.collection("users").document(auth.getCurrentUser().getEmail())
                                    .collection("plan").document(documentReference.getId()).collection("maps");
                            for(MapModel doc : docu) {
                                DocumentReference docRef = collectionRef.document(); // 자동 생성된 문서 ID
                                batch.set(docRef, doc);
                            }
                            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "내 일정에 추가되었습니다!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Exception e = task.getException();
                                        if (e != null) {
                                            System.out.println("문서 추가 중 오류 발생: " + e.getMessage());
                                        }
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("mytag", "Error writing document", e);
                        }
                    });
        });

        return view;
    }
}