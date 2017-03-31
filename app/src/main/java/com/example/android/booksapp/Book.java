package com.example.android.booksapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class defining a book object
 */

public class Book implements Parcelable {

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

    protected Book(Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
