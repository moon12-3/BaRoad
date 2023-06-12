package com.example.baroad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.baroad.Apdater.MyLovePostAdapter;
import com.example.baroad.Apdater.PostAdapter;
import com.example.baroad.Model.PostModel;
import com.example.baroad.databinding.FragmentMypageLikeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Mypage_like extends Fragment {

    private FragmentMypageLikeBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    public static Fragment newInstance() {
        return new Mypage_like();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage_like, container, false);

        binding = FragmentMypageLikeBinding.bind(view);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        getDB();

        return view;
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

                        adapter = new PostAdapter(postList);

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);

                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }
                });
    }
}