
package com.ucreate.mhsystems.util.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {

    @SerializedName("ItemNo")
    @Expose
    private Integer ItemNo;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("BilledFrom")
    @Expose
    private String BilledFrom;
    @SerializedName("BilledTo")
    @Expose
    private String BilledTo;
    @SerializedName("Tax")
    @Expose
    private Integer Tax;
    @SerializedName("ValueX")
    @Expose
    private Integer ValueX;
    @SerializedName("BillingPeriodStr")
    @Expose
    private String BillingPeriodStr;

    /**
     * 
     * @return
     *     The ItemNo
     */
    public Integer getItemNo() {
        return ItemNo;
    }

    /**
     * 
     * @param ItemNo
     *     The ItemNo
     */
    public void setItemNo(Integer ItemNo) {
        this.ItemNo = ItemNo;
    }

    /**
     * 
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * 
     * @return
     *     The BilledFrom
     */
    public String getBilledFrom() {
        return BilledFrom;
    }

    /**
     * 
     * @param BilledFrom
     *     The BilledFrom
     */
    public void setBilledFrom(String BilledFrom) {
        this.BilledFrom = BilledFrom;
    }

    /**
     * 
     * @return
     *     The BilledTo
     */
    public String getBilledTo() {
        return BilledTo;
    }

    /**
     * 
     * @param BilledTo
     *     The BilledTo
     */
    public void setBilledTo(String BilledTo) {
        this.BilledTo = BilledTo;
    }

    /**
     * 
     * @return
     *     The Tax
     */
    public Integer getTax() {
        return Tax;
    }

    /**
     * 
     * @param Tax
     *     The Tax
     */
    public void setTax(Integer Tax) {
        this.Tax = Tax;
    }

    /**
     * 
     * @return
     *     The ValueX
     */
    public Integer getValueX() {
        return ValueX;
    }

    /**
     * 
     * @param ValueX
     *     The ValueX
     */
    public void setValueX(Integer ValueX) {
        this.ValueX = ValueX;
    }

    /**
     * 
     * @return
     *     The BillingPeriodStr
     */
    public String getBillingPeriodStr() {
        return BillingPeriodStr;
    }

    /**
     * 
     * @param BillingPeriodStr
     *     The BillingPeriodStr
     */
    public void setBillingPeriodStr(String BillingPeriodStr) {
        this.BillingPeriodStr = BillingPeriodStr;
    }

}
