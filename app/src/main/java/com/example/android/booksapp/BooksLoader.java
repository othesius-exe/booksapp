package com.example.android.booksapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Loader class to handle book queries in the background
 */

public class BooksLoader extends android.support.v4.content.AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BooksLoader.class.getName();

    private String mUrl;

        public BooksLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.i(LOG_TAG, "Searching for books...");
        }

        @Override
        public List<Book> loadInBackground() {
            Log.i(LOG_TAG, "Populating Book List");
            if (mUrl == null) {
                return null;
            }

            List<Book> books = QueryUtils.fetchBookData(mUrl);
            return books;
        }
}
