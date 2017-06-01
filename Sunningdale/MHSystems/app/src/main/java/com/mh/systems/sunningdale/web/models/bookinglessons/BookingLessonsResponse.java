
package com.mh.systems.sunningdale.web.models.bookinglessons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingLessonsResponse {

    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private BookingLessonsData data;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookingLessonsData getData() {
        return data;
    }

    public void setData(BookingLessonsData data) {
        this.data = data;
    }

}
