package com.mh.systems.correstown.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 20-07-2016.
 */
public class CoursesData {
    @SerializedName("Key")
    @Expose
    private String Key;
    @SerializedName("Description")
    @Expose
    private String Description;

    /**
     * @return The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description The Description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * @return The Key
     */
    public String getKey() {
        return Key;
    }

    /**
     * @param Key The Key
     */
    public void setKey(String key) {
        Key = key;
    }
}
