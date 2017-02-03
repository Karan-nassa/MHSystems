
package com.mh.systems.chesterLeStreet.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InvoiceBillParts {

    @SerializedName("billPartses")
    @Expose
    private List<com.mh.systems.chesterLeStreet.models.BillParts> billPartses = new ArrayList<com.mh.systems.chesterLeStreet.models.BillParts>();

    /**
     * 
     * @return
     *     The billPartses
     */
    public List<com.mh.systems.chesterLeStreet.models.BillParts> getBillPartses() {
        return billPartses;
    }

    /**
     * 
     * @param billPartses
     *     The billPartses
     */
    public void setBillPartses(List<com.mh.systems.chesterLeStreet.models.BillParts> billPartses) {
        this.billPartses = billPartses;
    }

}
