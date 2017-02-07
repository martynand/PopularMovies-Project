package com.example.martyna.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SortOptionClass {

    static String getPreferenceSortOrder(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.pref_sort_key), context.getString(R.string.default_sort));

    }
}
