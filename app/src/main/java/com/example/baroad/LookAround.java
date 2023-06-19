package com.example.baroad;

import android.graphics.drawable.Drawable;
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
            PostModel postModel = new PostModel(date, "가족들을 위한 체험여행 코스", "#봄 #여름 #가을 #겨울", 3);

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
            PostModel postModel = new PostModel(date, "나고야항 지역 코스 1", "#봄 #여름", 4);

            setDB(postModel);
        });

        Button aroud_post5 = view.findViewById(R.id.aroud_post5);
        aroud_post5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replacePost(5);
            }
        });

        binding.post5Heart.setOnClickListener(v-> {
            long date = System.currentTimeMillis();
            PostModel postModel = new PostModel(date, "나고야항 지역 코스 2", "#봄 #여름", 5);

            setDB(postModel);
        });

        Button aroud_post6 = view.findViewById(R.id.aroud_post6);
        aroud_post6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replacePost(6);
            }
        });

        binding.post6Heart.setOnClickListener(v-> {
            long date = System.currentTimeMillis();
            PostModel postModel = new PostModel(date, "나고야 맛집 100% 즐기기", "#봄 #여름 #가을 #겨울", 6);

            setDB(postModel);
        });

        Button spring = view.findViewById(R.id.spring);
        Button summer = view.findViewById(R.id.summer);
        Button fall = view.findViewById(R.id.fall);
        Button winter = view.findViewById(R.id.winter);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            Button lastClickedButton = null;
            @Override
            public void onClick(View view) {
                // 현재 클릭된 버튼과 이전에 클릭된 버튼이 같은 경우, 클릭 상태를 해제하고 원래 상태로 변경
                if (lastClickedButton != null && lastClickedButton == view) {
                    switch (lastClickedButton.getId()) {
                        case R.id.spring:
                            lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_spring));
                            break;
                        case R.id.summer:
                            lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_summer));
                            break;
                        case R.id.fall:
                            lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_fall));
                            break;
                        case R.id.winter:
                            lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_winter));
                            break;
                    }
                    lastClickedButton = null;
                } else {
                    // 마지막으로 클릭된 버튼의 상태를 원래 상태로 되돌리기
                    if (lastClickedButton != null) {
                        switch (lastClickedButton.getId()) {
                            case R.id.spring:
                                lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_spring));
                                break;
                            case R.id.summer:
                                lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_summer));
                                break;
                            case R.id.fall:
                                lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_fall));
                                break;
                            case R.id.winter:
                                lastClickedButton.setBackground(getResources().getDrawable(R.drawable.aroud_winter));
                                break;
                        }
                    }

                    // 클릭된 버튼을 누른 상태로 변경
                    Button clickedButton = (Button) view;
                    switch (clickedButton.getId()) {
                        case R.id.spring:
                            clickedButton.setBackground(getResources().getDrawable(R.drawable.click_spring));
                            break;
                        case R.id.summer:
                            clickedButton.setBackground(getResources().getDrawable(R.drawable.click_summer));
                            break;
                        case R.id.fall:
                            clickedButton.setBackground(getResources().getDrawable(R.drawable.click_fall));
                            break;
                        case R.id.winter:
                            clickedButton.setBackground(getResources().getDrawable(R.drawable.click_winter));
                            break;
                    }


                    // 마지막으로 클릭된 버튼 업데이트
                    lastClickedButton = clickedButton;
                }
            }
        };

        spring.setOnClickListener(buttonClickListener);
        summer.setOnClickListener(buttonClickListener);
        fall.setOnClickListener(buttonClickListener);
        winter.setOnClickListener(buttonClickListener);

        return view;
    }

    public void reload() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void setDB(PostModel post) {
        long now = System.currentTimeMillis();
        db.collection("users").document(auth.getCurrentUser().getEmail())
                .collection("lovepost").document(post.pId).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        Toast.makeText(getActivity(), "좋아요", Toast.LENGTH_SHORT).show();
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
        Query docRef = db.collection("users").document(auth.getCurrentUser().getEmail())
                .collection("lovepost").orderBy("date", Query.Direction.DESCENDING);
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
                        binding.fullHeart5.setVisibility(View.GONE);
                        binding.fullHeart6.setVisibility(View.GONE);
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            PostModel post = document.toObject(PostModel.class);
                            postList.add(post);
                            switch (post.postIdx) {
                                case 1 : binding.fullHeart1.setVisibility(View.VISIBLE); break;
                                case 2 : binding.fullHeart2.setVisibility(View.VISIBLE); break;
                                case 3 : binding.fullHeart3.setVisibility(View.VISIBLE); break;
                                case 4 : binding.fullHeart4.setVisibility(View.VISIBLE); break;
                                case 5 : binding.fullHeart5.setVisibility(View.VISIBLE); break;
                                case 6 : binding.fullHeart6.setVisibility(View.VISIBLE); break;
                            }
                            Log.d("mytag", document.getId() + " => " + document.getData());
                        }

                        adapter = new MyLovePostAdapter(
                                postList,
                                getActivity(),
                                getFragmentManager()
                        );

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);

                    }
                });
    }
}
