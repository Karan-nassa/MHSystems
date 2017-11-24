package com.mh.systems.clubhouse.web.models.teetimebooking.booking;

import com.google.gson.annotations.Expose;
import com.newrelic.com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09-11-2017.
 */
public class Option {

    @SerializedName("Description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
