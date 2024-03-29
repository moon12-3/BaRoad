package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentResultListener;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baroad.Apdater.ItemTouchHelperCallback;
import com.example.baroad.Apdater.MyPlanAdapter;
import com.example.baroad.Apdater.MymapAdapter;
import com.example.baroad.Model.MapModel;
import com.example.baroad.Model.PlanModel;
import com.example.baroad.databinding.FragmentMyplanMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
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


public class Myplan_map extends Fragment implements OnMapReadyCallback, OnBackPressedListener {

    private RecyclerView recyclerView;
    ItemTouchHelper helper;
    private MymapAdapter adapter;

    private GoogleMap mMap;
    private PolylineOptions options = new PolylineOptions();

    private ArrayList<MapModel> maplist;
    private FragmentMyplanMapBinding binding;

    private TextView PlusBtn, title;
    private Bitmap newMarker;
    private String id;
    private int cnt;
    private int ins;
    private ImageView LocLine, Back;
    private EditText PlusEdit;
    private Button fixBtn;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private Bundle mybundle;

    private CardView card;
    private ConstraintLayout head;

    private int touch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myplan_map, container, false);
        binding = FragmentMyplanMapBinding.bind(view);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mymap);
        mapFragment.getMapAsync(this);

        title = binding.title;

        Back = view.findViewById(R.id.back_map);
        fixBtn = view.findViewById(R.id.fix_btn);
        PlusBtn = view.findViewById(R.id.plus_loc);
        PlusEdit = view.findViewById(R.id.edit_plus);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame, new MyPlan()).commit();
            }
        });
        fixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fixBtn.getText().toString()){
                    case "수정":
                        binding.addLoc.setVisibility(View.VISIBLE);
                        //ItemTouchHelper 생성
                        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
                        //RecyclerView에 ItemTouchHelper 붙이기
                        helper.attachToRecyclerView(recyclerView);
                        fixBtn.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.mycolor), PorterDuff.Mode.SRC_IN);
                        fixBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                        fixBtn.setText("완료");
                        break;
                    case "완료" :
                        binding.addLoc.setVisibility(View.GONE);
                        helper.attachToRecyclerView(null);
                        fixBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.fix_btn));
                        fixBtn.getBackground().setColorFilter(null);
                        fixBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.mycolor));
                        fixBtn.setText("수정");
                        setDB();
                        break;
                }
            }
        });
        PlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlusEdit.getText() != null){
                    // 검색창에서 텍스트를 가져온다
                    String searchText = PlusEdit.getText().toString();

                    Geocoder geocoder = new Geocoder(getActivity());
                    List<Address> addresses = null;

                    try {
                        addresses = geocoder.getFromLocationName(searchText, 3);
                        if (addresses != null && !addresses.equals(" ")) {
                            search(addresses);
                        }
                    } catch(Exception e) {

                    }
                    PlusEdit.setText(null);
                }
            }
        });

        touch =0;
        card = view.findViewById(R.id.card);
        head = view.findViewById(R.id.c_header);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touch++;
                int margin = 1000;
                int padding =50;
                if(touch%2==1) { margin = -50; padding *= (-1);}
                CardView.LayoutParams layoutParams = (CardView.LayoutParams)card.getLayoutParams();
                layoutParams.topMargin = margin;
                card.setLayoutParams(layoutParams);
                head.setPadding(head.getPaddingLeft(), head.getPaddingTop()+padding, head.getPaddingRight(), head.getPaddingBottom());
            }
        });

        //마커 이미지
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.position_icon_m);
        Bitmap b=bitmapdraw.getBitmap();
        newMarker = Bitmap.createScaledBitmap(b, 65, 65, false);


        LocLine = this.getActivity().findViewById(R.id.loc_line);

//        maplist = new ArrayList<>();
//        maplist.add( new MapModel("나고야 역", "1 Chome-1-4 Meieki, Nakamura Ward, Nagoya, Aichi", new LatLng(35.171186585151005, 136.88150238057705),"유알엘엘엘","폰"));
//        maplist.add( new MapModel("나고야 성", "1-1 Honmaru, Naka Ward, Nagoya, Aichi 460-0031", new LatLng(35.18516093401206, 136.89966616399312),"",""));
//        maplist.add( new MapModel("나고야시 과학관, 미술관", "일본 〒460-0008 Aichi, Nagoya, Naka Ward, Sakae, 2 Chome−17−1 芸術と科学の杜・白川公園内", new LatLng(35.16690110207503, 136.8996596839704),"",""));
//        maplist.add( new MapModel("오스 상점가", "Osu, Naka Ward, Nagoya, Aichi 460-0011", new LatLng(35.159220557056415, 136.90344139325344),"",""));
//        maplist.add( new MapModel("사카에 지역", "3 Chome-5-12先 Sakae, Naka Ward, Nagoya, Aichi 460-0008", new LatLng(35.170141721069506, 136.90823520978492),"",""));
//        maplist.add( new MapModel("이자카야 Gomitori Honten", "3 Chome-9-13 Sakae, Naka Ward, Nagoya, Aichi 460-0008", new LatLng(35.16744887508762, 136.90458910859599),"",""));

        recyclerView = view.findViewById(R.id.loc_recy);
