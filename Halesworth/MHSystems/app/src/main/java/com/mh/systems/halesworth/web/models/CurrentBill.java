
package com.mh.systems.halesworth.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentBill {

//    @SerializedName("InvoiceDate")
//    @Expose
//    private String InvoiceDate;

    @SerializedName("strInvoiceDate")
    @Expose
    private String strInvoiceDate;

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
     *     The strInvoiceDate
     */
    public String getStrInvoiceDate() {
        return strInvoiceDate;
    }

    /**
     * 
     * @param strInvoiceDate
     *     The strInvoiceDate
     */
    public void setStrInvoiceDate(String strInvoiceDate) {
        this.strInvoiceDate = strInvoiceDate;
    }

}
