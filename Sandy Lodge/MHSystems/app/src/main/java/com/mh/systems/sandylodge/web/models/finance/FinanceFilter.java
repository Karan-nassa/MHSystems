package com.mh.systems.sandylodge.web.models.finance;

import com.mh.systems.sandylodge.web.models.TransactionListData;

/**
 * Created by Karan Nassa on 22-05-2017.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class FinanceFilter {
    public static final int TYPE_DATE = 0;
    public static final int TYPE_DATA = 1;

    public int type;
    public TransactionListData transactionListData;
    public String text;

    public FinanceFilter(int type, String text, TransactionListData transactionListData)
    {
        this.type=type;
        this.text=text;
        this.transactionListData=transactionListData;
    }
}
