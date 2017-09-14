
package com.mh.systems.sunningdale.web.models.competitionsentrynew.confirmbooking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsConfirmBooking {

    @SerializedName("ClientId")
    @Expose
    private String ClientId;
    @SerializedName("EventId")
    @Expose
    private Integer EventId;
    @SerializedName("PayeeId")
    @Expose
    private Integer PayeeId;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;
    @SerializedName("RemoveEntry")
    @Expose
    private Boolean RemoveEntry;
    @SerializedName("Booking")
    @Expose
    private List<Booking> Booking = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsConfirmBooking() {
    }

    /**
     * 
     * @param booking
     * @param eventId
     * @param payeeId
     * @param memberId
     * @param removeEntry
     * @param ClientId
     */
    public AJsonParamsConfirmBooking(String ClientId, Integer EventId, Integer PayeeId, String MemberId, Boolean RemoveEntry, List<Booking> Booking) {
        super();
        this.ClientId = ClientId;
        this.EventId = EventId;
        this.PayeeId = PayeeId;
        this.MemberId = MemberId;
        this.RemoveEntry = RemoveEntry;
        this.Booking = Booking;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }

    public Integer getEventId() {
        return EventId;
    }

    public void setEventId(Integer EventId) {
        this.EventId = EventId;
    }

    public Integer getPayeeId() {
        return PayeeId;
    }

    public void setPayeeId(Integer PayeeId) {
        this.PayeeId = PayeeId;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    public Boolean getRemoveEntry() {
        return RemoveEntry;
    }

    public void setRemoveEntry(Boolean RemoveEntry) {
        this.RemoveEntry = RemoveEntry;
    }

    public List<Booking> getBooking() {
        return Booking;
    }

    public void setBooking(List<Booking> Booking) {
        this.Booking = Booking;
    }

}
