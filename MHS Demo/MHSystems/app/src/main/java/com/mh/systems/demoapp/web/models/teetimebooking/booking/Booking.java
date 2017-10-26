
package com.mh.systems.demoapp.web.models.teetimebooking.booking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("BookingId")
    @Expose
    private Integer BookingId;
    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("CanCancel")
    @Expose
    private Boolean CanCancel;
    @SerializedName("Options")
    @Expose
    private List<Object> Options = null;

    public Integer getBookingId() {
        return BookingId;
    }

    public void setBookingId(Integer BookingId) {
        this.BookingId = BookingId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Boolean getCanCancel() {
        return CanCancel;
    }

    public void setCanCancel(Boolean CanCancel) {
        this.CanCancel = CanCancel;
    }

    public List<Object> getOptions() {
        return Options;
    }

    public void setOptions(List<Object> Options) {
        this.Options = Options;
    }

}
