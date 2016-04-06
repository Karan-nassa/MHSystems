
package com.ucreate.mhsystems.utils.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentBill {

    @SerializedName("InvoiceDate")
    @Expose
    private String InvoiceDate;

    @SerializedName("InvoiceNo")
    @Expose
    private String InvoiceNo;

    @SerializedName("TotalPayable")
    @Expose
    private int TotalPayable;

    @SerializedName("InvoiceStatusStr")
    @Expose
    private String InvoiceStatusStr;

    @SerializedName("BillParts")
    @Expose
    private List<BillParts> BillParts;

    /**
     *
     * @return
     *     The BillParts
     */
    public List<BillParts> getBillParts() {
        return BillParts;
    }

    /**
     *
     * @param billParts
     *     The BillParts
     */
    public void setBillParts(List<BillParts> billParts) {
        BillParts = billParts;
    }

    /**
     *
     * @return
     *     The InvoiceStatusStr
     */
    public String getInvoiceStatusStr() {
        return InvoiceStatusStr;
    }

    /**
     *
     * @param invoiceStatusStr
     *     The invoiceStatusStr
     */
    public void setInvoiceStatusStr(String invoiceStatusStr) {
        InvoiceStatusStr = invoiceStatusStr;
    }

    /**
     *
     * @return
     *     The TotalPayable
     */
    public int getTotalPayable() {
        return TotalPayable;
    }

    /**
     *
     * @param totalPayable
     *     The TotalPayable
     */
    public void setTotalPayable(int totalPayable) {
        TotalPayable = totalPayable;
    }

    /**
     *
     * @return
     *     The InvoiceNo
     */
    public String getInvoiceNo() {
        return InvoiceNo;
    }

    /**
     *
     * @param InvoiceNo
     *     The InvoiceNo
     */
    public void setInvoiceNo(String InvoiceNo) {
        this.InvoiceNo = InvoiceNo;
    }



    /**
     * 
     * @return
     *     The InvoiceDate
     */
    public String getInvoiceDate() {
        return InvoiceDate;
    }

    /**
     * 
     * @param InvoiceDate
     *     The InvoiceDate
     */
    public void setInvoiceDate(String InvoiceDate) {
        this.InvoiceDate = InvoiceDate;
    }

}