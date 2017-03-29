package com.mh.systems.halesworth.web.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 05-07-2016.
 */
public class FinanceData {
    @SerializedName("TransactionList")
    @Expose
    private List<TransactionListData> TransactionList = new ArrayList<TransactionListData>();
    @SerializedName("ClosingBalance")
    @Expose
    private String ClosingBalance;

    /**
     * @return The TransactionList
     */
    public List<TransactionListData> getTransactionList() {
        return TransactionList;
    }

    /**
     * @param TransactionList The TransactionList
     */
    public void setTransactionList(List<TransactionListData> TransactionList) {
        this.TransactionList = TransactionList;
    }

    /**
     * @return The ClosingBalance
     */
    public String getClosingBalance() {
        return ClosingBalance;
    }

    /**
     * @param ClosingBalance The ClosingBalance
     */
    public void setClosingBalance(String ClosingBalance) {
        this.ClosingBalance = ClosingBalance;
    }

}