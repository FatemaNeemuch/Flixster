package com.example.flixster.models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.MovieTrailerActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String TAG = "MovieDetailActivity";
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=2a416fc12ab6087a10380559b344cb2d";
    //instance variable
    Movie movie;
    TextView tvAMDTitle;
    TextView tvAMDOverview;
    RatingBar rbVoteAverage;
    TextView tvReleasedate;
    Context context;
    ImageView ivPicture;
    String videoId;
    ImageView ivbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);
        // resolve the view objects
        tvAMDTitle = binding.tvAMDTitle;
        tvAMDOverview = binding.tvAMDOverview;
        rbVoteAverage = binding.rbVoteAverage;
        tvReleasedate = binding.tvReleasedate;
        ivPicture = binding.ivPicture;
        ivbutton = binding.ivbutton;
        ivbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent for the new activity
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(MovieDetailsActivity.class.getSimpleName(), videoId); //not sure about second parameter
                // show the activity
                startActivity(intent);
            }
        });

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvAMDTitle.setText(movie.getTitle());
        tvAMDOverview.setText(movie.getOverview());
        tvAMDOverview.setMovementMethod(new ScrollingMovementMethod());
        tvReleasedate.setText(movie.getReleaseDate());
        // Loads picture based on screen orientation
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Glide.with(this)
                    .load(movie.getBackdroppath())
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(30,10))
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .into(ivPicture);
        }else {
            Glide.with(this)
                    .load(movie.getPosterpath())
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(30,10))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .into(ivPicture);
        }

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        // initialize with API key stored in strings.xml
        AsyncHttpClient ytclient = new AsyncHttpClient();
        ytclient.get(String.format(BASE_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject object = json.jsonObject;
                try {
                    JSONArray array = object.getJSONArray("results");
                    JSONObject movie = array.getJSONObject(0);
                    videoId = movie.getString("key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "Error: " + throwable.getMessage());
            }
        });
    }
}