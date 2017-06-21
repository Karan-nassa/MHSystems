package com.mh.systems.corrstown.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mh.systems.corrstown.R;
import com.mh.systems.corrstown.web.models.TransactionListData;

import java.util.ArrayList;

/**
 * Created by  karan@ucreate.co.in to used to create adapter
 * to display FINANCE list items on 30/6/2016.
 */
public class FinanceAdapter extends BaseAdapter {

    Activity context;
    LayoutInflater inflater = null;
    ArrayList<TransactionListData> transactionListDataArrayList;

    /**
     * Declares the Typeface here for custom font style.
     */
    Typeface tfRobotoRegular;

    /**
     * Transaction list Adapter constructor to initialize all instances.
     *
     * @param context:             : To hold context.
     * @param transactionListDataArrayList : Used for Finance array data.
     */
    public FinanceAdapter(Activity context, ArrayList<TransactionListData> transactionListDataArrayList) {

        this.context = context;
        this.transactionListDataArrayList = transactionListDataArrayList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    /**
     * @return Transaction list array size.
     */
    @Override
    public int getCount() {
        return transactionListDataArrayList.size();
    }

    /**
     * @return Transaction list item position
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * @return Transaction list item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return View after create Transaction list row
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        View_Holder viewHolder;

        rowView = inflater.inflate(R.layout.list_item_finance_row, parent, false);

        viewHolder = new View_Holder();
        viewHolder.tvTransTitle = (TextView) rowView.findViewById(R.id.tvTransTitle);
        viewHolder.tvAmountStr = (TextView) rowView.findViewById(R.id.tvAmountStr);
        viewHolder.tvTimeOfTrans = (TextView) rowView.findViewById(R.id.tvTimeOfTrans);

        rowView.setTag(viewHolder);
        viewHolder = (View_Holder) rowView.getTag();

        //Set Font Style Typeface
        setEventTypeFace(viewHolder);


        viewHolder.tvTransTitle.setText(transactionListDataArrayList.get(position).getTitle());
        viewHolder.tvTimeOfTrans.setText(transactionListDataArrayList.get(position).getDateStr() +" "+ transactionListDataArrayList.get(position).getTimeStr());
        viewHolder.tvAmountStr.setText(transactionListDataArrayList.get(position).getAmountStr());

        if(transactionListDataArrayList.get(position).getIsTopup()){
            viewHolder.tvAmountStr.setTextColor(Color.parseColor("#7ED321"));
        }else{
            viewHolder.tvAmountStr.setTextColor(Color.parseColor("#EF8176"));
        }

        return rowView;
    }

    /**
     * Implements a method to set typeface.
     */
    public void setEventTypeFace(View_Holder viewHolder) {
        viewHolder.tvTransTitle.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvAmountStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvTimeOfTrans.setTypeface(tfRobotoRegular, Typeface.NORMAL);
    }

    /**
     * View_Holder to create COURSE DIARY row to
     * display EVENTS of COURSE DIARY.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvTransTitle, tvAmountStr, tvTimeOfTrans;
    }
}