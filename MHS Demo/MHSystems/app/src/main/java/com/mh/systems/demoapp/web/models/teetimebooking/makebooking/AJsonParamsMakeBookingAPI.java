
package com.mh.systems.demoapp.web.models.teetimebooking.makebooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsMakeBookingAPI {

    @SerializedName("Version")
    @Expose
    private int Version;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;
    @SerializedName("SlotStart")
    @Expose
    private String SlotStart;
    @SerializedName("PLU")
    @Expose
    private String PLU;
    @SerializedName("Price")
    @Expose
    private String Price;
    @SerializedName("IncludesBuggy")
    @Expose
    private Boolean IncludesBuggy;
    @SerializedName("BuggyPLU")
    @Expose
    private String BuggyPLU;
    @SerializedName("BuggyPrice")
    @Expose
    private String BuggyPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsMakeBookingAPI() {
    }

    /**
     * 
     * @param buggyPLU
     * @param includesBuggy
     * @param price
     * @param pLU
     * @param memberId
     * @param slotStart
     * @param buggyPrice
     * @param version
     */
    public AJsonParamsMakeBookingAPI(int Version, String MemberId, String SlotStart,
                                     String PLU, String Price, Boolean IncludesBuggy,
                                     String BuggyPLU, String BuggyPrice) {
        super();
        this.Version = Version;
        this.MemberId = MemberId;
        this.SlotStart = SlotStart;
        this.PLU = PLU;
        this.Price = Price;
        this.IncludesBuggy = IncludesBuggy;
        this.BuggyPLU = BuggyPLU;
        this.BuggyPrice = BuggyPrice;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int Version) {
        this.Version = Version;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    public String getSlotStart() {
        return SlotStart;
    }

    public void setSlotStart(String SlotStart) {
        this.SlotStart = SlotStart;
    }

    public String getPLU() {
        return PLU;
    }

    public void setPLU(String PLU) {
        this.PLU = PLU;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public Boolean getIncludesBuggy() {
        return IncludesBuggy;
    }

    public void setIncludesBuggy(Boolean IncludesBuggy) {
        this.IncludesBuggy = IncludesBuggy;
    }

    public String getBuggyPLU() {
        return BuggyPLU;
    }

    public void setBuggyPLU(String BuggyPLU) {
        this.BuggyPLU = BuggyPLU;
    }

    public String getBuggyPrice() {
        return BuggyPrice;
    }

    public void setBuggyPrice(String BuggyPrice) {
        this.BuggyPrice = BuggyPrice;
    }

}
