package com.minhnpa.coderschool.coderschool_simpletwitterclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.adapter.TweetAdapter;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.model.Tweet;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterApplication;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TweetAdapter tweetAdapter;

    @BindView(R.id.rvTweet)
    RecyclerView rvTweet;

    @BindView(R.id.fabCompose)
    FloatingActionButton fabCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        populateTimeline();
        setUpViews();
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpViews() {
        tweetAdapter = new TweetAdapter(this);
        rvTweet.setAdapter(tweetAdapter);
        rvTweet.setLayoutManager(new LinearLayoutManager(this));
    }

    private void populateTimeline() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                tweetAdapter.setTweets(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Error", errorResponse.toString());
            }
        });
    }
}
