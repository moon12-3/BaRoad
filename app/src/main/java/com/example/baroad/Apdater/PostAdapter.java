package com.example.baroad.Apdater;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.MainActivity;
import com.example.baroad.Model.PostModel;
import com.example.baroad.R;
import com.example.baroad.databinding.AroudLikePostBinding;
import com.example.baroad.databinding.AroundPostBinding;
import com.example.baroad.databinding.MainListviewItemBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<PostModel> dataList;
    private Activity mainActivity;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AroundPostBinding binding;

        public ViewHolder(AroundPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
``
        public void bind(PostModel post) {
//            binding.season.setText(post.season);
//            binding.title.setText(post.pName);
            int img =  R.drawable.aroud_post1;
            switch (post.postIdx) {
                case 2 : img =  R.drawable.aroud_post2; break;
                case 3 : img =  R.drawable.aroud_post3; break;
                case 4 : img =  R.drawable.aroud_post4; break;
                case 5 : img =  R.drawable.aroud_post5; break;
                case 6 : img =  R.drawable.aroud_post6; break;
            }

            binding.lovePost.setBackground(ContextCompat.getDrawable(mainActivity.getApplicationContext(), img));

            binding.lovePost.setOnClickListener(v-> {
                ((MainActivity)mainActivity).replacePost(post.postIdx);
            });

            binding.heart.setOnClickListener(v-> {
                delete(post.pId);
                removeData(getAdapterPosition());
            });
        }
    }

    public PostAdapter(List<PostModel> dataList, Activity activity) {
        this.dataList = dataList;
        mainActivity = activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ViewHolder(AroundPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // 삭제
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void delete(String pId) {
        db.collection("users").document(auth.getCurrentUser().getEmail())
                .collection("lovepost").document(pId).delete();
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
        return R.layout.around_post;
    }
}
