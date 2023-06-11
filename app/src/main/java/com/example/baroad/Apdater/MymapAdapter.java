package com.example.baroad.Apdater;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.MainActivity;
import com.example.baroad.Model.MapModel;
import com.example.baroad.R;
import com.example.baroad.databinding.MymapLocationBinding;

import java.util.List;

public class MymapAdapter extends RecyclerView.Adapter<MymapAdapter.ViewHolder> {
    private List<MapModel> maplist;
    private FragmentActivity activity;
    private int cnt=1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MymapLocationBinding binding;

        public ViewHolder(MymapLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MapModel map) {
            binding.locNum.setText(cnt+"");

            binding.locName.setText(map.name);
            binding.locDetail.setText(map.detail);
            cnt++;
        }
    }

    public MymapAdapter(List<MapModel> maplist, FragmentActivity activity) {
        this.maplist = maplist;
        this.activity = activity;
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