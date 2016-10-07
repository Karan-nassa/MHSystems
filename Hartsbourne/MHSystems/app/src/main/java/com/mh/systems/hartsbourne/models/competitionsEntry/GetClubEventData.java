
package com.mh.systems.hartsbourne.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetClubEventData {

    @SerializedName("ClubID")
    @Expose
    private Integer ClubID;
    @SerializedName("EventID")
    @Expose
    private Integer EventID;
    @SerializedName("EventStartDate")
    @Expose
    private String EventStartDate;
    @SerializedName("eventDateStr")
    @Expose
    private String eventDateStr;
    @SerializedName("eventTime")
    @Expose
    private String eventTime;
    @SerializedName("EventName")
    @Expose
    private String EventName;
    @SerializedName("EventDescription")
    @Expose
    private String EventDescription;
    @SerializedName("EventStatus")
    @Expose
    private Integer EventStatus;
    @SerializedName("EventStatusStr")
    @Expose
    private String EventStatusStr;
    @SerializedName("IsPublicEvent")
    @Expose
    private Boolean IsPublicEvent;
    @SerializedName("Rounds")
    @Expose
    private Integer Rounds;
    @SerializedName("CompBasis")
    @Expose
    private String CompBasis;
    @SerializedName("EntryFeeValue")
    @Expose
    private Double EntryFeeValue;
    @SerializedName("IncludesMember")
    @Expose
    private Boolean IncludesMember;
    @SerializedName("AllowCompEntryYearCount")
    @Expose
    private Integer AllowCompEntryYearCount;
    @SerializedName("AllowCompEntryMonthCount")
    @Expose
    private Integer AllowCompEntryMonthCount;
    @SerializedName("AllowCompEntryWeekCount")
    @Expose
    private Integer AllowCompEntryWeekCount;
    @SerializedName("AllowCompEntryDayCount")
    @Expose
    private Integer AllowCompEntryDayCount;
    @SerializedName("AllowCompEntryEventType")
    @Expose
    private Integer AllowCompEntryEventType;
    @SerializedName("AllowCompEntryEventEnabled")
    @Expose
    private Boolean AllowCompEntryEventEnabled;
    @SerializedName("AllowCompEntrySelfEntryMode")
    @Expose
    private Integer AllowCompEntrySelfEntryMode;
    @SerializedName("AllowCompEntrySelfEntryModeStr")
    @Expose
    private String AllowCompEntrySelfEntryModeStr;
    @SerializedName("AllowCompEntryAdHocSelection")
    @Expose
    private Boolean AllowCompEntryAdHocSelection;
    @SerializedName("TeamSize")
    @Expose
    private Integer TeamSize;
    @SerializedName("IsEntryAllowed")
    @Expose
    private Boolean IsEntryAllowed;
    @SerializedName("IsTeeTimeSlotsAllowed")
    @Expose
    private Boolean IsTeeTimeSlotsAllowed;
    @SerializedName("ClubEventStartSheet")
    @Expose
    private ClubEventStartSheet ClubEventStartSheet;
    @SerializedName("MemberName")
    @Expose
    private String MemberName;
    @SerializedName("Entry")
    @Expose
    private Entry Entry;

    /**
     * 
     * @return
     *     The ClubID
     */
    public Integer getClubID() {
        return ClubID;
    }

    /**
     * 
     * @param ClubID
     *     The ClubID
     */
    public void setClubID(Integer ClubID) {
        this.ClubID = ClubID;
    }

    /**
     * 
     * @return
     *     The EventID
     */
    public Integer getEventID() {
        return EventID;
    }

    /**
     * 
     * @param EventID
     *     The EventID
     */
    public void setEventID(Integer EventID) {
        this.EventID = EventID;
    }

    /**
     * 
     * @return
     *     The EventStartDate
     */
    public String getEventStartDate() {
        return EventStartDate;
    }

    /**
     * 
     * @param EventStartDate
     *     The EventStartDate
     */
    public void setEventStartDate(String EventStartDate) {
        this.EventStartDate = EventStartDate;
    }

    /**
     * 
     * @return
     *     The eventDateStr
     */
    public String getEventDateStr() {
        return eventDateStr;
    }

    /**
     * 
     * @param eventDateStr
     *     The eventDateStr
     */
    public void setEventDateStr(String eventDateStr) {
        this.eventDateStr = eventDateStr;
    }

    /**
     * 
     * @return
     *     The eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * 
     * @param eventTime
     *     The eventTime
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * 
     * @return
     *     The EventName
     */
    public String getEventName() {
        return EventName;
    }

    /**
     * 
     * @param EventName
     *     The EventName
     */
    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    /**
     * 
     * @return
     *     The EventDescription
     */
    public String getEventDescription() {
        return EventDescription;
    }

    /**
     * 
     * @param EventDescription
     *     The EventDescription
     */
    public void setEventDescription(String EventDescription) {
        this.EventDescription = EventDescription;
    }

    /**
     * 
     * @return
     *     The EventStatus
     */
    public Integer getEventStatus() {
        return EventStatus;
    }

    /**
     * 
     * @param EventStatus
     *     The EventStatus
     */
    public void setEventStatus(Integer EventStatus) {
        this.EventStatus = EventStatus;
    }

    /**
     * 
     * @return
     *     The EventStatusStr
     */
    public String getEventStatusStr() {
        return EventStatusStr;
    }

    /**
     * 
     * @param EventStatusStr
     *     The EventStatusStr
     */
    public void setEventStatusStr(String EventStatusStr) {
        this.EventStatusStr = EventStatusStr;
    }

    /**
     * 
     * @return
     *     The IsPublicEvent
     */
    public Boolean getIsPublicEvent() {
        return IsPublicEvent;
    }

    /**
     * 
     * @param IsPublicEvent
     *     The IsPublicEvent
     */
    public void setIsPublicEvent(Boolean IsPublicEvent) {
        this.IsPublicEvent = IsPublicEvent;
    }

    /**
     * 
     * @return
     *     The Rounds
     */
    public Integer getRounds() {
        return Rounds;
    }

    /**
     * 
     * @param Rounds
     *     The Rounds
     */
    public void setRounds(Integer Rounds) {
        this.Rounds = Rounds;
    }

    /**
     * 
     * @return
     *     The CompBasis
     */
    public String getCompBasis() {
        return CompBasis;
    }

    /**
     * 
     * @param CompBasis
     *     The CompBasis
     */
    public void setCompBasis(String CompBasis) {
        this.CompBasis = CompBasis;
    }

    /**
     * 
     * @return
     *     The EntryFeeValue
     */
    public Double getEntryFeeValue() {
        return EntryFeeValue;
    }

    /**
     * 
     * @param EntryFeeValue
     *     The EntryFeeValue
     */
    public void setEntryFeeValue(Double EntryFeeValue) {
        this.EntryFeeValue = EntryFeeValue;
    }

    /**
     * 
     * @return
     *     The IncludesMember
     */
    public Boolean getIncludesMember() {
        return IncludesMember;
    }

    /**
     * 
     * @param IncludesMember
     *     The IncludesMember
     */
    public void setIncludesMember(Boolean IncludesMember) {
        this.IncludesMember = IncludesMember;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryYearCount
     */
    public Integer getAllowCompEntryYearCount() {
        return AllowCompEntryYearCount;
    }

    /**
     * 
     * @param AllowCompEntryYearCount
     *     The AllowCompEntryYearCount
     */
    public void setAllowCompEntryYearCount(Integer AllowCompEntryYearCount) {
        this.AllowCompEntryYearCount = AllowCompEntryYearCount;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryMonthCount
     */
    public Integer getAllowCompEntryMonthCount() {
        return AllowCompEntryMonthCount;
    }

    /**
     * 
     * @param AllowCompEntryMonthCount
     *     The AllowCompEntryMonthCount
     */
    public void setAllowCompEntryMonthCount(Integer AllowCompEntryMonthCount) {
        this.AllowCompEntryMonthCount = AllowCompEntryMonthCount;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryWeekCount
     */
    public Integer getAllowCompEntryWeekCount() {
        return AllowCompEntryWeekCount;
    }

    /**
     * 
     * @param AllowCompEntryWeekCount
     *     The AllowCompEntryWeekCount
     */
    public void setAllowCompEntryWeekCount(Integer AllowCompEntryWeekCount) {
        this.AllowCompEntryWeekCount = AllowCompEntryWeekCount;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryDayCount
     */
    public Integer getAllowCompEntryDayCount() {
        return AllowCompEntryDayCount;
    }

    /**
     * 
     * @param AllowCompEntryDayCount
     *     The AllowCompEntryDayCount
     */
    public void setAllowCompEntryDayCount(Integer AllowCompEntryDayCount) {
        this.AllowCompEntryDayCount = AllowCompEntryDayCount;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryEventType
     */
    public Integer getAllowCompEntryEventType() {
        return AllowCompEntryEventType;
    }

    /**
     * 
     * @param AllowCompEntryEventType
     *     The AllowCompEntryEventType
     */
    public void setAllowCompEntryEventType(Integer AllowCompEntryEventType) {
        this.AllowCompEntryEventType = AllowCompEntryEventType;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryEventEnabled
     */
    public Boolean getAllowCompEntryEventEnabled() {
        return AllowCompEntryEventEnabled;
    }

    /**
     * 
     * @param AllowCompEntryEventEnabled
     *     The AllowCompEntryEventEnabled
     */
    public void setAllowCompEntryEventEnabled(Boolean AllowCompEntryEventEnabled) {
        this.AllowCompEntryEventEnabled = AllowCompEntryEventEnabled;
    }

    /**
     * 
     * @return
     *     The AllowCompEntrySelfEntryMode
     */
    public Integer getAllowCompEntrySelfEntryMode() {
        return AllowCompEntrySelfEntryMode;
    }

    /**
     * 
     * @param AllowCompEntrySelfEntryMode
     *     The AllowCompEntrySelfEntryMode
     */
    public void setAllowCompEntrySelfEntryMode(Integer AllowCompEntrySelfEntryMode) {
        this.AllowCompEntrySelfEntryMode = AllowCompEntrySelfEntryMode;
    }

    /**
     * 
     * @return
     *     The AllowCompEntrySelfEntryModeStr
     */
    public String getAllowCompEntrySelfEntryModeStr() {
        return AllowCompEntrySelfEntryModeStr;
    }

    /**
     * 
     * @param AllowCompEntrySelfEntryModeStr
     *     The AllowCompEntrySelfEntryModeStr
     */
    public void setAllowCompEntrySelfEntryModeStr(String AllowCompEntrySelfEntryModeStr) {
        this.AllowCompEntrySelfEntryModeStr = AllowCompEntrySelfEntryModeStr;
    }

    /**
     * 
     * @return
     *     The AllowCompEntryAdHocSelection
     */
    public Boolean getAllowCompEntryAdHocSelection() {
        return AllowCompEntryAdHocSelection;
    }

    /**
     * 
     * @param AllowCompEntryAdHocSelection
     *     The AllowCompEntryAdHocSelection
     */
    public void setAllowCompEntryAdHocSelection(Boolean AllowCompEntryAdHocSelection) {
        this.AllowCompEntryAdHocSelection = AllowCompEntryAdHocSelection;
    }

    /**
     * 
     * @return
     *     The TeamSize
     */
    public Integer getTeamSize() {
        return TeamSize;
    }

    /**
     * 
     * @param TeamSize
     *     The TeamSize
     */
    public void setTeamSize(Integer TeamSize) {
        this.TeamSize = TeamSize;
    }

    /**
     * 
     * @return
     *     The IsEntryAllowed
     */
    public Boolean getIsEntryAllowed() {
        return IsEntryAllowed;
    }

    /**
     * 
     * @param IsEntryAllowed
     *     The IsEntryAllowed
     */
    public void setIsEntryAllowed(Boolean IsEntryAllowed) {
        this.IsEntryAllowed = IsEntryAllowed;
    }

    /**
     * 
     * @return
     *     The IsTeeTimeSlotsAllowed
     */
    public Boolean getIsTeeTimeSlotsAllowed() {
        return IsTeeTimeSlotsAllowed;
    }

    /**
     * 
     * @param IsTeeTimeSlotsAllowed
     *     The IsTeeTimeSlotsAllowed
     */
    public void setIsTeeTimeSlotsAllowed(Boolean IsTeeTimeSlotsAllowed) {
        this.IsTeeTimeSlotsAllowed = IsTeeTimeSlotsAllowed;
    }

    /**
     * 
     * @return
     *     The ClubEventStartSheet
     */
    public ClubEventStartSheet getClubEventStartSheet() {
        return ClubEventStartSheet;
    }

    /**
     * 
     * @param ClubEventStartSheet
     *     The ClubEventStartSheet
     */
    public void setClubEventStartSheet(ClubEventStartSheet ClubEventStartSheet) {
        this.ClubEventStartSheet = ClubEventStartSheet;
    }

    /**
     *
     * @return
     * The MemberName
     */
    public String getMemberName() {
        return MemberName;
    }

    /**
     *
     * @param memberName
     * The MemberName
     */
    public void setMemberName(String MemberName) {
        this.MemberName = MemberName;
    }

    /**
     *
     * @return
     * The Entry
     */
    public Entry getEntry() {
        return Entry;
    }

    /**
     *
     * @param Entry
     * The Entry
     */
    public void setEntry(Entry Entry) {
        this.Entry = Entry;
    }
}
