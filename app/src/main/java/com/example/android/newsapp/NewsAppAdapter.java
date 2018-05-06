package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Owner on 31-03-2018.
 */
/**
 * This project is done by Khaidem Sandip Singha under the Udacity Android Foundations Nanodegree program.
 *
 * I confirm that this submission is my own work. I have not used code from any other Udacity student's or graduate's submission of the same project.
 * I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code may result in the cancellation of my
 * enrollment.
 */
public class NewsAppAdapter extends ArrayAdapter<NewsApp> {

    /**
     * The webPrublicationDate has date and time joined by the letter T,so we need to separate them with
     * the letter 'T'
     */
    private static final String TEXT_SEPERATOR = "T";

    public String datePart;

    public String timePart;

    /**
     * Constructs a new {@link NewsAppAdapter}
     *
     * @param context of the app
     * @param newsApp is the list of news, which is the data source of the adapter
     */
    public NewsAppAdapter(Context context, List<NewsApp> newsApp) {
        super(context, 0, newsApp);
    }

    /**
     * Returns a list item that displays all the news
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the news at the given position in the list of earthquakes
        NewsApp currentNewsApp = getItem(position);

        //Find the TextView with view ID sectionName
        TextView sectionNameView = listItemView.findViewById(R.id.sectionName);
        // Display the location of the current Section name news in that TextView
        sectionNameView.setText(currentNewsApp.getSection());

        //Find the TextView with view ID authorName
        TextView authorFirstNameView = listItemView.findViewById(R.id.authorName);
        // Display the location of the current Author name news in that TextView
        authorFirstNameView.setText(currentNewsApp.getAuthorName());

        //Find the TextView with view ID webTitle
        TextView webTitleView = listItemView.findViewById(R.id.webTitle);
        // Display the location of the current Web title news in that TextView
        webTitleView.setText(currentNewsApp.getWebTitle());

        // Get the original webPublicationDate string from the NewsApp object
        String originalWebPublicationDate = currentNewsApp.getWebPublicationDate();

        // Check whether the original webPublicationDate string contains the "T" letter
        if (originalWebPublicationDate.contains(TEXT_SEPERATOR)) {
            // Split the string into different parts (as an array of Strings)
            String[] parts = originalWebPublicationDate.split(TEXT_SEPERATOR);
            datePart = parts[0];
            timePart = parts[1];
        }
        // Find the TextView with view ID date
        TextView dateView = listItemView.findViewById(R.id.date);
        // Display the date of the current news in that TextView
        dateView.setText(datePart);

        //Replace all the "Z" letter in the timePart by null space
        timePart = timePart.replaceAll("Z", "");
        // Find the TextView with view ID time
        TextView timeView = listItemView.findViewById(R.id.time);
        // Display the date of the current news in that TextView
        timeView.setText(timePart);

        return listItemView;
    }
}