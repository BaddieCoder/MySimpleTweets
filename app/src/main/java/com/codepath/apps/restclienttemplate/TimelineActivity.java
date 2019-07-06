package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class TimelineActivity extends AppCompatActivity {
    //here is where we populate data
    private Context context;

        private TwitterClient client;
        //reference to the client we're accessing


    //to hookup the adapter we need to declare the references below
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_timeline);
            //the contect is this object
            client = TwitterApp.getRestClient(this);


            //before populating timeline
            //fine recycler view
            rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
            //instantiate data source
            tweets = new ArrayList<>();
            //construct adapter from data source
            tweetAdapter = new TweetAdapter(tweets);

            //recyclervuew setup, layout manager and use adapter
            rvTweets.setLayoutManager(new LinearLayoutManager(this));

            //set the adapter
            rvTweets.setAdapter(tweetAdapter);
            populateTimeline();
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.timeline, menu);
            return true;
        }

  //private final int REQUEST_CODE = 20;
        //we simply called compose action in the xml file
    //for a toast we can just use ''this" for context to test the click , remember .show
    public void onComposeAction(MenuItem menuItem ) {
        Toast.makeText(this,"clicked compose", Toast.LENGTH_LONG).show();



        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);

        startActivityForResult(intent,200);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 200) {
            // Extract name value from result extras

            //unwrap tweet and add it to the top of our timeline

            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet_body"));
            //here we unwrap the tweet by making a new object here and setting it equal to
            //the one that we make in compose
            tweets.add(0, tweet);
            tweetAdapter.notifyItemChanged(0);
            rvTweets.scrollToPosition(0);
        }
        else
            Toast.makeText(this, "Failure to unwrap", Toast.LENGTH_LONG).show();


    }


    private void populateTimeline(){
            client.getHomeTimeline(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //Log.d("TwitterClient", response.toString());
                    //iterate through the array and for each enety convert to a tweet model
                    //add the tweed to the array we've made
                    //notify the adapter we added an irem

                    for (int i = 0; i<response.length(); i++){
                        try {
                           Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                           tweets.add(tweet);
                           tweetAdapter.notifyItemInserted(tweets.size()-1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("TwitterClient", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("TwitterClient", responseString);
                    throwable.printStackTrace();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
            }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }
            });
    }
}
