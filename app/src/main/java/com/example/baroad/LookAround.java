package com.example.baroad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.baroad.Apdater.MyLovePostAdapter;
import com.example.baroad.Apdater.MyPlanAdapter;
import com.example.baroad.Model.PlanModel;
import com.example.baroad.Model.PostModel;
import com.example.baroad.databinding.FragmentLookAroundBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LookAround extends Fragment {

    private FragmentLookAroundBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private MyLovePostAdapter adapter;

    public LookAround() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_around, container, false);
        binding = FragmentLookAroundBinding.bind(view);
        recyclerView = binding.container;

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        getDB();

        FragmentLookAroundBinding binding = FragmentLookAroundBinding.bind(view);

        Button aroud_post1 = view.findViewById(R.id.aroud_post1);
        aroud_post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost1.newInstance());
            }
        });

        binding.post1Heart.setOnClickListener(v-> {
            setDB();
        });

        Button aroud_post2 = view.findViewById(R.id.aroud_post2);
        aroud_post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost2.newInstance());
            }
        });
        Button aroud_post3 = view.findViewById(R.id.aroud_post3);
        aroud_post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost3.newInstance());
            }
        });
        Button aroud_post4 = view.findViewById(R.id.aroud_post4);
        aroud_post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(TripPost4.newInstance());
            }
        });

        return view;
    }

    private void setDB() {
        long now = System.currentTimeMillis();
        
        PostModel post = new PostModel();

        String coll = "lovepost " + auth.getCurrentUser().getEmail();
        db.collection(coll).add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("mytag", "DocumentSnapshot successfully written!");
                        Toast.makeText(getActivity(), "조하요", Toast.LENGTH_SHORT).show();
                        getDB();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("mytag", "Error writing document", e);
                    }
                });
    }

    private void getDB() {
        recyclerView = binding.container;
        String coll = "lovepost " + auth.getCurrentUser().getEmail();
        Query docRef = db.collection(coll).orderBy("date", Query.Direction.DESCENDING);;

        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<PostModel> postList = new ArrayList<>();

                        postList.clear();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            PostModel post = document.toObject(PostModel.class);
                            postList.add(post);
                            Log.d("mytag", document.getId() + " => " + document.getData());
                        }

                        adapter = new MyLovePostAdapter(postList);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }
                });
    }
}
