
package com.mh.systems.porterspark.models.ResetPassword;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResetPasswordItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private Object data;

    /**
     * 
     * @return
     *     The message
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
     *     The data
     */
    public Object getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The Data
     */
    public void setData(Object data) {
        this.data = data;
    }

}