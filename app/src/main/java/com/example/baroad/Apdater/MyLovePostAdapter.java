package com.example.baroad.Apdater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baroad.LookAround;
import com.example.baroad.MainActivity;
import com.example.baroad.Model.PostModel;
import com.example.baroad.R;
import com.example.baroad.databinding.AroudLikePostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
public class MyLovePostAdapter extends RecyclerView.Adapter<MyLovePostAdapter.ViewHolder> {
    private List<PostModel> dataList;
    private Activity mainActivity;
    private FragmentManager frag;

    private FirebaseFirestore  db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

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

            binding.heart.setOnClickListener(v-> {
                delete(post.pId);
                removeData(getAdapterPosition());
            });
        }
    }

    public MyLovePostAdapter(List<PostModel> dataList, Activity activity, FragmentManager frag) {
        this.dataList = dataList;
        mainActivity = activity;
        this.frag = frag;
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
        return R.layout.aroud_like_post;
    }
}