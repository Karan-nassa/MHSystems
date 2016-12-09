
package com.mh.systems.demoapp.models.UnreadNewsCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnreadNewsCountData {

    @SerializedName("UnRead")
    @Expose
    private Integer UnRead;

    /**
     * 
     * @return
     *     The UnRead
     */
    public Integer getUnRead() {
        return UnRead;
    }

    /**
     * 
     * @param UnRead
     *     The UnRead
     */
    public void setUnRead(Integer UnRead) {
        this.UnRead = UnRead;
    }

}
