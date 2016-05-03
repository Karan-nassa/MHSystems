
package com.ucreate.mhsystems.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyAccountData {

    @SerializedName("CurrentBills")
    @Expose
    private List<CurrentBill> CurrentBills = new ArrayList<CurrentBill>();
    @SerializedName("memBalance")
    @Expose
    private List<MemBalance> memBalance = new ArrayList<MemBalance>();

    /**
     * 
     * @return
     *     The CurrentBills
     */
    public List<CurrentBill> getCurrentBills() {
        return CurrentBills;
    }

    /**
     *
     * @param CurrentBills
     *     The CurrentBills
     */
    public void setCurrentBills(List<CurrentBill> CurrentBills) {
        this.CurrentBills = CurrentBills;
    }

    /**
     * 
     * @return
     *     The memBalance
     */
    public List<MemBalance> getMemBalance() {
        return memBalance;
    }

    /**
     * 
     * @param memBalance
     *     The memBalance
     */
    public void setMemBalance(List<MemBalance> memBalance) {
        this.memBalance = memBalance;
    }

}
