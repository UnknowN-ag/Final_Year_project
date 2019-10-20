package com.abhishek.buynsell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.recyclerViewHolder> {
    String postImageStr,profilePicStr = "";

    Context context;
    private List<Post> post;
    private ImageView postImage, profileImg;

    public recyclerViewAdapter(Context context,List<Post> post){
        this.context = context;
        this.post = post;
    }


    @Override
    public recyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout_recyclerview, parent, false);
        return new recyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(recyclerViewHolder holder, int i) {
        holder.recyclerTxt_heading.setText(post.get(i).getNameOfProduct());
        holder.recyclerTxt_paymentType.setText(post.get(i).getPaymentType());
        if(post.get(i).getPaymentType().equals("Free")){
            holder.recyclerTxt_price.setVisibility(View.INVISIBLE);
        }
        holder.recyclerTxt_price.setText(post.get(i).getPrice());
        postImageStr = post.get(i).getPostImage();
        profilePicStr = post.get(i).getProfilePic();
        final String productId = post.get(i).getProductId();

        holder.post_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Post_description.class);
                intent.putExtra("productId", productId);
                context.startActivity(intent);
            }
        });
        Picasso.get()
                .load(postImageStr)
                .resize(800, 800)
                .centerCrop()
                .into(postImage);
        Picasso.get()
                .load(profilePicStr)
                .resize(200, 200)
                .centerCrop()
                .into(profileImg);
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class recyclerViewHolder extends RecyclerView.ViewHolder{

        TextView recyclerTxt_heading,recyclerTxt_paymentType,recyclerTxt_price;
        CardView post_cardView;
        public recyclerViewHolder(View itemView){
            super(itemView);
            postImage = itemView.findViewById(R.id.recyclerImg);
            recyclerTxt_heading = itemView.findViewById(R.id.recyclerTxt_heading);
            recyclerTxt_paymentType = itemView.findViewById(R.id.recyclerTxt_paymentType);
            recyclerTxt_price = itemView.findViewById(R.id.recyclerTxt_price);
            post_cardView = itemView.findViewById(R.id.post_cardView);
            profileImg = itemView.findViewById((R.id.profileImg));

        }
    }
}
