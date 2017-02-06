
package com.mh.systems.redlibbets.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Slot {

    @SerializedName("SlotNo")
    @Expose
    private Integer SlotNo;
    @SerializedName("Capacity")
    @Expose
    private Integer Capacity;
    @SerializedName("SlotStart")
    @Expose
    private String SlotStart;
    @SerializedName("SlotEnd")
    @Expose
    private String SlotEnd;
    @SerializedName("ZoneNameAndTeeOffTime")
    @Expose
    private String ZoneNameAndTeeOffTime;
    @SerializedName("SlotStartTimeStr")
    @Expose
    private String SlotStartTimeStr;
    @SerializedName("IsSlotReserved")
    @Expose
    private Boolean IsSlotReserved;

    /**
     * @return The SlotNo
     */
    public Integer getSlotNo() {
        return SlotNo;
    }

    /**
     * @param SlotNo The SlotNo
     */
    public void setSlotNo(Integer SlotNo) {
        this.SlotNo = SlotNo;
    }

    /**
     * @return The Capacity
     */
    public Integer getCapacity() {
        return Capacity;
    }

    /**
     * @param Capacity The Capacity
     */
    public void setCapacity(Integer Capacity) {
        this.Capacity = Capacity;
    }

    /**
     * @return The SlotStart
     */
    public String getSlotStart() {
        return SlotStart;
    }

    /**
     * @param SlotStart The SlotStart
     */
    public void setSlotStart(String SlotStart) {
        this.SlotStart = SlotStart;
    }

    /**
     * @return The SlotEnd
     */
    public String getSlotEnd() {
        return SlotEnd;
    }

    /**
     * @param SlotEnd The SlotEnd
     */
    public void setSlotEnd(String SlotEnd) {
        this.SlotEnd = SlotEnd;
    }

    /**
     * @return The ZoneNameAndTeeOffTime
     */
    public String getZoneNameAndTeeOffTime() {
        return ZoneNameAndTeeOffTime;
    }

    /**
     * @param ZoneNameAndTeeOffTime The ZoneNameAndTeeOffTime
     */
    public void setZoneNameAndTeeOffTime(String ZoneNameAndTeeOffTime) {
        this.ZoneNameAndTeeOffTime = ZoneNameAndTeeOffTime;
    }

    /**
     * @return The SlotStartTimeStr
     */
    public String getSlotStartTimeStr() {
        return SlotStartTimeStr;
    }

    /**
     * @param SlotStartTimeStr The SlotStartTimeStr
     */
    public void setSlotStartTimeStr(String SlotStartTimeStr) {
        this.SlotStartTimeStr = SlotStartTimeStr;
    }

    /**
     * @return The IsSlotReserved
     */
    public Boolean getIsSlotReserved() {
        return IsSlotReserved;
    }

    /**
     * @param IsSlotReserved The IsSlotReserved
     */
    public void setIsSlotReserved(Boolean IsSlotReserved) {
        this.IsSlotReserved = IsSlotReserved;
    }

}
