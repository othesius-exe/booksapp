package com.example.android.booksapp;

/**
 * Created by Othesius on 3/25/17.
 */

public class Book {

    private String mAuthor;

    private String mTitle;

    private String mCategory;

    public Book(String author, String title, String category) {
        mAuthor = author;
        mTitle = title;
        mCategory = category;
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
    // Method to return Subject
    public String getCategory() {
        return mCategory;
    }

    // Return a user readable string of the books info
    @Override
    public String toString() {
        return mTitle + " " +
                mAuthor + " " +
                mCategory;
    }

}
