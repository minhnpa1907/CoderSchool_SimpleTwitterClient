package com.minhnpa.coderschool.coderschool_simpletwitterclient.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.adapter.TweetAdapter;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.model.Tweet;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.TwitterApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.minhnpa.coderschool.coderschool_simpletwitterclient.R.id.edtTweet;

public class MainActivity extends AppCompatActivity {
    public final int REQUEST_COMPOSE = 9001;
    TweetAdapter tweetAdapter;
    Gson mGson;
    String strTweet;
    boolean networkConnected;

    @BindView(R.id.rvTweet)
    RecyclerView rvTweet;

    @BindView(R.id.fabCompose)
    FloatingActionButton fabCompose;

    @BindView(R.id.pbLoadMore)
    ProgressBar pbLoadMore;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private interface Listener {
        void onResult(List<Tweet> tweetList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mGson = new Gson();

        networkConnected = isNetworkAvailable();
        if (networkConnected) {
            setUpViews();
            setUpPullToRefresh();
            swipeContainer.setRefreshing(true);
            load();
        } else {
            Toast.makeText(this, "Can't connect to the network", Toast.LENGTH_LONG).show();
        }
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
                startActivityForResult(intent, REQUEST_COMPOSE);
            }
        });
    }

    private void setUpViews() {
        tweetAdapter = new TweetAdapter(this);
        tweetAdapter.setListener(new TweetAdapter.Listener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        rvTweet.setAdapter(tweetAdapter);
        rvTweet.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpPullToRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkConnected = isNetworkAvailable();
                if (networkConnected) {
                    load();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Can't refresh. Please check your connection!", Toast.LENGTH_LONG).show();
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        // Color
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
    }

    private void load() {
        TwitterApplication.getRestClient().resetPage();
        populateTimeline(new Listener() {
            @Override
            public void onResult(List<Tweet> tweetList) {
                tweetAdapter.setTweets(tweetList);
            }
        });
        rvTweet.scrollToPosition(0);
    }

    private void loadMore() {
        TwitterApplication.getRestClient().nextPage();
        pbLoadMore.setVisibility(View.VISIBLE);
        populateTimeline(new Listener() {
            @Override
            public void onResult(List<Tweet> tweetList) {
                tweetAdapter.addTweets(tweetList);
            }
        });

    }

    private void populateTimeline(final Listener listener) {
        TwitterApplication.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (response.toString() != null) {
                    List<Tweet> tweetList = mGson.fromJson(response.toString(),
                            new TypeToken<List<Tweet>>() {
                            }.getType());
                    listener.onResult(tweetList);
                }
                handleComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Error", errorResponse.toString());
                handleComplete();
            }
        });
    }

    private void postTweet(String tweet) {
        TwitterApplication.getRestClient().postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void handleComplete() {
        pbLoadMore.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_COMPOSE && resultCode == RESULT_OK) {
            strTweet = data.getExtras().getString("tweet");
            postTweet(strTweet);
        }
        load();
    }
}
