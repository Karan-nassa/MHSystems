
package com.ucreate.mhsystems.utils.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
        InvoiceNo = InvoiceNo;
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
