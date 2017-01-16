package com.example.martyna.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainFragment extends Fragment {

    public static PosterArrayAdapter adapter;

    private final String PREFS_TAG = SettingsActivity.class.getSimpleName();

    public GridView movieGrid;
    private ArrayList<MovieClass> movieList;
    private String sortingOption;
    private String currentSortingOption;
    SharedPreferences prefs;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortingOption = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.default_sort));
        Log.v(PREFS_TAG," old: " +  sortingOption);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<>(Arrays.<MovieClass>asList());
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        movieGrid = (GridView) view.findViewById(R.id.movies_grid);
        adapter = new PosterArrayAdapter(getActivity(), movieList);
        movieGrid.setAdapter(adapter);

        movieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {

                MovieClass movie = adapter.getItem(pos);
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                intent.putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getData() {

        currentSortingOption = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.default_sort));
        Log.v(PREFS_TAG," new: " + currentSortingOption);

        if (currentSortingOption.equals(sortingOption)) {
            FetchMovieDataTask downloadTask = new FetchMovieDataTask();
            downloadTask.execute(currentSortingOption);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (ConnectionCheck.isNetworkAvailable(getContext())) {
            getData();
        } else {
            Toast.makeText(getContext(), "No network! Check your connection.", Toast.LENGTH_LONG).show();
        }

    }
}