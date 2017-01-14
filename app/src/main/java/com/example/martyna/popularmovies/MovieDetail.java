package com.example.martyna.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetail extends AppCompatActivity {

    private static final String MOVIEDETAILFRAGMENT_TAG = "MovieDetailFragment";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int iD = item.getItemId();
        if (iD == R.id.settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerDetail, new PlaceholderFragment())
                    .commit();
        }

        ActionBar menu = getSupportActionBar();
        menu.setDisplayHomeAsUpEnabled(true);
    }

    public static class PlaceholderFragment extends Fragment {

        @BindView(R.id.posterImage)
        ImageView posterImage;
        @BindView(R.id.titleMovie)
        TextView titleMovie;
        @BindView(R.id.ratingMovie)
        TextView ratingMovie;
        @BindView(R.id.dateMovie)
        TextView dateMovie;
        @BindView(R.id.plotMovie)
        TextView plotMovie;
        public Unbinder unbinder;

        private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Intent intent = getActivity().getIntent();
            View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);
            unbinder = ButterKnife.bind(this, view);

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                MovieClass movieDetail = intent.getParcelableExtra(intent.EXTRA_TEXT);

                Picasso.with(getContext())
                        .load("http://image.tmdb.org/t/p/w185//" + movieDetail.getPoster_path())
                        .placeholder(R.drawable.my_placeholder)
                        .error(R.drawable.my_placeholder_error)
                        .into(posterImage);

                titleMovie.setText("Title: " + movieDetail.getOriginal_title());
                ratingMovie.setText("Rating: " + movieDetail.getUser_rating() + "/10");
                dateMovie.setText("Release date: " + movieDetail.getRelease_date());
                plotMovie.setText(movieDetail.getPlot_synopsis());
            }
            return view;
        }
    }
}
