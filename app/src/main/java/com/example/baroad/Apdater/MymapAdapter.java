package com.example.baroad.Apdater;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.MainActivity;
import com.example.baroad.Model.MapModel;
import com.example.baroad.R;
import com.example.baroad.databinding.MymapLocationBinding;

import java.util.Collections;
import java.util.List;

public class MymapAdapter extends RecyclerView.Adapter<MymapAdapter.ViewHolder> implements ItemTouchHelperListener{
    private List<MapModel> maplist;

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        MapModel mapModel = maplist.get(from_position);
        //이동할 객체 삭제
        maplist.remove(from_position);
        //이동하고 싶은 position에 추가
        maplist.add(to_position,mapModel);

        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);
        notifyItemChanged(from_position,maplist.get(to_position));
        notifyItemChanged(to_position,maplist.get(from_position));
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        maplist.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MymapLocationBinding binding;

        public ViewHolder(MymapLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MapModel map) {
            binding.locNum.setText(""+(maplist.indexOf(map)+1));

            binding.locName.setText(map.name);
            binding.locDetail.setText(map.detail);

        }
    }

    public MymapAdapter(List<MapModel> maplist, FragmentActivity activity) {
        this.maplist = maplist;
    }

    @Override
    public MymapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new MymapAdapter.ViewHolder(MymapLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // 삭제
    public void removeData(int position) {
        maplist.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(MymapAdapter.ViewHolder holder, int position) {
        holder.bind(maplist.get(position));
    }

    @Override
    public int getItemCount() {
        return maplist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.mymap_location;
    }
}