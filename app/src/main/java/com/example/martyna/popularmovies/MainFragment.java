package com.example.martyna.popularmovies;

import android.content.Intent;
import android.os.Bundle;
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

public class MainFragment extends Fragment {

    private static final String MOVIES_KEY = "movies_key";
    private static final String SORT_OPTION_KEY = "sort_option";

    private ArrayList<MovieClass> movieList;
    private String sortOption;
    public static PosterArrayAdapter adapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        GridView movieGrid = (GridView) view.findViewById(R.id.movies_grid);

        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIES_KEY) || !savedInstanceState.containsKey(SORT_OPTION_KEY)) {
            movieList = new ArrayList<>();
            sortOption = SortOptionClass.getPreferenceSortOrder(getContext());

        } else {

            movieList = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            sortOption = savedInstanceState.getString(SORT_OPTION_KEY);
        }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIES_KEY, movieList);
        outState.putString(SORT_OPTION_KEY, sortOption);
    }

    @Override
    public void onResume() {
        super.onResume();

        String newSortOrder = SortOptionClass.getPreferenceSortOrder(getContext());

        if (movieList.isEmpty() || (newSortOrder != null && !newSortOrder.equals(sortOption))) {
            sortOption = newSortOrder;
            getData();
        }
    }

    public void getData() {

        if (ConnectionCheck.isNetworkAvailable(getActivity())) {

            FetchMovieDataTask downloadTask = new FetchMovieDataTask();
            downloadTask.execute(sortOption);

        } else {
            Toast.makeText(getContext(), "No network! Check your connection.", Toast.LENGTH_LONG).show();
        }
    }
}
