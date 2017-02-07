package com.example.martyna.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.martyna.popularmovies.MainFragment.adapter;

public class FetchMovieDataTask extends AsyncTask<String, Void, MovieClass[]> {

    private final String MOVIEDATA_TAG = FetchMovieDataTask.class.getSimpleName();

    String movieJSONStr = null;

    @Override
    public MovieClass[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String APPKEY_PARAM = "api_key";

            Uri builtNew = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APPKEY_PARAM, BuildConfig.OPEN_MOVIES_API_KEY)
                    .build();

            URL url = new URL(builtNew.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            movieJSONStr = buffer.toString();

        } catch (
                IOException e
                ) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }

            try {
                return getMovieDataFromJson(movieJSONStr);
            } catch (JSONException e) {
                Log.e(MOVIEDATA_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }
    }

    private MovieClass[] getMovieDataFromJson(String jsonData)
            throws JSONException {

        List<MovieClass> movies = new ArrayList<>();

        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String PLOT_SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";

        String BASIC_URL = "http://image.tmdb.org/t/p/w185/";

        JSONObject movieJson = new JSONObject(jsonData);
        JSONArray movieArray = movieJson.getJSONArray("results");

        for (int i = 0; i < movieArray.length(); i++) {

            MovieClass movieClassItem = new MovieClass();
            JSONObject detail = movieArray.getJSONObject(i);

            String original_title = detail.getString(ORIGINAL_TITLE);
            String poster_path = detail.getString(POSTER_PATH);
            String plot_synopsis = detail.getString(PLOT_SYNOPSIS);
            String user_rating = detail.getString(USER_RATING);
            String release_date = detail.getString(RELEASE_DATE);

            movieClassItem.setPoster_path(poster_path);
            movieClassItem.setOriginal_title(original_title);
            movieClassItem.setPlot_synopsis(plot_synopsis);
            movieClassItem.setRelease_date(release_date);
            movieClassItem.setUser_rating(user_rating);

            movies.add(movieClassItem);
        }

        MovieClass[] moviesList = movies.toArray(new MovieClass[0]);
        return moviesList;
    }

    @Override
    protected void onPostExecute(MovieClass[] strings) {
        if (strings != null) {
            adapter.clear();
            for (MovieClass movieData : strings) {
                adapter.add(movieData);
            }
        }
    }
}
