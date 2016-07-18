package com.mh.systems.hartsbourne.models.ClubNews;

/**
 * Created by karan@mh.co.in on 17-06-2016.
 */
public class ClubNewsItems {
    String strTimeOfNews;
    String strDateOfNews;
    String strDescOfNews;

    public ClubNewsItems() {
    }

    public ClubNewsItems(String strTimeOfNews, String strDateOfNews, String strDescOfNews) {
        this.strTimeOfNews = strTimeOfNews;
        this.strDateOfNews = strDateOfNews;
        this.strDescOfNews = strDescOfNews;
    }


    public String getStrDescOfNews() {
        return strDescOfNews;
    }

    public void setStrDescOfNews(String strDescOfNews) {
        this.strDescOfNews = strDescOfNews;
    }

    public String getStrTimeOfNews() {
        return strTimeOfNews;
    }

    public void setStrTimeOfNews(String strTimeOfNews) {
        this.strTimeOfNews = strTimeOfNews;
    }

    public String getStrDateOfNews() {
        return strDateOfNews;
    }

    public void setStrDateOfNews(String strDateOfNews) {
        this.strDateOfNews = strDateOfNews;
    }
}
