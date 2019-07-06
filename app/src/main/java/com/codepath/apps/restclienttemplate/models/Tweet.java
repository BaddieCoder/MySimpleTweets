package com.codepath.apps.restclienttemplate.models;


import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

//made this class to store specific information for an object from a call
//in this case its a tweet but it could be a news story and we're grabbing the headline
//and an image

@Parcel
public class Tweet {

    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;
    public String date;

   public static Tweet fromJSON (JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        //extract values from json
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user= User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.date = tweet.getRelativeTimeAgo(jsonObject.getString("created_at"));
       return tweet;

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
