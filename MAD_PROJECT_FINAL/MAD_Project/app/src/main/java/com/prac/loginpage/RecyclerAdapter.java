package com.prac.loginpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private int[] images;
    private String[] image_text;
    private ImageViewHolder.OnListItemClickListener mClickListener;
    private Context context;


    public RecyclerAdapter(int[] pics, String[] text, ImageViewHolder.OnListItemClickListener clickListener, Context anyContext  )
    {
        images = pics;
        image_text = text;
        context = anyContext;
        mClickListener = clickListener;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view, context, images,image_text, mClickListener);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        int img_pos = images[position];
        holder.items_pics.setImageResource(img_pos);
        holder.items_text.setText(image_text[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView items_pics;
        TextView items_text;
        Context context;
        int[] images;
        String[] image_text;
        OnListItemClickListener listItemClickListener;

        public  ImageViewHolder(@NonNull View itemView, Context anyContext, int[] pics, String[] text, OnListItemClickListener clickListener) {
            super(itemView);
            items_pics = itemView.findViewById(R.id.items_pics);
            items_text = itemView.findViewById(R.id.items_text);
            listItemClickListener = clickListener;
            itemView.setOnClickListener(this);
            context = anyContext;
            images = pics;
            image_text = text;
        }

        @Override
        public void onClick(View view)
        {
            listItemClickListener.OnListItemClick(getAdapterPosition());

        }

        public interface OnListItemClickListener {
            void OnListItemClick(int position);
        }
    }



}
