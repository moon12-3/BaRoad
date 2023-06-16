package com.example.baroad;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class Select_local extends Fragment {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private CircleIndicator3 mIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //ViewPager2
//        mPager = findViewById(R.id.viewpager);
//        //Adapter
//        pagerAdapter = new MyAdapter(this, num_page);
//        mPager.setAdapter(pagerAdapter);
//        //Indicator
//        mIndicator = findViewById(R.id.indicator);
//        mIndicator.setViewPager(mPager);
//        mIndicator.createIndicators(num_page,0);
//        //ViewPager Setting
//        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//
//        mPager.setCurrentItem(1000); //시작 지점
//        mPager.setOffscreenPageLimit(4); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }
            @Override
            public void onPageScrollStateChanged(int position) {
                super.onPageScrollStateChanged(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });
    }

    public Select_local() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new Select_local();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_select_local, container, false);

        return v;
    }

}