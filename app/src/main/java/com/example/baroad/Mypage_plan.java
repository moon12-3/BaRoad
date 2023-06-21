package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baroad.Apdater.MyPlanAdapter;
import com.example.baroad.Model.PlanModel;
import com.example.baroad.databinding.FragmentMypagePlanBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Mypage_plan extends Fragment {

    private FragmentMypagePlanBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private MyPlanAdapter adapter;

    public Mypage_plan() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new Mypage_plan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mypage_plan, container, false);

        binding = FragmentMypagePlanBinding.bind(view);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = binding.container;
        setDB();

        return view;
    }

    private void setDB() {
        recyclerView = binding.container;
        Query docRef = db.collection("users").document(auth.getCurrentUser().getEmail())
                .collection("plan").orderBy("date");

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