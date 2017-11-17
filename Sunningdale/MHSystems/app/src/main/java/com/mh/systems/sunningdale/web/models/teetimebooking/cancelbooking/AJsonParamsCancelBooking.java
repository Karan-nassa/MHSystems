
package com.mh.systems.sunningdale.web.models.teetimebooking.cancelbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsCancelBooking {

    @SerializedName("Version")
    @Expose
    private int version;
    @SerializedName("MemberId")
    @Expose
    private String memberId;
    @SerializedName("BookingId")
    @Expose
    private int bookingId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsCancelBooking() {
    }

    /**
     * 
     * @param bookingId
     * @param memberId
     * @param version
     */
    public AJsonParamsCancelBooking(int version, String memberId, int bookingId) {
        super();
        this.version = version;
        this.memberId = memberId;
        this.bookingId = bookingId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

}
