package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

//User is a class created to make user objects that store user info that we got from the json object
//our tweets dont just show body , but correspond to a user so for every tweet we need the corresponding
//user data to display - technically this data could be combined with tweet but having the seperation
//allows us to display user data seperately without necessarilly displaying a tweet body.

@Parcel
public class User {

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    public static User fromJSON(JSONObject json)throws JSONException {
        User user = new User();

        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url_https");
        return user;
    }

}
