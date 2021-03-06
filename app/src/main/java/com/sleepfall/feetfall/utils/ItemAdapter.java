package com.sleepfall.feetfall.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sleepfall.feetfall.ItemDetailsActivity;
import com.sleepfall.feetfall.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> mItems;
    private Context context;

    public ItemAdapter(List<Item> items, Context context) {
        mItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItems.get(position);

        Glide.with(context).load(item.getImageId()).into(holder.ivItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItem = itemView.findViewById(R.id.ivItem);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Item temp = mItems.get(pos);
                        Intent i = new Intent(context, ItemDetailsActivity.class);

                        i.putExtra("name", temp.getActualName());
                        i.putExtra("image", temp.getImageId());
                        i.putExtra("description", temp.getDescription());
                        if(temp instanceof Weapon) {
                            i.putExtra("atk", ((Weapon) temp).getStr());
                            i.putExtra("def", ((Weapon) temp).getDef());
                        }

                        context.startActivity(i);
                    }

                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION) {
                mItems.get(pos).use(context);
            }
        }
    }
}
