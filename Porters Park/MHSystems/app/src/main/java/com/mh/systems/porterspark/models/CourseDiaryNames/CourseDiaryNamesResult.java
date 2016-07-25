
package com.mh.systems.porterspark.models.CourseDiaryNames;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CourseDiaryNamesResult {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private List<CourseDiaryNamesData> Data = new ArrayList<CourseDiaryNamesData>();

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
    public void setMessage(String message) {
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
    public void setResult(Integer result) {
        this.Result = Result;
    }

    /**
     * 
     * @return
     *     The Data
     */
    public List<CourseDiaryNamesData> getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The Data
     */
    public void setData(List<CourseDiaryNamesData> Data) {
        this.Data = Data;
    }

}
