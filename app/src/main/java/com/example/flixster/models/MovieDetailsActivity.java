package com.example.flixster.models;

import androidx.appcompat.app.AppCompatActivity;

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

    //class constants
    public static final String TAG = "MovieDetailActivity";
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=2a416fc12ab6087a10380559b344cb2d";

    //instance variable
    Movie movie;
    ImageView ivPicture;
    String videoId;
    ImageView ivbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);
        // resolve the view objects
        ivPicture = binding.ivPicture;
        ivbutton = binding.ivbutton;

        ivbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent for the new activity
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                // add videoId to intent
                intent.putExtra(MovieDetailsActivity.class.getSimpleName(), videoId);
                // show the activity
                startActivity(intent);
            }
        });

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        binding.tvAMDTitle.setText(movie.getTitle());
        binding.tvAMDOverview.setText(movie.getOverview());
        //allow the overview to be scrollable
        binding.tvAMDOverview.setMovementMethod(new ScrollingMovementMethod());
        //set the release date
        binding.tvReleasedate.setText(movie.getReleaseDate());
        // Loads picture based on screen orientation
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //loads backdrop with rounded corners and placeholder image
            Glide.with(this)
                    .load(movie.getBackdroppath())
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(30,10))
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .into(ivPicture);
        }else {
            //loads poster with rounded corners and placeholder image
            Glide.with(this)
                    .load(movie.getPosterpath())
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(30,10))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .into(ivPicture);
        }

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage / 2.0f);

        //sending a network request
        AsyncHttpClient ytclient = new AsyncHttpClient();
        ytclient.get(String.format(BASE_URL, movie.getId()), new JsonHttpResponseHandler() {
            //if network request successful
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                //get JSON data from API
                JSONObject object = json.jsonObject;
                try {
                    //create JSONArray of values of results key in JSON data
                    JSONArray array = object.getJSONArray("results");
                    //pick the first trailer video
                    JSONObject movie = array.getJSONObject(0);
                    //assign key value to videoId
                    videoId = movie.getString("key");
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            //if network request failure
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "Error: " + throwable.getMessage());
            }
        });
    }
}