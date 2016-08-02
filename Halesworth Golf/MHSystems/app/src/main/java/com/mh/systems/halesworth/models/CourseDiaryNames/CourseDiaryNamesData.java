
package com.mh.systems.halesworth.models.CourseDiaryNames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDiaryNamesData {

    @SerializedName("Key")
    @Expose
    private String Key;
    @SerializedName("Description")
    @Expose
    private String Description;

    /**
     * 
     * @return
     *     The Key
     */
    public String getKey() {
        return Key;
    }

    /**
     * 
     * @param Key
     *     The Key
     */
    public void setKey(String Key) {
        this.Key = Key;
    }

    /**
     * 
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

}
