
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.Booking;

import java.io.Serializable;
import java.util.List;

public class NewCompEntryData implements Serializable {

    @SerializedName("ClientId")
    @Expose
    private Integer ClientId;
    @SerializedName("EventId")
    @Expose
    private int EventId;
    @SerializedName("UpdateFailed")
    @Expose
    private boolean UpdateFailed;
    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;
    @SerializedName("PayeeId")
    @Expose
    private Integer PayeeId;
    @SerializedName("PayeeName")
    @Expose
    private String PayeeName;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("EventStartDate")
    @Expose
    private EventStartDate EventStartDate;
    @SerializedName("EntryCloseDate")
    @Expose
    private EntryCloseDate EntryCloseDate;
    @SerializedName("EventName")
    @Expose
    private String EventName;
    @SerializedName("EventDescription")
    @Expose
    private String EventDescription;
    @SerializedName("TeamSize")
    @Expose
    private Integer TeamSize;
    @SerializedName("IsNewBooking")
    @Expose
    private Boolean IsNewBooking;
    @SerializedName("CanModifyBooking")
    @Expose
    private Boolean CanModifyBooking;
    @SerializedName("CompetitionEntryClosed")
    @Expose
    private boolean CompetitionEntryClosed;
    @SerializedName("IsNotEligible")
    @Expose
    private boolean IsNotEligible;
    @SerializedName("IsPayee")
    @Expose
    private Boolean IsPayee;
    @SerializedName("CanRemoveOwnTeamOnly")
    @Expose
    private Boolean CanRemoveOwnTeamOnly;
    @SerializedName("MaxTeamCount")
    @Expose
    private Integer MaxTeamCount;
    @SerializedName("IsSimpleMode")
    @Expose
    private Boolean IsSimpleMode;
    @SerializedName("IsZonedMode")
    @Expose
    private Boolean IsZonedMode;
    @SerializedName("IsSlotMode")
    @Expose
    private Boolean IsSlotMode;
    @SerializedName("AllowGuests")
    @Expose
    private Boolean AllowGuests;
    @SerializedName("FundsAvailable")
    @Expose
    private float FundsAvailable;
    @SerializedName("TotalBookingFee")
    @Expose
    private float TotalBookingFee;
    @SerializedName("CrnSymbol")
    @Expose
    private String CrnSymbol;
    @SerializedName("Zones")
    @Expose
    private List<Zone> Zones = null;
    @SerializedName("Booking")
    @Expose
    private List<Booking> Booking = null;
    @SerializedName("AllPlayers")
    @Expose
    private List<AllPlayer> AllPlayers = null;
    @SerializedName("HCapSelector")
    @Expose
    private Object HCapSelector;
    @SerializedName("EntryFee")
    @Expose
    private float EntryFee;

    private boolean slefAlreadyAdded = false;
    private int MaxTeamAdded;

    public Integer getClientId() {
        return ClientId;
    }

    public void setClientId(Integer ClientId) {
        this.ClientId = ClientId;
    }

    public int getEventID() {
        return EventId;
    }

    public void setEventID(int EventId) {
        this.EventId = EventId;
    }

    public Integer getPayeeId() {
        return PayeeId;
    }

    public void setPayeeId(Integer PayeeId) {
        this.PayeeId = PayeeId;
    }

    public String getPayeeName() {
        return PayeeName;
    }

