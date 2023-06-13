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
import com.google.firebase.firestore.DocumentSnapshot;
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
                ((MainActivity)getActivity()).replacePost(1);
            }
        });

        binding.post1Heart.setOnClickListener(v-> {
            binding.fullHeart1.setVisibility(View.VISIBLE);
            long date = System.currentTimeMillis();
            PostModel postModel = new PostModel(date, "나고야 중심부 위주코스 1", "#봄 #여름 #가을 #겨울", 1);
            setDB(postModel);
        });

        Button aroud_post2 = view.findViewById(R.id.aroud_post2);
        aroud_post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replacePost(2);
            }
        });

        binding.post2Heart.setOnClickListener(v-> {
            long date = System.currentTimeMillis();
            PostModel postModel = new PostModel(date, "나고야 중심부 위주코스 2", "#봄 #여름 #가을 #겨울", 2);

            setDB(postModel);
        });

        Button aroud_post3 = view.findViewById(R.id.aroud_post3);
        aroud_post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replacePost(3);
            }
        });

        binding.post3Heart.setOnClickListener(v-> {
            long date = System.currentTimeMillis();
            PostModel postModel = new PostModel(date, "나고야항 지역 코스 1", "#봄 #여름", 3);

            setDB(postModel);
        });

        Button aroud_post4 = view.findViewById(R.id.aroud_post4);
        aroud_post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replacePost(4);
            }
        });

        binding.post4Heart.setOnClickListener(v-> {
            long date = System.currentTimeMillis();
            PostModel postModel = new PostModel(date, "나고야항 지역 코스 2", "#봄 #여름", 4);

            setDB(postModel);
        });

        return view;
    }

    private void setDB(PostModel post) {
        long now = System.currentTimeMillis();

        String coll = "lovepost " + auth.getCurrentUser().getEmail();
        db.collection(coll).document(post.pId).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "좋아요", Toast.LENGTH_SHORT).show();
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
                        binding.fullHeart1.setVisibility(View.GONE);
                        binding.fullHeart2.setVisibility(View.GONE);
                        binding.fullHeart3.setVisibility(View.GONE);
                        binding.fullHeart4.setVisibility(View.GONE);
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            PostModel post = document.toObject(PostModel.class);
                            postList.add(post);
                            switch (post.postIdx) {
                                case 1 : binding.fullHeart1.setVisibility(View.VISIBLE); break;
                                case 2 : binding.fullHeart2.setVisibility(View.VISIBLE); break;
                                case 3 : binding.fullHeart3.setVisibility(View.VISIBLE); break;
                                case 4 : binding.fullHeart4.setVisibility(View.VISIBLE); break;
                            }
                            Log.d("mytag", document.getId() + " => " + document.getData());
                        }

                        adapter = new MyLovePostAdapter(
                                postList,
                                getActivity()
                        );

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);

                    }
                });
    }

    public void delete(DocumentSnapshot todo) {
        String coll = "lovepost " + auth.getCurrentUser().getEmail();
        db.collection(coll).document(todo.getId()).delete();
    }
}
