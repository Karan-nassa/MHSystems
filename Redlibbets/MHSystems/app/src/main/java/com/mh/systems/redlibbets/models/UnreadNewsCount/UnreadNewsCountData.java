package com.mh.systems.redlibbets.models.UnreadNewsCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Karan Nassa on 19-12-2016.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class UnreadNewsCountData {

    @SerializedName("UnRead")
    @Expose
    private Integer UnRead;

    /**
     * @return The UnRead
     */
    public Integer getUnRead() {
        return UnRead;
    }

    /**
     * @param UnRead The UnRead
     */
    public void setUnRead(Integer UnRead) {
        this.UnRead = UnRead;
    }

}
