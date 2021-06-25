package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel //annotation indicates class is Parcelable
public class Movie {

    //instance variables
    String backdroppath;
    String posterpath;
    String title;
    String overview;
    Double voteAverage;
    String releaseDate;
    Integer id;

    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        //reference to corresponding JSON data
        id = jsonObject.getInt("id");
        backdroppath = jsonObject.getString("backdrop_path");
        posterpath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        releaseDate = jsonObject.getString("release_date");
    }

    //List of movies from API
    public static List<Movie> fromJsonArray(JSONArray movieJSONArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        //loop through JSONArray and construct a Movie for every element in the JSONArray
        for (int i = 0; i < movieJSONArray.length(); i++){
            movies.add(new Movie(movieJSONArray.getJSONObject(i)));
        }
        return movies;
    }

    public Integer getId() {
        return id;
    }

    public String getBackdroppath() {
        //backdroppath is a partial URL so this makes it a complete URL with poster size w780
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdroppath);
    }

    public String getPosterpath() {
        //posterpath is a partial URL so this makes it a complete URL with poster size w342
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterpath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
