
package com.mh.systems.teesside.web.models.teetimebooking.makebooking;

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
    private int Price;
    @SerializedName("BuggyQty")
    @Expose
    private int BuggyQty;
    @SerializedName("BuggyPLU")
    @Expose
    private String BuggyPLU;
    @SerializedName("BuggyPrice")
    @Expose
    private int BuggyPrice;

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
                                     String PLU, int Price, int BuggyQty,
                                     String BuggyPLU, int BuggyPrice) {
        super();
        this.Version = Version;
        this.MemberId = MemberId;
        this.SlotStart = SlotStart;
        this.PLU = PLU;
        this.Price = Price;
        this.BuggyQty = BuggyQty;
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getIncludesBuggy() {
        return BuggyQty;
    }

    public void setIncludesBuggy(int BuggyQty) {
        this.BuggyQty = BuggyQty;
    }

    public String getBuggyPLU() {
        return BuggyPLU;
    }

    public void setBuggyPLU(String BuggyPLU) {
        this.BuggyPLU = BuggyPLU;
    }

    public int getBuggyPrice() {
        return BuggyPrice;
    }

    public void setBuggyPrice(int BuggyPrice) {
        this.BuggyPrice = BuggyPrice;
    }

}
