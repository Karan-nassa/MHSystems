
package com.ucreate.mhsystems.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InvoiceBillParts {

    @SerializedName("billPartses")
    @Expose
    private List<com.ucreate.mhsystems.models.BillParts> billPartses = new ArrayList<com.ucreate.mhsystems.models.BillParts>();

    /**
     * 
     * @return
     *     The billPartses
     */
    public List<com.ucreate.mhsystems.models.BillParts> getBillPartses() {
        return billPartses;
    }

    /**
     * 
     * @param billPartses
     *     The billPartses
     */
    public void setBillPartses(List<com.ucreate.mhsystems.models.BillParts> billPartses) {
        this.billPartses = billPartses;
    }

}
