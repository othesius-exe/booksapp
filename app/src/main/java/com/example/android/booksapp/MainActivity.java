package com.example.android.booksapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Book>> {

    private String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private String API_KEY = "&key=AIzaSyD9Yob2rv-7WHTs6_nklHadEw_0uc7IfKY";

    public static final String LOG_TAG = MainActivity.class.getName();

    private BookAdapter mAdapter;

    private TextView mEmptyView;

    public static final int BOOK_LOADER_ID = 1;

    public ProgressBar mProgressBar;

    private LoaderManager mLoaderManager;

    String mSearchFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make the progress bar invisible on start
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // Set mLoaderManager and initialize the loader
        mLoaderManager = getSupportLoaderManager();

        ListView bookListView = (ListView) findViewById(R.id.book_list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        mEmptyView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyView);

        final EditText searchView = (EditText) findViewById(R.id.search_bar);
        Button searchSubmit = (Button) findViewById(R.id.submit_button);

        searchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the search filter from the user input
                String userInput = searchView.getText().toString();
                mSearchFilter = userInput.replace(" ", "+");

                mLoaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);


                // Reset the loader, with new search parameters
                mLoaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
            }
        });

        // Start a connectivity manager
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // If no connection display no connection message
        if (!isConnected) {
            mEmptyView.setText(R.string.no_connection);
        }

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        Log.i(LOG_TAG, "Creating the Loader");
        mProgressBar.setVisibility(View.VISIBLE);
        return new BooksLoader(this, API_URL + mSearchFilter + API_KEY);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        Log.i(LOG_TAG, "Loader finished");
        mAdapter.clear();
        mEmptyView.setText(R.string.none_found);
        if (data != null && !data.isEmpty()){
            mProgressBar.setVisibility(View.GONE);
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i(LOG_TAG, "Reset");
        mAdapter.clear();
        mLoaderManager.restartLoader(BOOK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        //mSearchView.setIconifiedByDefault(true);
        return true;
    }

}
