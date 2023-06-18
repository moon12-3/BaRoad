package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baroad.season.Select_season1;
import com.example.baroad.season.Select_season2;
import com.example.baroad.season.Select_season3;
import com.example.baroad.season.Select_season4;

public class SeasonAdapter extends FragmentStateAdapter {
    public int mCount;

    public SeasonAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new Select_season1();
        else if(index==1) return new Select_season2();
        else if(index==2) return new Select_season3();
        else return new Select_season4();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }

}
