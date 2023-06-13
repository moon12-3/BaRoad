package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentResultListener;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baroad.Apdater.MyPlanAdapter;
import com.example.baroad.Apdater.MymapAdapter;
import com.example.baroad.Model.MapModel;
import com.example.baroad.databinding.FragmentMyplanMapBinding;
import com.example.baroad.databinding.MymapLocationBinding;
import com.example.baroad.databinding.FragmentMyplanMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.MatchesPattern;


public class Myplan_map extends Fragment implements OnMapReadyCallback, OnBackPressedListener {

    private RecyclerView recyclerView;
    private MymapAdapter adapter;

    private GoogleMap mMap;
    PolylineOptions options = new PolylineOptions();

    ArrayList<MapModel> maplist;
    private FragmentMyplanMapBinding binding;

    Bitmap newMarker;
    ImageView LocLine;
    private ImageButton searchButton;
    private EditText searchBox;

    @Override
    public void onStart() {
        super.onStart();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String date = bundle.getString("date");
                String local = bundle.getString("local");
                binding.title.setText(date + " " + local);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myplan_map, container, false);
        binding = FragmentMyplanMapBinding.bind(view);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mymap);
        mapFragment.getMapAsync(this);

        searchButton = view.findViewById(R.id.searchbtn);
        searchBox = view.findViewById(R.id.searchbox);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // 검색창에서 텍스트를 가져온다
                String searchText = searchBox.getText().toString();

                Geocoder geocoder = new Geocoder(getActivity());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocationName(searchText, 3);
                    if (addresses != null && !addresses.equals(" ")) {
                        search(addresses);
                    }
                } catch(Exception e) {

                }
            }
        });

        //마커 이미지
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.position_icon);
        Bitmap b=bitmapdraw.getBitmap();
        newMarker = Bitmap.createScaledBitmap(b, 40, 40, false);


        LocLine = this.getActivity().findViewById(R.id.loc_line);

        maplist = new ArrayList<>();
        maplist.add( new MapModel("나고야 역", "1 Chome-1-4 Meieki, Nakamura Ward, Nagoya, Aichi", new LatLng(35.171186585151005, 136.88150238057705)));
        maplist.add( new MapModel("나고야 성", "1-1 Honmaru, Naka Ward, Nagoya, Aichi 460-0031", new LatLng(35.18516093401206, 136.89966616399312)));
        maplist.add( new MapModel("나고야시 과학관, 미술관", "일본 〒460-0008 Aichi, Nagoya, Naka Ward, Sakae, 2 Chome−17−1 芸術と科学の杜・白川公園内", new LatLng(35.16690110207503, 136.8996596839704)));
        maplist.add( new MapModel("오스 상점가", "Osu, Naka Ward, Nagoya, Aichi 460-0011", new LatLng(35.159220557056415, 136.90344139325344)));
        maplist.add( new MapModel("사카에 지역", "3 Chome-5-12先 Sakae, Naka Ward, Nagoya, Aichi 460-0008", new LatLng(35.170141721069506, 136.90823520978492)));
        maplist.add( new MapModel("이자카야 Gomitori Honten", "3 Chome-9-13 Sakae, Naka Ward, Nagoya, Aichi 460-0008", new LatLng(35.16744887508762, 136.90458910859599)));

        recyclerView = binding.locRecy;
        adapter = new MymapAdapter(maplist, getActivity());
        adapter = new MymapAdapter(maplist, getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        if(maplist.isEmpty()){
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng P = new LatLng(35.17731148171244, 136.90706992119397);
            markerOptions.position(P);
            markerOptions.title("나고야시");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P, 18));
        }

        else{

            //Geocoder geocoder = new Geocoder(getActivity().getBaseContext());
            //List<Address> addresses = null;

            for (MapModel map : maplist) {// 검색창에서 텍스트를 가져온다

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(map.location);
                markerOptions.title(map.name);
                markerOptions.snippet(map.detail);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(newMarker));
                mMap.addMarker(markerOptions);
                addPath(map.location);
                drawPath();
/*
                try {
                    addresses = geocoder.getFromLocationName(map.name, 3);
                    if (addresses != null && !addresses.equals(" ")) {
                        search(addresses, map.name);
                    }else
                        Log.d("TEST", map.name);
                } catch(Exception e) {
                    Log.d("TEST", map.name);
                }
 */
            }
        }

    }

    // 구글맵 주소 검색 메서드
    protected void search(List<Address> addresses) {
        Address address = addresses.get(0);
        String t = address.getFeatureName(); //장소 이름
        String t2 = address.getAddressLine(0); //상세주소
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude()); // 경도, 위도
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
}