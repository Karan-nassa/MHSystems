
package com.mh.systems.demoapp.models.competitionsEntry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Zone {

    @SerializedName("ZoneID")
    @Expose
    private Integer ZoneID;
    @SerializedName("ZoneNo")
    @Expose
    private Integer ZoneNo;
    @SerializedName("CourseID")
    @Expose
    private Integer CourseID;
    @SerializedName("CourseName")
    @Expose
    private String CourseName;
    @SerializedName("Capacity")
    @Expose
    private Integer Capacity;
    @SerializedName("FreeEntries")
    @Expose
    private Integer FreeEntries;
    @SerializedName("ZoneTeeOffTimeRangeStr")
    @Expose
    private String ZoneTeeOffTimeRangeStr;
    @SerializedName("TeamsPerSlot")
    @Expose
    private Integer TeamsPerSlot;
    @SerializedName("Slots")
    @Expose
    private List<Slot> Slots = new ArrayList<Slot>();

    /**
     * 
     * @return
     *     The ZoneID
     */
    public Integer getZoneID() {
        return ZoneID;
    }

    /**
     * 
     * @param ZoneID
     *     The ZoneID
     */
    public void setZoneID(Integer ZoneID) {
        this.ZoneID = ZoneID;
    }

    /**
     * 
     * @return
     *     The ZoneNo
     */
    public Integer getZoneNo() {
        return ZoneNo;
    }

    /**
     * 
     * @param ZoneNo
     *     The ZoneNo
     */
    public void setZoneNo(Integer ZoneNo) {
        this.ZoneNo = ZoneNo;
    }

    /**
     * 
     * @return
     *     The CourseID
     */
    public Integer getCourseID() {
        return CourseID;
    }

    /**
     * 
     * @param CourseID
     *     The CourseID
     */
    public void setCourseID(Integer CourseID) {
        this.CourseID = CourseID;
    }

    /**
     * 
     * @return
     *     The CourseName
     */
    public String getCourseName() {
        return CourseName;
    }

    /**
     * 
     * @param CourseName
     *     The CourseName
     */
    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    /**
     * 
     * @return
     *     The Capacity
     */
    public Integer getCapacity() {
        return Capacity;
    }

    /**
     * 
     * @param Capacity
     *     The Capacity
     */
    public void setCapacity(Integer Capacity) {
        this.Capacity = Capacity;
    }

    /**
     * 
     * @return
     *     The FreeEntries
     */
    public Integer getFreeEntries() {
        return FreeEntries;
    }

    /**
     * 
     * @param FreeEntries
     *     The FreeEntries
     */
    public void setFreeEntries(Integer FreeEntries) {
        this.FreeEntries = FreeEntries;
    }

    /**
     * 
     * @return
     *     The ZoneTeeOffTimeRangeStr
     */
    public String getZoneTeeOffTimeRangeStr() {
        return ZoneTeeOffTimeRangeStr;
    }

    /**
     * 
     * @param ZoneTeeOffTimeRangeStr
     *     The ZoneTeeOffTimeRangeStr
     */
    public void setZoneTeeOffTimeRangeStr(String ZoneTeeOffTimeRangeStr) {
        this.ZoneTeeOffTimeRangeStr = ZoneTeeOffTimeRangeStr;
    }

    /**
     * 
     * @return
     *     The TeamsPerSlot
     */
    public Integer getTeamsPerSlot() {
        return TeamsPerSlot;
    }

    /**
     * 
     * @param TeamsPerSlot
     *     The TeamsPerSlot
     */
    public void setTeamsPerSlot(Integer TeamsPerSlot) {
        this.TeamsPerSlot = TeamsPerSlot;
    }

    /**
     * 
     * @return
     *     The Slots
     */
    public List<Slot> getSlots() {
        return Slots;
    }

    /**
     * 
     * @param Slots
     *     The Slots
     */
    public void setSlots(List<Slot> Slots) {
        this.Slots = Slots;
    }

}
