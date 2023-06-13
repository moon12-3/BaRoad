package com.example.baroad.Apdater;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.MainActivity;
import com.example.baroad.Model.PostModel;
import com.example.baroad.R;
import com.example.baroad.databinding.AroudLikePostBinding;
import com.example.baroad.databinding.MainListviewItemBinding;

import java.util.List;
public class MyLovePostAdapter extends RecyclerView.Adapter<MyLovePostAdapter.ViewHolder> {
    private List<PostModel> dataList;
    private Activity mainActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AroudLikePostBinding binding;

        public ViewHolder(AroudLikePostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostModel post) {
            binding.season.setText(post.season);
            binding.title.setText(post.pName);

            binding.lovePost.setOnClickListener(v-> {
                ((MainActivity)mainActivity).replacePost(post.postIdx);
            });
        }
    }

    public MyLovePostAdapter(List<PostModel> dataList, Activity activity) {
        this.dataList = dataList;
        mainActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ViewHolder(AroudLikePostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        return R.layout.aroud_like_post;
    }
}