    public void setPayeeName(String PayeeName) {
        this.PayeeName = PayeeName;
    }

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
    }

    public EventStartDate getEventStartDate() {
        return EventStartDate;
    }

    public void setEventStartDate(EventStartDate EventStartDate) {
        this.EventStartDate = EventStartDate;
    }

    public EntryCloseDate getEntryCloseDate() {
        return EntryCloseDate;
    }

    public void setEntryCloseDate(EntryCloseDate EntryCloseDate) {
        this.EntryCloseDate = EntryCloseDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String EventDescription) {
        this.EventDescription = EventDescription;
    }

    public Integer getTeamSize() {
        return TeamSize;
    }

    public void setTeamSize(Integer TeamSize) {
        this.TeamSize = TeamSize;
    }

    public Boolean getIsNewBooking() {
        return IsNewBooking;
    }

    public void setIsNewBooking(Boolean IsNewBooking) {
        this.IsNewBooking = IsNewBooking;
    }

    public Boolean getCanModifyBooking() {
        return CanModifyBooking;
    }

    public void setCanModifyBooking(Boolean CanModifyBooking) {
        this.CanModifyBooking = CanModifyBooking;
    }

    public boolean isCompetitionEntryClosed() {
        return CompetitionEntryClosed;
    }

    public void setCompetitionEntryClosed(boolean competitionEntryClosed) {
        CompetitionEntryClosed = competitionEntryClosed;
    }

    public boolean isNotEligible() {
        return IsNotEligible;
    }

    public void setNotEligible(boolean notEligible) {
        IsNotEligible = notEligible;
    }

    public Boolean getIsPayee() {
        return IsPayee;
    }

    public void setIsPayee(Boolean IsPayee) {
        this.IsPayee = IsPayee;
    }

    public Boolean getCanRemoveOwnTeamOnly() {
        return CanRemoveOwnTeamOnly;
    }

    public void setCanRemoveOwnTeamOnly(Boolean CanRemoveOwnTeamOnly) {
        this.CanRemoveOwnTeamOnly = CanRemoveOwnTeamOnly;
    }

    public Integer getMaxTeamCount() {
        return MaxTeamCount;
    }

    public void setMaxTeamCount(Integer MaxTeamCount) {
        this.MaxTeamCount = MaxTeamCount;
    }

    public Boolean getIsSimpleMode() {
        return IsSimpleMode;
    }

    public void setIsSimpleMode(Boolean IsSimpleMode) {
        this.IsSimpleMode = IsSimpleMode;
    }

    public Boolean getIsZonedMode() {
        return IsZonedMode;
    }

    public void setIsZonedMode(Boolean IsZonedMode) {
        this.IsZonedMode = IsZonedMode;
    }

    public Boolean getIsSlotMode() {
        return IsSlotMode;
    }

    public void setIsSlotMode(Boolean IsSlotMode) {
        this.IsSlotMode = IsSlotMode;
    }

    public Boolean getAllowGuests() {
        return AllowGuests;
    }

    public void setAllowGuests(Boolean AllowGuests) {
        this.AllowGuests = AllowGuests;
    }

    public float getFundsAvailable() {
        return FundsAvailable;
    }

    public void setFundsAvailable(float FundsAvailable) {
        this.FundsAvailable = FundsAvailable;
    }

    public float getTotalBookingFee() {
        return TotalBookingFee;
    }

    public void setTotalBookingFee(float TotalBookingFee) {
        this.TotalBookingFee = TotalBookingFee;
    }

    public String getCrnSymbol() {
        return CrnSymbol;
    }

    public void setCrnSymbol(String CrnSymbol) {
        this.CrnSymbol = CrnSymbol;
    }

    public List<Zone> getZones() {
        return Zones;
    }

    public void setZones(List<Zone> Zones) {
        this.Zones = Zones;
    }

    public List<Booking> getBooking() {
        return Booking;
    }

    public void setBooking(List<Booking> Booking) {
        this.Booking = Booking;
    }

    public List<AllPlayer> getAllPlayers() {
        return AllPlayers;
    }

    public void setAllPlayers(List<AllPlayer> AllPlayers) {
        this.AllPlayers = AllPlayers;
    }

    public Object getHCapSelector() {
        return HCapSelector;
    }

    public void setHCapSelector(Object HCapSelector) {
        this.HCapSelector = HCapSelector;
    }

    public boolean isUpdateFailed() {
        return UpdateFailed;
    }

    public void setUpdateFailed(boolean updateFailed) {
        UpdateFailed = updateFailed;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public boolean isSlefAlreadyAdded() {
        return slefAlreadyAdded;
    }

    public void setSelfAlreadyAdded(boolean slefAlreadyAdded) {
        this.slefAlreadyAdded = slefAlreadyAdded;
    }

    public float getEntryFee() {
        return EntryFee;
    }

    public void setEntryFee(float entryFee) {
        EntryFee = entryFee;
    }

    public int getMaxTeamAdded() {
        return MaxTeamAdded;
    }

    public void setMaxTeamAdded(int maxTeamAdded) {
        MaxTeamAdded = maxTeamAdded;
    }
}
