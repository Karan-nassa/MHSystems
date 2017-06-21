
package com.mh.systems.corrstown.web.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InvoiceBillParts {

    @SerializedName("billPartses")
    @Expose
    private List<BillParts> billPartses = new ArrayList<BillParts>();

    /**
     * 
     * @return
     *     The billPartses
     */
    public List<BillParts> getBillPartses() {
        return billPartses;
    }

    /**
     * 
     * @param billPartses
     *     The billPartses
     */
    public void setBillPartses(List<BillParts> billPartses) {
        this.billPartses = billPartses;
    }

}
