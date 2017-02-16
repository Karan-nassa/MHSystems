
package com.mh.systems.guildford.models.ClubNewsThumbnail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClubNewsThumbnailResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private int Result;
    @SerializedName("Data")
    @Expose
    private List<ClubNewsThumbnailData> Data = null;

    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return The Result
     */
    public int getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(int Result) {
        this.Result = Result;
    }

    /**
     * @return The Data
     */
    public List<ClubNewsThumbnailData> getData() {
        return Data;
    }

    /**
     * @param Data The Data
     */
    public void setData(List<ClubNewsThumbnailData> Data) {
        this.Data = Data;
    }

}
