package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baroad.Select_local1;
import com.example.baroad.Select_local2;
import com.example.baroad.Select_local3;

public class LocalAdapter extends FragmentStateAdapter {
    public int mCount;

    public LocalAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new Select_local1();
        else if(index==1) return new Select_local2();
        else return new Select_local3();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }

}
