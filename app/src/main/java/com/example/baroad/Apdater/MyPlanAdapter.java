package com.example.baroad.Apdater;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.MainActivity;
import com.example.baroad.Model.PlanModel;
import com.example.baroad.Myplan_map;
import com.example.baroad.R;
import com.example.baroad.databinding.MainListviewItemBinding;

import java.util.List;

public class MyPlanAdapter extends RecyclerView.Adapter<MyPlanAdapter.ViewHolder> {
    private List<PlanModel> dataList;
    private List<String> idList;
    private FragmentActivity activity;
    private FragmentManager fgManager;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MainListviewItemBinding binding;

        public ViewHolder(MainListviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PlanModel schedule) {
            String s = schedule.date+"\n"+schedule.local+" 여행";
            binding.detailText.setText(s);

            binding.goDetail.setOnClickListener(v-> {
                Bundle result = new Bundle();
                result.putString("date", schedule.date);
                result.putString("local", schedule.local);
                result.putString("id", idList.get(getAdapterPosition()));
//                fgManager.setFragmentResult("requestKey", result);
//                ((MainActivity)activity).changeFragment(2);
                Myplan_map map = new Myplan_map();
                map.setArguments(result);
                fgManager.beginTransaction().replace(R.id.frame, map).commit();
            });
        }
    }

    public MyPlanAdapter(List<PlanModel> dataList, List<String> idList, FragmentActivity activity, FragmentManager fgManager) {
        this.dataList = dataList;
        this.fgManager = fgManager;
        this.activity = activity;
        this.idList = idList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ViewHolder(MainListviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // 삭제
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