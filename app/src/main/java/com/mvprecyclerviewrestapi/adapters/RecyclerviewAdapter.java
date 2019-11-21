package com.mvprecyclerviewrestapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mvprecyclerviewrestapi.R;
import com.mvprecyclerviewrestapi.databinding.RowRecylerviewBinding;
import com.mvprecyclerviewrestapi.model.allposts.AllPosts;
import com.mvprecyclerviewrestapi.model.allposts.DataItem;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    public RowRecylerviewBinding binding;
    private AllPosts allPosts;

    public RecyclerviewAdapter(Context mContext, AllPosts allPosts) {
        this.mContext = mContext;
        this.allPosts = allPosts;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.binding.setDatum(users.getData().get(position));
        DataItem rowModel = allPosts.getData().get(position);
//        binding.tvTitle.setText(rowModel.getFirstName());
//        binding.tvBody.setText(rowModel.getLastName());
        Glide.with(mContext)
                .load(rowModel.getAvatar())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(binding.ivAvtar);
        holder.bind(rowModel);
    }

    @Override
    public int getItemCount() {
        return allPosts.getData().size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_recylerview, parent, false);
        return new MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowRecylerviewBinding binding;

        public MyViewHolder(RowRecylerviewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        public void bind(DataItem rowModel) {
            binding.setDatum(rowModel);
        }
    }
}