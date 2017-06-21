
package com.mh.systems.corrstown.web.models.competitionsentry;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsUpdateEntry {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("EventId")
    @Expose
    private String eventId;
    @SerializedName("MemberId")
    @Expose
    private String memberId;
    @SerializedName("Players")
    @Expose
    private List<Integer> players = new ArrayList<Integer>();
    @SerializedName("SlotNo")
    @Expose
    private Integer slotNo;
    @SerializedName("ZoneId")
    @Expose
    private int zoneId;
    @SerializedName("EntryId")
    @Expose
    private int EntryId;

    /**
     * 
     * @return
     *     The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The callid
     */
    public String getCallid() {
        return callid;
    }

    /**
     * 
     * @param callid
     *     The callid
     */
    public void setCallid(String callid) {
        this.callid = callid;
    }

    /**
     * 
     * @return
     *     The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * 
     * @param eventId
     *     The EventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * 
     * @return
     *     The memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 
     * @param memberId
     *     The MemberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 
     * @return
     *     The players
     */
    public List<Integer> getPlayers() {
        return players;
    }

    /**
     * 
     * @param players
     *     The Players
     */
    public void setPlayers(List<Integer> players) {
        this.players = players;
    }

    /**
     * 
     * @return
     *     The slotNo
     */
    public Integer getSlotNo() {
        return slotNo;
    }

    /**
     * 
     * @param slotNo
     *     The SlotNo
     */
    public void setSlotNo(Integer slotNo) {
        this.slotNo = slotNo;
    }

    /**
     * 
     * @return
     *     The zoneId
     */
    public int getZoneId() {
        return zoneId;
    }

    /**
     * 
     * @param zoneId
     *     The ZoneId
     */
    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    /**
     *
     * @param EntryId
     *     The EntryId
     */
    public int getEntryId() {
        return EntryId;
    }

    /**
     *
     * @param EntryId
     *     The EntryId
     */
    public void setEntryId(int EntryId) {
        this.EntryId = EntryId;
    }
}
