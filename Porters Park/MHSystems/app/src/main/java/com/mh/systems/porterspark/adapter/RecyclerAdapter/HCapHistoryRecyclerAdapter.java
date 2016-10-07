package com.mh.systems.porterspark.adapter.RecyclerAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mh.systems.porterspark.R;
import com.mh.systems.porterspark.models.HCapHistory.HCapHistoryData;

import java.util.ArrayList;


/**
 * Created by  karan@ucreate.co.in to create Handicap History
 * data.
 */
public class HCapHistoryRecyclerAdapter extends RecyclerView.Adapter<HCapHistoryRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<HCapHistoryData> handicapDataArrayList;

    Typeface tfRobotoMedium, tfRobotoTextFontBold, tfRobotoRegular;

    // The default constructor to receive titles,icons and context from DashboardActivity.
    public HCapHistoryRecyclerAdapter(Context context, ArrayList<HCapHistoryData> handicapDataArrayList) {

        this.handicapDataArrayList = handicapDataArrayList;
        this.context = context;

        tfRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        tfRobotoTextFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    /**
     * This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     * Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public HCapHistoryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemLayout = layoutInflater.inflate(R.layout.list_item_handicap_history, null);
        return new ViewHolder(itemLayout, viewType, context);
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(HCapHistoryRecyclerAdapter.ViewHolder holder, int position) {

        holder.tvDatePlayedStr.setText(handicapDataArrayList.get(position).getDatePlayedStr());
        holder.tvVenueOrAuthoriser.setText(handicapDataArrayList.get(position).getVenueOrAuthoriser());
        holder.tvCompetitionOrReason.setText(handicapDataArrayList.get(position).getCompetitionOrReason());
        holder.tvRoundNo.setText("R"+handicapDataArrayList.get(position).getRoundNoStr());

        holder.tvRoundNoStr.setText(handicapDataArrayList.get(position).getRoundNoStr());
        holder.tvConguCode.setText(handicapDataArrayList.get(position).getConguCode());
        holder.tvRoundCSSStr.setText(handicapDataArrayList.get(position).getRoundCSSStr());
        holder.tvAdjGrossScoreStr.setText(handicapDataArrayList.get(position).getAdjGrossScoreStr());
        holder.tvNdbAdjStr.setText(handicapDataArrayList.get(position).getNdbAdjStr());

        holder.tvGrossDiffStr.setText(handicapDataArrayList.get(position).getGrossDiffStr());
        holder.tvNettDiffStr.setText(handicapDataArrayList.get(position).getNettDiffStr());
        holder.tvHCapAdjStr.setText(handicapDataArrayList.get(position).getHCapAdjStr());
        holder.tvNewExactHCapOnlyStr.setText(handicapDataArrayList.get(position).getNewExactHCapOnlyStr());
        holder.tvNewPlayHCapOnlyStr.setText(handicapDataArrayList.get(position).getNewPlayHCapOnlyStr()
           + handicapDataArrayList.get(position).getHCapStatusStr());
    }

    /**
     * It returns the total no. of items . We +1 count to include the header view.
     * So , it the total count is 5 , the method returns 6.
     * This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */
    @Override
    public int getItemCount() {
        return handicapDataArrayList.size();
    }

    /**
     * This methods returns 0 if the position of the item is '0'.
     * If the position is zero its a header view and if its anything else
     * its a list_item_alphabet_row with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Create custom view and initialize the font style.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvDatePlayedStr, tvVenueOrAuthoriser, tvCompetitionOrReason, tvRoundNoStr, tvConguCode, tvRoundCSSStr, tvAdjGrossScoreStr, tvNdbAdjStr;
        TextView tvGrossDiffStr, tvNettDiffStr, tvHCapAdjStr, tvNewExactHCapOnlyStr, tvNewPlayHCapOnlyStr;
        TextView tvRoundNo, tvRd, tvType, tvCSS, tvScore, tvNDB;
        TextView tvGDiff, tvNDiff, tvHCap, tvNewExact, tvPlay;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            tvDatePlayedStr = (TextView) itemView.findViewById(R.id.tvDatePlayedStr);
            tvVenueOrAuthoriser = (TextView) itemView.findViewById(R.id.tvVenueOrAuthoriser);
            tvCompetitionOrReason = (TextView) itemView.findViewById(R.id.tvCompetitionOrReason);
            tvRoundNo = (TextView) itemView.findViewById(R.id.tvRoundNo);

            tvRd = (TextView) itemView.findViewById(R.id.tvRd);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvCSS = (TextView) itemView.findViewById(R.id.tvCSS);
            tvScore = (TextView) itemView.findViewById(R.id.tvScore);
            tvNDB = (TextView) itemView.findViewById(R.id.tvNDB);

            tvRoundNoStr = (TextView) itemView.findViewById(R.id.tvRoundNoStr);
            tvConguCode = (TextView) itemView.findViewById(R.id.tvConguCode);
            tvRoundCSSStr = (TextView) itemView.findViewById(R.id.tvRoundCSSStr);
            tvAdjGrossScoreStr = (TextView) itemView.findViewById(R.id.tvAdjGrossScoreStr);
            tvNdbAdjStr = (TextView) itemView.findViewById(R.id.tvNdbAdjStr);

            tvGDiff = (TextView) itemView.findViewById(R.id.tvGDiff);
            tvNDiff = (TextView) itemView.findViewById(R.id.tvNDiff);
            tvHCap = (TextView) itemView.findViewById(R.id.tvHCap);
            tvNewExact = (TextView) itemView.findViewById(R.id.tvNewExact);
            tvPlay = (TextView) itemView.findViewById(R.id.tvPlay);

            tvGrossDiffStr = (TextView) itemView.findViewById(R.id.tvGrossDiffStr);
            tvNettDiffStr = (TextView) itemView.findViewById(R.id.tvNettDiffStr);
            tvHCapAdjStr = (TextView) itemView.findViewById(R.id.tvHCapAdjStr);
            tvNewExactHCapOnlyStr = (TextView) itemView.findViewById(R.id.tvNewExactHCapOnlyStr);
            tvNewPlayHCapOnlyStr = (TextView) itemView.findViewById(R.id.tvNewPlayHCapOnlyStr);

            setFontTypeFace();
        }

        private void setFontTypeFace() {
            tvDatePlayedStr.setTypeface(tfRobotoMedium);
            tvVenueOrAuthoriser.setTypeface(tfRobotoMedium);
            tvCompetitionOrReason.setTypeface(tfRobotoMedium);
            tvRoundNo.setTypeface(tfRobotoMedium);

            tvRd.setTypeface(tfRobotoTextFontBold);
            tvType.setTypeface(tfRobotoTextFontBold);
            tvCSS.setTypeface(tfRobotoTextFontBold);
            tvScore.setTypeface(tfRobotoTextFontBold);
            tvNDB.setTypeface(tfRobotoTextFontBold);

            tvRoundNoStr.setTypeface(tfRobotoRegular);
            tvConguCode.setTypeface(tfRobotoTextFontBold);
            tvRoundCSSStr.setTypeface(tfRobotoRegular);
            tvAdjGrossScoreStr.setTypeface(tfRobotoTextFontBold);
            tvNdbAdjStr.setTypeface(tfRobotoRegular);

            tvGDiff.setTypeface(tfRobotoTextFontBold);
            tvNDiff.setTypeface(tfRobotoTextFontBold);
            tvHCap.setTypeface(tfRobotoTextFontBold);
            tvNewExact.setTypeface(tfRobotoTextFontBold);
            tvPlay.setTypeface(tfRobotoTextFontBold);

            tvGrossDiffStr.setTypeface(tfRobotoRegular);
            tvNettDiffStr.setTypeface(tfRobotoRegular);
            tvHCapAdjStr.setTypeface(tfRobotoRegular);
            tvNewExactHCapOnlyStr.setTypeface(tfRobotoTextFontBold);
            tvNewPlayHCapOnlyStr.setTypeface(tfRobotoRegular);
        }
    }
}
