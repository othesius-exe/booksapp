package com.example.android.booksapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Book>> {

    private String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private String API_KEY = "AIzaSyD9Yob2rv-7WHTs6_nklHadEw_0uc7IfKY";

    public static final String LOG_TAG = MainActivity.class.getName();

    private BookAdapter mAdapter;

    private TextView mEmptyView;

    public static final int BOOK_LOADER_ID = 1;

    private ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

    private LoaderManager mLoaderManager;

    SearchView mSearchView;

    String mSearchFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Make the progress bar invisible on start
        mProgressBar.setVisibility(View.GONE);

        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(BOOK_LOADER_ID, null, this);

        ListView bookListView = (ListView) findViewById(R.id.book_list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        mEmptyView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyView);

        // Start a connectivity manager
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            mEmptyView.setText(R.string.no_connection);
        }

    }

    public void submit(View view) {
        EditText searchView = (EditText) findViewById(R.id.search_bar);
        mSearchFilter = searchView.getText().toString();
        mLoaderManager.restartLoader(BOOK_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        Log.i(LOG_TAG, "Creating the Loader");
        return new BooksLoader(this, API_URL + mSearchFilter + API_KEY);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        Log.i(LOG_TAG, "Loader finished");
        mAdapter.clear();
        mEmptyView.setText(R.string.none_found);
        mProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i(LOG_TAG, "Reset");
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        mSearchView.setIconifiedByDefault(true);
        return true;
    }

}
