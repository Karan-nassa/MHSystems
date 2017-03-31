
package com.mh.systems.brokenhurst.web.models.registertoken;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RegisterTokenResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("Data")
    @Expose
    private Integer data;

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The result
     */
    public Integer getResult() {
        return result;
    }

    /**
     * @param result The Result
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * @return The data
     */
    public Integer getData() {
        return data;
    }

    /**
     * @param data The Data
     */
    public void setData(Integer data) {
        this.data = data;
    }

}
