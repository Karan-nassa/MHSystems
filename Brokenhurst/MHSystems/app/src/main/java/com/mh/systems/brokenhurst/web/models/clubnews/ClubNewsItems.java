
package com.mh.systems.brokenhurst.web.models.clubnews;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClubNewsItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private List<ClubNewsData> Data = new ArrayList<ClubNewsData>();

    /**
     * 
     * @return
     *     The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * 
     * @param Message
     *     The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * 
     * @return
     *     The Result
     */
    public Integer getResult() {
        return Result;
    }

    /**
     * 
     * @param Result
     *     The Result
     */
    public void setResult(Integer Result) {
        this.Result = Result;
    }

    /**
     * 
     * @return
     *     The Data
     */
    public List<ClubNewsData> getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The Data
     */
    public void setData(List<ClubNewsData> Data) {
        this.Data = Data;
    }

}