//        adapter = new MymapAdapter(maplist, getParentFragmentManager(), mybundle);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
//        layoutParams.height = maplist.size()*300; // 원하는 높이로 설정
//        recyclerView.setLayoutParams(layoutParams);
//        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mybundle = getArguments();
        String date = mybundle.getString("date");
        String local = mybundle.getString("local");
        id = mybundle.getString("id");
        title.setText(date + " " + local);
        maplist = new ArrayList<>();
        getDB();

        mMap = googleMap;
        if(maplist.isEmpty()){
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng P = new LatLng(35.17731148171244, 136.90706992119397);
            markerOptions.position(P);
            markerOptions.title("나고야시");
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(newMarker));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P, 18));
        }else{
            for (MapModel map : maplist) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(map.latitude, map.longitude));
                markerOptions.title(map.name);
                markerOptions.snippet(map.detail);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(newMarker));
                mMap.addMarker(markerOptions);
                addPath(new LatLng(map.latitude, map.longitude));
                drawPath();
            }
            int getindex = maplist.size()/2;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(maplist.get(getindex).latitude, maplist.get(getindex).longitude), 13));
        }

    }

    // 구글맵 주소 검색 메서드
    protected void search(List<Address> addresses) {
        Address address = addresses.get(0);
        String t = address.getFeatureName(); //장소 이름
        String t2 = address.getAddressLine(0); //상세주소
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude()); // 경도, 위도
        // MapModel m = new MapModel(t, t2, latLng, address.getUrl(), address.getPhone(), System.currentTimeMillis());
        String url = address.getUrl()==null ? "https://railway.jr-central.co.jp/station-guide/shinkansen/nagoya/index.html" : address.getUrl();
        String phone = address.getPhone()==null ? "+81 50-3772-3910" : address.getPhone();
        MapModel m = new MapModel(t, t2, address.getLatitude(), address.getLongitude(), url, phone, System.currentTimeMillis());
        maplist.add(m);
        Location a = new Location("a");
        a.setLatitude(latLng.latitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(newMarker));
        markerOptions.position(latLng);
        markerOptions.title(t);
        markerOptions.snippet(t2);

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        addPath(latLng);
        drawPath();

        adapter = new MymapAdapter(maplist, getParentFragmentManager(),mybundle);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        layoutParams.height = maplist.size()*300; // 원하는 높이로 설정
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setAdapter(adapter);
    }
    private void addPath(LatLng newlat){        //polyline을 그려주는 메소드
        options.add(newlat);

    }
    private void drawPath(){        //polyline을 그려주는 메소드
        Resources resources = getResources();
        options.width(5).color(resources.getColor(R.color.mycolor)).geodesic(true);
        mMap.addPolyline(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(options.getPoints().get(0), 13));
    }

    @Override
    public void onBackPressed() {
        ((MainActivity)getActivity()).changeFragment(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setOnBackPressedListener(this);
    }

    public void setDB() {
        ins = 0;
        for(int i = cnt; i < maplist.size(); i++) {
            db.collection("users").document(auth.getCurrentUser().getEmail())
                    .collection("plan").document(id).collection("maps").add(maplist.get(i))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            ins++;
                            if(maplist.size()==ins) getDB();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("mytag", "Error writing document", e);
                        }
                    });
        }
    }

    public void getDB() {
        Query docRef = db.collection("users").document(auth.getCurrentUser().getEmail())
                .collection("plan").document(id)
                .collection("maps").orderBy("time");
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        //maplist.clear();
                        cnt = 0;
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            MapModel map = document.toObject(MapModel.class);
                            maplist.add(map);
                            LatLng latLng = new LatLng(map.latitude, map.longitude);
                            Location a = new Location("a");
                            a.setLatitude(latLng.latitude);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(newMarker));
                            markerOptions.position(latLng);
                            markerOptions.title(map.name);
                            markerOptions.snippet(map.detail);

                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                            addPath(latLng);
                            drawPath();

                            cnt++;
                            Log.d("mytag", document.getId() + " => " + document.getData());
                        }

                        adapter = new MymapAdapter(maplist, getParentFragmentManager(), mybundle);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                        layoutParams.height = maplist.size()*300; // 원하는 높이로 설정
                        recyclerView.setLayoutParams(layoutParams);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}