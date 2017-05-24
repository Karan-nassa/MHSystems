package com.mh.systems.sunningdale.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.web.models.TransactionListData;

import java.util.ArrayList;

/**
 * Created by  karan@mh.co.in to used to create adapter
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

        rowView = inflater.inflate(R.layout.list_item_finance_data, parent, false);

        viewHolder = new View_Holder();
        viewHolder.tvTransTime = (TextView) rowView.findViewById(R.id.tvTransTime);
        viewHolder.tvTransTitle = (TextView) rowView.findViewById(R.id.tvTransTitle);
        viewHolder.tvAmountStr = (TextView) rowView.findViewById(R.id.tvAmountStr);
        viewHolder.tvBalanceStr = (TextView)rowView.findViewById(R.id.tvBalanceStr);

        viewHolder.tvDiscountTime = (TextView) rowView.findViewById(R.id.tvDiscountTime);
        viewHolder.tvDiscountTitle = (TextView) rowView.findViewById(R.id.tvDiscountTitle);
        viewHolder.tvDiscountAmtStr = (TextView) rowView.findViewById(R.id.tvDiscountAmtStr);
        viewHolder.tvDiscountBalStr = (TextView) rowView.findViewById(R.id.tvDiscountBalStr);

        viewHolder.llFinanceView = (LinearLayout) rowView.findViewById(R.id.llFinanceView);
        viewHolder.llDiscountGroup = (LinearLayout) rowView.findViewById(R.id.llDiscountGroup);
       // viewHolder.flDiscountGroup = (FrameLayout) rowView.findViewById(R.id.flDiscountGroup);

        rowView.setTag(viewHolder);
        viewHolder = (View_Holder) rowView.getTag();

        //Set Font Style Typeface
        setEventTypeFace(viewHolder);

        if(transactionListDataArrayList.get(position).getDiscountTitle().length()>0){
            viewHolder.llDiscountGroup.setVisibility(View.VISIBLE);
            viewHolder.tvDiscountTitle.setText(transactionListDataArrayList.get(position).getDiscountTitle());
            viewHolder.tvDiscountAmtStr.setText(transactionListDataArrayList.get(position).getDiscountAmountStr());
        }else{
            viewHolder.llDiscountGroup.setVisibility(View.GONE);
        }

        viewHolder.tvTransTime.setText(transactionListDataArrayList.get(position).getTimeStr());
        viewHolder.tvTransTitle.setText(transactionListDataArrayList.get(position).getTitle());
        //viewHolder.tvTransTime.setText(transactionListDataArrayList.get(position).getDateStr() +" "+ transactionListDataArrayList.get(position).getTimeStr());
        viewHolder.tvAmountStr.setText(transactionListDataArrayList.get(position).getAmountStr());

        if(transactionListDataArrayList.get(position).getIsTopup()){
            viewHolder.llFinanceView.setBackgroundResource(R.drawable.button_corner_shape_7ed321);
        }else{
            viewHolder.llFinanceView.setBackgroundResource(R.drawable.button_corner_shape_c41615);
        }

        return rowView;
    }

    /**
     * Implements a method to set typeface.
     */
    public void setEventTypeFace(View_Holder viewHolder) {
        viewHolder.tvTransTime.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvTransTitle.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvAmountStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvBalanceStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);

        viewHolder.tvDiscountTime.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvDiscountTitle.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvDiscountAmtStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvDiscountBalStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
    }

    /**
     * View_Holder to create COURSE DIARY row to
     * display EVENTS of COURSE DIARY.
     */
    class View_Holder {
//        /**
//         * Text Row VIEW INSTANCES DECLARATION
//         */
//        TextView tvTransTitle, tvAmountStr, tvTimeOfTrans;
//        TextView tvDiscountTitle, tvDiscountAmt;
//        FrameLayout flDiscountGroup;

        TextView tvTransTime, tvTransTitle, tvAmountStr, tvBalanceStr;
        TextView tvDiscountTime, tvDiscountTitle, tvDiscountAmtStr, tvDiscountBalStr;
        LinearLayout llFinanceView;
        LinearLayout llDiscountGroup;
    }
}