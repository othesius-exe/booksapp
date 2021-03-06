package com.example.android.booksapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter to display proper fields for a list of Book objects
 */

public class BookAdapter extends ArrayAdapter<Book> {

    // Constructor for a BookAdapter
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    // Finds a view to inflate
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_book, parent, false);
        }

        // Get position of each item in the list
        Book thisBook = getItem(position);

        // Find views to use
        TextView authorView = (TextView) convertView.findViewById(R.id.author);
        TextView titleView = (TextView) convertView.findViewById(R.id.title);

        // Set the appropriate information to each view
        authorView.setText(thisBook.getAuthor());
        titleView.setText(thisBook.getTitle());

        authorView.setSelected(true);
        authorView.requestFocus();

        titleView.setSelected(true);
        titleView.requestFocus();

        return convertView;
    }

}
