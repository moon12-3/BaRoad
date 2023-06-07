package com.example.baroad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.databinding.MainListviewItemBinding;

import java.util.List;

public class MyPlanAdapter extends RecyclerView.Adapter<MyPlanAdapter.ViewHolder> {
    private List<PlanModel> dataList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MainListviewItemBinding binding;

        public ViewHolder(MainListviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PlanModel schedule) {

        }
    }

    public MyPlanAdapter(List<PlanModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ViewHolder(MainListviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.main_listview_item;
    }
}