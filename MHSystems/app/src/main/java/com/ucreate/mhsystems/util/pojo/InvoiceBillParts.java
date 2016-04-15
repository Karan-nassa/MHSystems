
package com.ucreate.mhsystems.util.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InvoiceBillParts {

    @SerializedName("billPartses")
    @Expose
    private List<com.ucreate.mhsystems.util.pojo.BillParts> billPartses = new ArrayList<com.ucreate.mhsystems.util.pojo.BillParts>();

    /**
     * 
     * @return
     *     The billPartses
     */
    public List<com.ucreate.mhsystems.util.pojo.BillParts> getBillPartses() {
        return billPartses;
    }

    /**
     * 
     * @param billPartses
     *     The billPartses
     */
    public void setBillPartses(List<com.ucreate.mhsystems.util.pojo.BillParts> billPartses) {
        this.billPartses = billPartses;
    }

}
