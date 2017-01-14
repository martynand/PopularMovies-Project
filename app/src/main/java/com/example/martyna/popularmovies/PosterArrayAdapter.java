package com.example.martyna.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterArrayAdapter extends ArrayAdapter<MovieClass> {

    private final String LOG_TAG = PosterArrayAdapter.class.getSimpleName();

    static class ViewHolder {
        ImageView poster;
    }

    public PosterArrayAdapter(Activity context, List<MovieClass> movieClass) {
        super(context, 0, movieClass);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieClass movieClass = getItem(position);
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item, parent, false);

            holder = new ViewHolder();
            holder.poster = (ImageView) convertView.findViewById(R.id.posterView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).setLoggingEnabled(true);

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185/" + movieClass.getPoster_path())
                .placeholder(R.drawable.my_placeholder)
                .error(R.drawable.my_placeholder_error)
                .into(holder.poster);

        return convertView;
    }
}
