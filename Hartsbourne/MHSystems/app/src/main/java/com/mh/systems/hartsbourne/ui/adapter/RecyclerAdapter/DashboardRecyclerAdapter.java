package com.mh.systems.hartsbourne.ui.adapter.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.ui.activites.DashboardActivity;

import java.util.ArrayList;


/**
 * Created by  karan@ucreate.co.in to create Dashboard Grid
 * data.
 */
public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.ViewHolder> {


    Context context;
    private LayoutInflater inflater = null;

    private ViewHolder mInstanceOfClubNews = null;

    private final int POSITION_NORMAL = 0;
    private final int POSITION_HANDICAP = 1;

    String hCapExactStr;
    int iHandicapPosition;

    Typeface tfButtlerMedium, tfRobotoMedium;

    ArrayList<DashboardActivity.DashboardItems> dashboardItemsArrayList;

    // The default constructor to receive titles,icons and context from DashboardActivity.
    public DashboardRecyclerAdapter(DashboardActivity context, ArrayList<DashboardActivity.DashboardItems> dashboardItemsArrayList, int iHandicapPosition, String hCapExactStr) {

        this.context = context;
        this.dashboardItemsArrayList = dashboardItemsArrayList;
        this.iHandicapPosition = iHandicapPosition;
        this.hCapExactStr = hCapExactStr;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfButtlerMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Butler_Medium.otf");
        tfRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    /**
     * This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     * Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (viewType) {
            case POSITION_HANDICAP:
                View itemLayout = layoutInflater.inflate(R.layout.item_grid_row_text, null);
                return new ViewHolder(itemLayout, viewType, context);

            case POSITION_NORMAL:
                View itemLayout2 = layoutInflater.inflate(R.layout.item_grid_row_icon, null);
                return new ViewHolder(itemLayout2, viewType, context);

            default:
                return null;
        }

       /* View itemLayout = layoutInflater.inflate(R.layout.item_grid_row_text, null);
        return new ViewHolder(itemLayout, viewType, context);*/
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Set View data according position.
        holder.tvGridTitle.setText(dashboardItemsArrayList.get(position).getStrTitleOfGrid());
        holder.ivGridLogo.setImageResource(dashboardItemsArrayList.get(position).getiGridIcon());

        if (position == iHandicapPosition) {
            holder.tvHCapExactStr.setVisibility(View.VISIBLE);
            if(hCapExactStr.length() == 0){
                holder.tvHCapExactStr.setText("N/A");
            }else {
                holder.tvHCapExactStr.setText(hCapExactStr);
            }
        }

        /**
         * Assign instance of club news holder on dashboard
         * that will require to update unread news count.
         */
        if (dashboardItemsArrayList.get(position).getStrTitleOfGrid().equals("Club News")) {
            mInstanceOfClubNews = holder;
        }

       /* if (position == 4) {
            holder.flBadgerGroup.setVisibility(View.VISIBLE);
        }*/
    }

    /**
     * It returns the total no. of items . We +1 count to include the header view.
     * So , it the total count is 5 , the method returns 6.
     * This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */
    @Override
    public int getItemCount() {
        return dashboardItemsArrayList.size();
    }

    /**
     * This methods returns 0 if the position of the item is '0'.
     * If the position is zero its a header view and if its anything else
     * its a list_item_alphabet_row with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        return (position == iHandicapPosition) ? POSITION_HANDICAP : POSITION_NORMAL;
    }

    /**
     * Create custom view and initialize the font style.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvHCapExactStr;
        TextView tvGridTitle;
        TextView tvBadgerCount;
        ImageView ivGridLogo;
        RelativeLayout rlGridMenuItem;
        FrameLayout flBadgerGroup;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            tvGridTitle = (TextView) itemView.findViewById(R.id.tvGridTitle);
            ivGridLogo = (ImageView) itemView.findViewById(R.id.ivGridLogo);
            rlGridMenuItem = (RelativeLayout) itemView.findViewById(R.id.rlGridMenuItem);

            tvBadgerCount = (TextView) itemView.findViewById(R.id.tvBadgerCount);

            flBadgerGroup = (FrameLayout) itemView.findViewById(R.id.flBadgerGroup);
            tvGridTitle.setTypeface(tfButtlerMedium);

            if (itemType == POSITION_HANDICAP) {
                tvHCapExactStr = (TextView) itemView.findViewById(R.id.tvHCapExactStr);
                tvHCapExactStr.setTypeface(tfRobotoMedium);
            }

            drawerItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int iPosition = 0;

            Class<?> destClassName = null;
            if (dashboardItemsArrayList.get(getAdapterPosition()).getStrTagOfGrid() != null) {
                try {
                    destClassName = Class.forName(dashboardItemsArrayList.get(getAdapterPosition()).getStrTagOfGrid());

                    if (dashboardItemsArrayList.get(getAdapterPosition()).getiGridIcon() == R.mipmap.ic_handicap_chart) {
                        iPosition = 1;
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, destClassName);
                intent.putExtra("iTabPosition", iPosition);
                context.startActivity(intent);
            }

            /*Intent intent = null;

            switch (getAdapterPosition()) {
                case 0:
                    intent = new Intent(context, YourAccountActivity.class);
                    intent.putExtra("iTabPosition", 1);
                    break;
                case 1:
                    intent = new Intent(context, CourseDiaryActivity.class);
                    break;
                case 2:
                    intent = new Intent(context, CompetitionsActivity.class);
                    break;
                case 3:
                    intent = new Intent(context, MembersActivity.class);
                    break;

                case 4:
                    intent = new Intent(context, ClubNewsActivity.class);
                    break;

                case 5:
                    intent = new Intent(context, YourAccountActivity.class);
                    intent.putExtra("iTabPosition", 0);
                    break;
            }

            //Check if intent not NULL then navigate to that selected screen.
            if (intent != null) {
                context.startActivity(intent);
                intent = null;
            }*/
        }
    }

    /**
     * Implements this method to update the Club News
     * badger icon on dashboard.
     */
    public void updateBadgerCount(int iUnreadCount) {
        mInstanceOfClubNews.flBadgerGroup.setVisibility(View.VISIBLE);
        mInstanceOfClubNews.tvBadgerCount.setText(("" + iUnreadCount));
    }
}
