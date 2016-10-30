package com.minhnpa.coderschool.coderschool_simpletwitterclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.R;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.model.Tweet;
import com.minhnpa.coderschool.coderschool_simpletwitterclient.stuff.RoundedCornersTransformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    private Context mContext;
    private Listener mListener;

    public interface Listener {
        void onLoadMore();
    }

    public TweetAdapter(Context context) {
        mTweets = new ArrayList<>();
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(TweetAdapter.ViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);

        if (null != tweet) {
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
            viewHolder.tvText.setText(tweet.getText());
            viewHolder.tvTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
            Glide.with(getContext())
                    .load(tweet.getUser().getProfileImageUrl())
                    .bitmapTransform(new RoundedCornersTransformation(getContext(), 4, 4))
                    .into(viewHolder.ivPhoto);
        }
        if(position == mTweets.size() - 1 && mListener != null){
            mListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void setTweets(List<Tweet> tweets) {
        mTweets.clear();
        mTweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public void addTweets(List<Tweet> tweets){
        int position = tweets.size();
        mTweets.addAll(tweets);
        notifyItemRangeInserted(position, tweets.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.tvScreenName)
        TextView tvScreenName;

        @BindView(R.id.tvText)
        TextView tvText;

        @BindView(R.id.tvTime)
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
