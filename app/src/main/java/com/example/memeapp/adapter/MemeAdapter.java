package com.example.memeapp.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memeapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.ViewHolder> {
    Context context;
    ArrayList<MemeContactModel> data;
    public MemeAdapter(Context context, ArrayList<MemeContactModel> data){
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public MemeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.div,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemeAdapter.ViewHolder holder, int position) {
        //holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left));
        holder.meme_id.setText("@"+data.get(position).id);
        holder.meme_text_1.setText(data.get(position).title);
        Glide.with(context).load(data.get(position).url).into(holder.meme_img_1);
        holder.meme_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.image_zoom);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imageZoom);
                imageView.setImageDrawable(holder.meme_img_1.getDrawable());
                dialog.show();
            }
        });
        holder.share_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Hey ! Checkout this meme :\n"+data.get(position).url;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT,msg);
                i.setType("text/plain");
                Intent.createChooser(i,"Share using...");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView meme_img_1;
        TextView meme_id,meme_text_1;
        Button share_1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meme_id = (TextView) itemView.findViewById(R.id.meme_id);
            meme_img_1 = (ImageView) itemView.findViewById(R.id.meme_img_1);
            meme_text_1 = (TextView) itemView.findViewById(R.id.meme_text_1);
            share_1 = (Button) itemView.findViewById(R.id.share_1);
        }
    }
}
