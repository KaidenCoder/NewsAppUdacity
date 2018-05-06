package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** url connnection read time out*/
    private static final int READ_TIMEOUT = 10000;

    /** url connection connect timeout*/
    private static final int CONNECT_TIMEOUT = 15000;

    /** url connection if get response displays 200*/
    private static final int GET_RESPONSECODE = 200;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){
    }

    /**
     * Query the USGS dataset and return a list of {@link NewsApp} objects.
     */
    public static List<NewsApp> fetchNewsAppData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Extract relevant fields from the JSON response and create a list of {@link NewsApp}
        List<NewsApp> newsApp = extractFeatureFromJson(jsonResponse);
        Log.i(LOG_TAG,"Fetching News Data");
        // Return the list of {@link NewsApp}
        return newsApp;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            Log.i(LOG_TAG, "makeHttpRequest: "+url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == GET_RESPONSECODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link NewsApp} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<NewsApp> extractFeatureFromJson(String newsAppJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsAppJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        List<NewsApp> newsApp = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsAppJSON);

            JSONObject response = baseJsonResponse.getJSONObject("response");
            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or news).
            JSONArray newsAppArray = response.getJSONArray("results");

            // For each news in the newsAppArray, create an {@link NewsApp} object
            for (int i = 0; i < newsAppArray.length(); i++) {

                // Get a single news at position i within the list of news
                JSONObject currentNewsApp = newsAppArray.getJSONObject(i);

                // Extract the value for the key called "sectionName"
                String sectionName = currentNewsApp.optString("sectionName");

                // Extract the value for the key called "webTitle"
                String webTitle = currentNewsApp.optString("webTitle");

                // Extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNewsApp.optString("webPublicationDate");

                // Extract the value for the key called "webUrl"
                String webUrl = currentNewsApp.optString("webUrl");

                //Defining the variable authorName
                String authorName = "";

                // Extract the JSONArray associated with the key called "tags",
                // which represents a list of tags.
                JSONArray tagsArray = currentNewsApp.getJSONArray("tags");
                /** Checking if author name is empty or not*/
                if (tagsArray.length() == 0){
                    authorName = null;
                } else {
                    /**If author name is not empty, then display authorName */
                    for(int j = 0; j < tagsArray.length(); j++) {
                        JSONObject nameList = tagsArray.getJSONObject(j);
                        authorName = nameList.optString("webTitle");
                    }
                }
                // Create a new {@link NewsApp} object with sectionName, authorName, webTitle, webPublicationDate,
                // and webUrl from the JSON response.
                NewsApp news = new NewsApp(sectionName, authorName, webTitle, webPublicationDate, webUrl);

                // Add the new {@link NewsApp} to the list of news.
                newsApp.add(news);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        // Return the list of news
        return newsApp;
    }
}
