package com.example.baroad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baroad.Apdater.MyPlanAdapter;
import com.example.baroad.Model.PlanModel;
import com.example.baroad.databinding.FragmentMyPlanBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyPlan extends Fragment {

    public MyPlan() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MyPlan();
    }

    private FragmentMyPlanBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private MyPlanAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_plan, container, false);
        binding = FragmentMyPlanBinding.bind(view);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // DB 가져오기
        setDB();

        binding.addBtn.setOnClickListener(v ->{

            long now = System.currentTimeMillis();

            Date date = new Date(now);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd");

            String getTime = sdf.format(date);

            PlanModel plan = new PlanModel(getTime, "나고야");

            db.collection("users").document(auth.getCurrentUser().getEmail())
                    .collection("plan").add(plan)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("mytag", "DocumentSnapshot successfully written!");
                            Toast.makeText(getActivity(), "알람을 추가하였습니다.", Toast.LENGTH_SHORT).show();
                            setDB();
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

    private void setDB() {
        recyclerView = binding.container;
        Query docRef = db.collection("users").document(auth.getCurrentUser().getEmail())
                .collection("plan").orderBy("time");
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<PlanModel> planList = new ArrayList<>();
                        List<String> idList = new ArrayList<>();

                        planList.clear();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            PlanModel schedule = document.toObject(PlanModel.class);
                            planList.add(schedule);
                            idList.add(document.getId());
                            Log.d("mytag", document.getId() + " => " + document.getData());
                        }

                        adapter = new MyPlanAdapter(planList, idList, getActivity(), getParentFragmentManager());

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }
                });
    }

}