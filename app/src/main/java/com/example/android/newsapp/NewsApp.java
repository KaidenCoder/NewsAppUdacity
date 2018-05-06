package com.example.android.newsapp;

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
public class NewsApp {

    /** Section name of the News in the NewsApp*/
    private String mSection;

    /** Author name of the news in the NewsApp*/
    private String mAuthorName;

    /** Web title of the News in the NewsApp*/
    private String mWebTitle;

    /**  Web publication date of the News in the NewsApp*/
    private String mWebPublicationDate;

    /** Web Url of the News in the NewsApp*/
    private String mWebUrl;

    /**
     * Constructs a new {@Link NewsApp} object.
     *
     * @param section is the news section name
     * @param authorName is the author's first name
     * @param webTitle is the news header
     * @param webPublicationDate is the news publication date
     * @param webUrl is the news url link
     */
    public NewsApp(String section, String authorName, String webTitle, String webPublicationDate, String webUrl){
        mSection = section;
        mAuthorName = authorName;
        mWebPublicationDate = webPublicationDate;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
    }

    /**
     * Returns the section name of the news
     */
    public String getSection(){
        return mSection;
    }

    /**
     * Returns the author first name of the news
     */
    public String getAuthorName() {
        return mAuthorName;
    }

    /**
     * Returns the webTitle of the news
     */
    public String getWebTitle(){
        return mWebTitle;
    }

    /**
     * Returns the webPublicationDate of the news
     */
    public String getWebPublicationDate(){
        return mWebPublicationDate;
    }

    /**
     * Returns the webUrl of the news
     */
    public String getWebUrl(){
        return mWebUrl;
    }
}
