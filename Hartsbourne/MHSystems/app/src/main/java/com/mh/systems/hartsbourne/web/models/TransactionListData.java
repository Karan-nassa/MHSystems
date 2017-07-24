
package com.mh.systems.hartsbourne.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TransactionListData {

    @SerializedName("CardStr")
    @Expose
    private String CardStr;
    @SerializedName("TimeStr")
    @Expose
    private String TimeStr;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("AmountStr")
    @Expose
    private String AmountStr;
    @SerializedName("BalanceStr")
    @Expose
    private String BalanceStr;
    @SerializedName("DiscountTitle")
    @Expose
    private String DiscountTitle;
    @SerializedName("DiscountAmountStr")
    @Expose
    private String DiscountAmountStr;
    @SerializedName("DiscountBalanceStr")
    @Expose

    private String DiscountBalanceStr;
    @SerializedName("DateStr")
    @Expose
    private String DateStr;
    @SerializedName("IsTopup")
    @Expose
    private Boolean IsTopup;
    @SerializedName("TransactionId")
    @Expose
    private Integer TransactionId;

    /**
     * 
     * @return
     *     The CardStr
     */
    public String getCardStr() {
        return CardStr;
    }

    /**
     * 
     * @param CardStr
     *     The CardStr
     */
    public void setCardStr(String CardStr) {
        this.CardStr = CardStr;
    }

    /**
     * 
     * @return
     *     The TimeStr
     */
    public String getTimeStr() {
        return TimeStr;
    }

    /**
     * 
     * @param TimeStr
     *     The TimeStr
     */
    public void setTimeStr(String TimeStr) {
        this.TimeStr = TimeStr;
    }

    /**
     * 
     * @return
     *     The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * 
     * @param Title
     *     The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * 
     * @return
     *     The AmountStr
     */
    public String getAmountStr() {
        return AmountStr;
    }

    /**
     * 
     * @param AmountStr
     *     The AmountStr
     */
    public void setAmountStr(String AmountStr) {
        this.AmountStr = AmountStr;
    }

    /**
     * 
     * @return
     *     The DiscountTitle
     */
    public String getDiscountTitle() {
        return DiscountTitle;
    }

    /**
     * 
     * @param DiscountTitle
     *     The DiscountTitle
     */
    public void setDiscountTitle(String DiscountTitle) {
        this.DiscountTitle = DiscountTitle;
    }

    /**
     * 
     * @return
     *     The DiscountAmountStr
     */
    public String getDiscountAmountStr() {
        return DiscountAmountStr;
    }

    /**
     * 
     * @param DiscountAmountStr
     *     The DiscountAmountStr
     */
    public void setDiscountAmountStr(String DiscountAmountStr) {
        this.DiscountAmountStr = DiscountAmountStr;
    }

    /**
     * 
     * @return
     *     The DateStr
     */
    public String getDateStr() {
        return DateStr;
    }

    /**
     * 
     * @param DateStr
     *     The DateStr
     */
    public void setDateStr(String DateStr) {
        this.DateStr = DateStr;
    }

    /**
     * 
     * @return
     *     The IsTopup
     */
    public Boolean getIsTopup() {
        return IsTopup;
    }

    /**
     * 
     * @param IsTopup
     *     The IsTopup
     */
    public void setIsTopup(Boolean IsTopup) {
        this.IsTopup = IsTopup;
    }

    /**
     * 
     * @return
     *     The TransactionId
     */
    public Integer getTransactionId() {
        return TransactionId;
    }

    /**
     * 
     * @param TransactionId
     *     The TransactionId
     */
    public void setTransactionId(Integer TransactionId) {
        this.TransactionId = TransactionId;
    }

    /**
     *
     * @return
     *     The BalanceStr
     */
    public String getBalanceStr() {
        return BalanceStr;
    }

    /**
     *
     * @param BalanceStr
     *     The BalanceStr
     */
    public void setBalanceStr(String balanceStr) {
        BalanceStr = balanceStr;
    }

    /**
     *
     * @return
     *     The DiscountBalanceStr
     */
    public String getDiscountBalanceStr() {
        return DiscountBalanceStr;
    }

    /**
     *
     * @param DiscountBalanceStr
     *     The DiscountBalanceStr
     */
    public void setDiscountBalanceStr(String discountBalanceStr) {
        DiscountBalanceStr = discountBalanceStr;
    }
}
