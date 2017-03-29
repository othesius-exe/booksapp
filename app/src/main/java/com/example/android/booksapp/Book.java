package com.example.android.booksapp;

/**
 * Created by Othesius on 3/25/17.
 */

public class Book {

    private String mAuthor;

    private String mTitle;

    public Book(String author, String title) {
        mAuthor = author;
        mTitle = title;
    }

    // Define public methods for retrieving individual pieces of info on each book
    // Method to return Author
    public String getAuthor() {
        return mAuthor;
    }
    // Method to return Title
    public String getTitle() {
        return mTitle;
    }

    // Return a user readable string of the books info
    @Override
    public String toString() {
        return mTitle + " " +
                mAuthor;
    }

}
