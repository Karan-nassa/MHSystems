
package com.mh.systems.sunningdale.web.models.bookinglessons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingLessonsData {

    @SerializedName("Url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
