package com.example.baroad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class Myplan_map extends AppCompatActivity
        implements OnMapReadyCallback {

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
    LinearLayout planPlace;
    ImageView PlanBtn, BkmBtn, plusplan;
    TextView planText, planBtnt, bkmBtnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_myplan_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMarkerPoints = new ArrayList<>();
/*
        myBookMark = new ArrayList<>();
        testTitles = new ArrayList<>();
        testTitles.add("나고야 역");
        testTitles.add("히가시야마 동물원");
        testTitles.add("다이노 어드벤쳐 나고야");
        testTitles.add("나고야시 과학관");
        myBookMark.add(testTitles);
        testTitles = new ArrayList<>();
        testTitles.add("나고야 역");
        testTitles.add("노리타케의 숲");
        testTitles.add("나고야 성");
        testTitles.add("킨샤치 요코쵸");
        testTitles.add("사카에 지역");
        myBookMark.add(testTitles);
        testTitles = new ArrayList<>();
        testTitles.add("나고야 역");
        testTitles.add("나고야 성");
        testTitles.add("나고야 시 과학관, 미술관");
        testTitles.add("오스 상점가");
        testTitles.add("사카에 지역");
        myBookMark.add(testTitles);
        testTitles = new ArrayList<>();
        testTitles.add("나고야 역");
        testTitles.add("크루즈 나고야");
        testTitles.add("레고랜드 재팬");
        testTitles.add("긴조후토");
        myBookMark.add(testTitles);
        testTitles = new ArrayList<>();
        testTitles.add("나고야 역");
        testTitles.add("크루즈 나고야");
        testTitles.add("나고야항 수족관");
        testTitles.add("리니어, 철도관");
        myBookMark.add(testTitles);*/
        //내일정, 북마크 탭
        /*planPlace = findViewById(R.id.planPlace);
        PlanBtn = findViewById(R.id.planbtn);
        planBtnt = findViewById(R.id.planbtnt);
        plusplan = findViewById(R.id.plusplan);
        BkmBtn = findViewById(R.id.bmkbtn);
        bkmBtnt = findViewById(R.id.bmkbtnt);
        planText = findViewById(R.id.plantext);*/

        testTitles = new ArrayList<>();
        testTitles.add("나고야 역");
        testTitles.add("나고야 성");
        testTitles.add("나고야시 과학관, 미술관");
        testTitles.add("오스 상점가");
        testTitles.add("사카에 지역");
        testTitles.add("이자카야 고미토리 본점");
        myBookMark.add(testTitles);

        for(String a : testTitles){// 검색창에서 텍스트를 가져온다
            String searchText = a;

            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocationName(searchText, 3);
                if (addresses != null && !addresses.equals(" ")) {
                    mMarkerPoints.add(searchText);
                    search(addresses, searchText);
                }
            } catch(Exception e) {

            }
        }

        PlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                PlanBtn.setBackgroundColor(getResources().getColor(R.color.white));
                planBtnt.setTextColor(getResources().getColor(R.color.mycolor));
                BkmBtn.setBackgroundColor(getResources().getColor(R.color.mycolor));
                bkmBtnt.setTextColor(getResources().getColor(R.color.white));
                */

                plusplan.setImageResource(R.drawable.plusbtn);
                planPlace.removeAllViews();
                planText.setText("내 일정");
                for (String plan:mMarkerPoints) {
                    TextView t = new TextView(Myplan_map.this);
                    t.setText(plan);
                    t.setBackground(getDrawable(R.drawable.planbox));
                    t.setPadding(60,40,0,0);
                    t.setTextSize(16);
                    t.setTextColor(getResources().getColor(R.color.black));
                    planPlace.addView(t);
                }
            }
        });

        BkmBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                BkmBtn.setBackgroundColor(getResources().getColor(R.color.white));
                bkmBtnt.setTextColor(getResources().getColor(R.color.mycolor));
                PlanBtn.setBackgroundColor(getResources().getColor(R.color.mycolor));
                planBtnt.setTextColor(getResources().getColor(R.color.white));
                planPlace.removeAllViews();
                plusplan.setImageResource(0);
                planText.setText("내 북마크");
                LinearLayout l = new LinearLayout(Myplan_map.this);
                LinearLayout l2 = new LinearLayout(Myplan_map.this);
                ImageView plan1 = new ImageView(Myplan_map.this);
                ImageView plan2 = new ImageView(Myplan_map.this);
                ImageView plan3 = new ImageView(Myplan_map.this);
                ImageView plan4 = new ImageView(Myplan_map.this);
                ImageView plan5 = new ImageView(Myplan_map.this);
                plan1.setImageResource(R.drawable.plan1);
                plan2.setImageResource(R.drawable.plan2);
                plan3.setImageResource(R.drawable.plan3);
                plan4.setImageResource(R.drawable.plan4);
                plan5.setImageResource(R.drawable.plan5);
                plan1.setPadding(5,5,5,5);
                plan2.setPadding(5,5,5,5);
                plan3.setPadding(5,5,5,5);
                plan4.setPadding(5,5,5,5);
                plan5.setPadding(5,5,5,5);
                plan1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        options = new PolylineOptions();
                        mMap.clear();
                        ArrayList<String> a = myBookMark.get(0);
                        for (String i : a) {
                            String searchText = i;

                            Geocoder geocoder = new Geocoder(getBaseContext());
                            List<Address> addresses = null;

                            try {
                                addresses = geocoder.getFromLocationName(searchText, 3);
                                if (addresses != null && !addresses.equals(" ")) {
                                    PlanBtn.callOnClick();
                                    mMarkerPoints.add(i);
                                    search(addresses, i);
                                }
                            } catch(Exception e) {

                            }
                        }
                    }
                });
                plan2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        options = new PolylineOptions();
                        mMap.clear();
                        mMarkerPoints.clear();
                        ArrayList<String> a = myBookMark.get(1);
                        for (String i : a) {
                            String searchText = i;

                            Geocoder geocoder = new Geocoder(getBaseContext());
                            List<Address> addresses = null;

                            try {
                                addresses = geocoder.getFromLocationName(searchText, 3);
                                if (addresses != null && !addresses.equals(" ")) {
                                    PlanBtn.callOnClick();
                                    mMarkerPoints.add(i);
                                    search(addresses, i);
                                }
                            } catch(Exception e) {

                            }
                        }
                    }
                });
                plan3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        options = new PolylineOptions();
                        mMap.clear();
                        mMarkerPoints.clear();
                        ArrayList<String> a = myBookMark.get(2);
                        for (String i : a) {
                            String searchText = i;

                            Geocoder geocoder = new Geocoder(getBaseContext());
                            List<Address> addresses = null;

                            try {
                                addresses = geocoder.getFromLocationName(searchText, 3);
                                if (addresses != null && !addresses.equals(" ")) {
                                    PlanBtn.callOnClick();
                                    mMarkerPoints.add(i);
                                    search(addresses, i);
                                }
                            } catch(Exception e) {

                            }
                        }
                    }
                });
                plan4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        options = new PolylineOptions();
                        mMap.clear();
                        mMarkerPoints.clear();
                        ArrayList<String> a = myBookMark.get(3);
                        for (String i : a) {
                            String searchText = i;

                            Geocoder geocoder = new Geocoder(getBaseContext());
                            List<Address> addresses = null;

                            try {
                                addresses = geocoder.getFromLocationName(searchText, 3);
                                if (addresses != null && !addresses.equals(" ")) {
                                    PlanBtn.callOnClick();
                                    mMarkerPoints.add(i);
                                    search(addresses, i);
                                }
                            } catch(Exception e) {

                            }
                        }
                    }
                });
                plan5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        options = new PolylineOptions();
                        mMap.clear();
                        mMarkerPoints.clear();
                        ArrayList<String> a = myBookMark.get(4);
                        for (String i : a) {
                            String searchText = i;

                            Geocoder geocoder = new Geocoder(getBaseContext());
                            List<Address> addresses = null;

                            try {
                                addresses = geocoder.getFromLocationName(searchText, 3);
                                if (addresses != null && !addresses.equals(" ")) {
                                    PlanBtn.callOnClick();
                                    mMarkerPoints.add(i);
                                    search(addresses, i);
                                }
                            } catch(Exception e) {

                            }
                        }
                    }
                });
                l.setOrientation(LinearLayout.HORIZONTAL);
                l2.setOrientation(LinearLayout.HORIZONTAL);
                l.addView(plan1);
                l.addView(plan2);
                l2.addView(plan3);
                l2.addView(plan4);
                l2.addView(plan5);
                planPlace.addView(l);
                planPlace.addView(l2);
            }
        });

        // 구글맵에서 검색창 editText 와 검색된 위치 불러올 textView 초기화
        searchBox = findViewById(R.id.searchbox);
        //locationText = findViewById(R.id.shop_text_location);

        // 구글맵 검색 하는 부분
        ImageButton searchButton = findViewById(R.id.searchbtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 검색창에서 텍스트를 가져온다
                String searchText = searchBox.getText().toString();

                Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocationName(searchText, 3);
                    if (addresses != null && !addresses.equals(" ")) {
                        mMarkerPoints.add(searchText);
                        search(addresses, searchText);
                    }
                } catch(Exception e) {

                }

            }
        });
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