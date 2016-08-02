
package com.mh.systems.halesworth.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionsResultItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("MyAccountData")
    @Expose
    private List<CompetitionsData> Data = new ArrayList<CompetitionsData>();

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
     *     The MyAccountData
     */
    public List<CompetitionsData> getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The MyAccountData
     */
    public void setData(List<CompetitionsData> Data) {
        this.Data = Data;
    }

}
