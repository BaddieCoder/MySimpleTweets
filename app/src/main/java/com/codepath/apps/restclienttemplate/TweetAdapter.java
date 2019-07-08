package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

//The adapter connects the data and our viewholders
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mtweets;
    private  Context context;


    public TweetAdapter(List<Tweet> tweets){
        mtweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //invoked when i need to create a new row
        context = viewGroup.getContext();

        LayoutInflater inflater= LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet,viewGroup, false);
        ViewHolder viewHolder= new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Tweet tweet = mtweets.get(position);
                //the reference of tweets

        viewHolder.tvUserName.setText(tweet.user.name);
        viewHolder.tvBody.setText(tweet.body);
        viewHolder.tvDate.setText(tweet.date);

       Glide.with(context)
               .load(tweet.user.profileImageUrl)
               .apply(RequestOptions.circleCropTransform())
               .into(viewHolder.ivProfileImage);
}

    @Override
    public int getItemCount() {
        return mtweets.size();
        //changed to represent the number of tweets that we actually have
    }

    //passing tweets array
    //for each row inflate layout and pass to vieweholder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvDate;

        public ViewHolder(View itemView){
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUserName = (TextView)itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView)itemView.findViewById(R.id.tvBody);
            tvDate= (TextView)itemView.findViewById(R.id.tv_date);
    }

    }
}


