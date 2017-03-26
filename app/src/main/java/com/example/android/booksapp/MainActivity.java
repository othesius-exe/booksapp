package com.example.android.booksapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private String API_KEY = "AIzaSyD9Yob2rv-7WHTs6_nklHadEw_0uc7IfKY";

    private BookAdapter mAdapter;

    private TextView mEmptyView;

    public static final int BOOK_LOADER_ID = 1;

    private ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

    SearchView mSearchView;

    String mSearchFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar.setVisibility(View.GONE);

        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(MainActivity.this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        mSearchView.setIconifiedByDefault(true);
        return true;
    }

    public boolean onQueryTextChange(String newText) {
        // Called when the action bar search text has changed.  Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        // Don't do anything if the filter hasn't actually changed.
        // Prevents restarting the loader when restoring state.
        if (mSearchFilter == null && newFilter == null) {
            return true;
        }
        if (mSearchFilter != null && mSearchFilter.equals(newFilter)) {
            return true;
        }
        mSearchFilter = newFilter;
        getLoaderManager().restartLoader(0, null, null);
        return true;
    }

    @Override public boolean onQueryTextSubmit(String query) {
        return true;
    }
}
