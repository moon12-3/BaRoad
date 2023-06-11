package com.example.baroad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.baroad.Apdater.MyPlanAdapter;
import com.example.baroad.Apdater.MymapAdapter;
import com.example.baroad.Model.MapModel;
import com.example.baroad.databinding.FragmentMyplanMapBinding;
import com.example.baroad.databinding.MymapLocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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


public class Myplan_map extends Fragment implements OnMapReadyCallback {

    private RecyclerView recyclerView;
    private MymapAdapter adapter;

    private GoogleMap mMap;
    private EditText searchBox;

    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    HashMap<String, LatLng> mMarkerP;
    ArrayList<String> mMarkerPoints;
    ArrayList<ArrayList> myBookMark;
    PolylineOptions options = new PolylineOptions();
    List<Polyline> polylines;

    ArrayList<String> testTitles;
    ImageView PlanBtn, BkmBtn, plusplan;
    TextView planText, planBtnt, bkmBtnt;

    ArrayList<MapModel> maplist;
    FragmentMyplanMapBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myplan_map, container, false);
        binding = FragmentMyplanMapBinding.bind(view);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mymap);
        mapFragment.getMapAsync(this);

        maplist = new ArrayList<>();
        maplist.add( new MapModel("나고야 역", "1 Chome-1-4 Meieki, Nakamura Ward, Nagoya, Aichi", new LatLng(35.171186585151005, 136.88150238057705)));
        maplist.add( new MapModel("나고야 성", "1-1 Honmaru, Naka Ward, Nagoya, Aichi 460-0031", new LatLng(35.18516093401206, 136.89966616399312)));
        maplist.add( new MapModel("나고야시 과학관, 미술관", "일본 〒460-0008 Aichi, Nagoya, Naka Ward, Sakae, 2 Chome−17−1 芸術と科学の杜・白川公園内", new LatLng(35.16690110207503, 136.8996596839704)));
        maplist.add( new MapModel("오스 상점가", "Osu, Naka Ward, Nagoya, Aichi 460-0011", new LatLng(35.159220557056415, 136.90344139325344)));
        maplist.add( new MapModel("사카에 지역", "3 Chome-5-12先 Sakae, Naka Ward, Nagoya, Aichi 460-0008", new LatLng(35.170141721069506, 136.90823520978492)));
        maplist.add( new MapModel("이자카야 고미토리 본점", "3 Chome-9-13 Sakae, Naka Ward, Nagoya, Aichi 460-0008", new LatLng(35.16744887508762, 136.90458910859599)));

        recyclerView = binding.locRecy;
        adapter = new MymapAdapter(maplist, getActivity());
        adapter = new MymapAdapter(maplist, getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        for (MapModel map : maplist) {// 검색창에서 텍스트를 가져온다

            addPath(map.location);
            //drawPath();

        }
        return view;
    }

    // 구글맵 주소 검색 메서드
    protected void search(List<Address> addresses, String t) {
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        LatLng P = new LatLng(35.17731148171244, 136.90706992119397);
        String addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : " ", address.getFeatureName());
        Location a = new Location("a");
        Location b = new Location("b");
        a.setLatitude(latLng.latitude);
        b.setLatitude(P.latitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(t);
        // float res = a.distanceTo(b);
        //markerOptions.snippet(res+"");

        mMap.addMarker(markerOptions);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

        addPath(latLng);
        drawPath();

    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng P = new LatLng(35.17731148171244, 136.90706992119397);
        markerOptions.position(P);
        //markerOptions.title("나고야시");
        //markerOptions.snippet("나고야시");
        // mMap.addMarker(markerOptions);

        // 기존에 사용하던 다음 2줄은 문제가 있습니다.
        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(P, 18));

    }
    private void addPath(LatLng newlat){        //polyline을 그려주는 메소드
        options.add(newlat);

    }
    private void drawPath(){        //polyline을 그려주는 메소드
        options.width(5).color(R.color.mycolor).geodesic(true);
        polylines.add(mMap.addPolyline(options));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(options.getPoints().get(1), 13));
    }
}