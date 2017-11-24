package com.mh.systems.dunstabledowns.ui.adapter.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.systems.dunstabledowns.R;
import com.mh.systems.dunstabledowns.ui.activites.FinanceDetailWebActivity;
import com.mh.systems.dunstabledowns.ui.activites.YourAccountActivity;
import com.mh.systems.dunstabledowns.web.models.finance.FinanceFilter;

import java.util.ArrayList;


/**
 * Created by karan Nassa on 22/05/17.
 */
public class FinanceRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FinanceFilter> dataSet;
    Context mContext;
    Typeface tfRobotoRegular, tfSfUiDisplayBold;

    public static class DateTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        RelativeLayout rlFinanceTitleGroup;

        public DateTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.tvDateOfTrans);
            this.rlFinanceTitleGroup = (RelativeLayout) itemView.findViewById(R.id.rlFinanceTitleGroup);
        }

    }

    private class RawTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTransTime, tvTransTitle, tvAmountStr, tvBalanceStr;
        TextView tvDiscountTime, tvDiscountTitle, tvDiscountAmtStr, tvDiscountBalStr;
        LinearLayout llFinanceView;
        LinearLayout llDiscountGroup;

        private RawTypeViewHolder(View itemView) {
            super(itemView);

            this.tvTransTime = (TextView) itemView.findViewById(R.id.tvTransTime);
            this.tvTransTitle = (TextView) itemView.findViewById(R.id.tvTransTitle);
            this.tvAmountStr = (TextView) itemView.findViewById(R.id.tvAmountStr);
            this.tvBalanceStr = (TextView) itemView.findViewById(R.id.tvBalanceStr);

            this.tvDiscountTime = (TextView) itemView.findViewById(R.id.tvDiscountTime);
            this.tvDiscountTitle = (TextView) itemView.findViewById(R.id.tvDiscountTitle);
            this.tvDiscountAmtStr = (TextView) itemView.findViewById(R.id.tvDiscountAmtStr);
            this.tvDiscountBalStr = (TextView) itemView.findViewById(R.id.tvDiscountBalStr);

            this.llFinanceView = (LinearLayout) itemView.findViewById(R.id.llFinanceView);
            this.llDiscountGroup = (LinearLayout) itemView.findViewById(R.id.llDiscountGroup);

            llFinanceView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (dataSet.get(getAdapterPosition()).transactionListData.getTransactionId() != 0) {
                Intent intent = new Intent(mContext, FinanceDetailWebActivity.class);
                intent.putExtra("IsTopup", dataSet.get(getAdapterPosition()).transactionListData.getIsTopup());
                intent.putExtra("iTransactionId", dataSet.get(getAdapterPosition()).transactionListData.getTransactionId());
                intent.putExtra("strMemberId", ((YourAccountActivity) mContext).getMemberId());
                intent.putExtra("titleOfScreen", dataSet.get(getAdapterPosition()).transactionListData.getTitle());
                mContext.startActivity(intent);
            }else{
                ((YourAccountActivity)mContext).showAlertMessage(mContext.getResources().getString(R.string.error_no_transaction));
            }
        }
    }

    public FinanceRecycleAdapter(ArrayList<FinanceFilter> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        tfSfUiDisplayBold = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Bold.otf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case FinanceFilter.TYPE_DATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_finance_title, parent, false);
                return new DateTypeViewHolder(view);
            case FinanceFilter.TYPE_DATA:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_finance_data, parent, false);
                return new RawTypeViewHolder(view);
        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return FinanceFilter.TYPE_DATE;
            case 1:
                return FinanceFilter.TYPE_DATA;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        FinanceFilter object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case FinanceFilter.TYPE_DATE:
                    setTitleTypeFace(holder);
                    ((DateTypeViewHolder) holder).txtType.setText(object.text);
                    ((DateTypeViewHolder) holder).rlFinanceTitleGroup.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorF8F7F6));

                    break;
                case FinanceFilter.TYPE_DATA:
                    setRawTypeFace(holder);
                    RawTypeViewHolder rawViewHolder = (RawTypeViewHolder) holder;

                    rawViewHolder.tvTransTime.setText(object.transactionListData.getTimeStr());
                    rawViewHolder.tvTransTitle.setText(object.transactionListData.getTitle());
                    rawViewHolder.tvAmountStr.setText(object.transactionListData.getAmountStr());
                    rawViewHolder.tvBalanceStr.setText(object.transactionListData.getBalanceStr());

                    if (object.transactionListData.getDiscountTitle().length() > 0) {
                        rawViewHolder.llDiscountGroup.setVisibility(View.VISIBLE);
                        rawViewHolder.tvDiscountTime.setText(object.transactionListData.getTimeStr());
                        rawViewHolder.tvDiscountTitle.setText(object.transactionListData.getDiscountTitle());
                        rawViewHolder.tvDiscountAmtStr.setText(object.transactionListData.getDiscountAmountStr());
                        rawViewHolder.tvDiscountBalStr.setText(object.transactionListData.getDiscountBalanceStr());
                    } else {
                        rawViewHolder.llDiscountGroup.setVisibility(View.GONE);
                    }

//                    if (object.transactionListData.getIsTopup()) {
//                        rawViewHolder.llFinanceView.setBackgroundResource(R.drawable.button_corner_shape_7ed321);
//                    } else {
//                        rawViewHolder.llFinanceView.setBackgroundResource(R.drawable.button_corner_shape_c41615);
//                    }
                    break;
            }
        }

    }

    private void setTitleTypeFace(RecyclerView.ViewHolder holder) {
        DateTypeViewHolder viewHolder = (DateTypeViewHolder) holder;
        viewHolder.txtType.setTypeface(tfSfUiDisplayBold, Typeface.NORMAL);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * Implements a method to set typeface.
     *
     * @param viewHolder :  Instance of holder to access views
     */
    private void setRawTypeFace(RecyclerView.ViewHolder rawViewHolder) {
        RawTypeViewHolder viewHolder = (RawTypeViewHolder) rawViewHolder;
        viewHolder.tvTransTime.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvTransTitle.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvAmountStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvBalanceStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);

        viewHolder.tvDiscountTime.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvDiscountTitle.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvDiscountAmtStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvDiscountBalStr.setTypeface(tfRobotoRegular, Typeface.NORMAL);
    }

}
