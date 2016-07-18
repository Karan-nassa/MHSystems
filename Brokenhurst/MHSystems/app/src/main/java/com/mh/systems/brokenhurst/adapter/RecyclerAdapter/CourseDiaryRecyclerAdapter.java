package com.mh.systems.brokenhurst.adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.models.CourseDiaryData;

import java.util.ArrayList;


/**
 * Created by  karan@mh.co.in to create Navigation
 * drawer for FriendsDetailRecyclerAdapter on 15/2/2016.
 */
public class CourseDiaryRecyclerAdapter extends RecyclerView.Adapter<CourseDiaryRecyclerAdapter.ViewHolder>  {

    ArrayList<CourseDiaryData> CourseDiaryData;
    Context context;
    String strLastDate = "";

    // The default constructor to receive titles,icons and context from DashboardActivity.
    public CourseDiaryRecyclerAdapter(Context context, ArrayList<CourseDiaryData> CourseDiaryData) {

        this.CourseDiaryData = CourseDiaryData;
        this.context = context;
    }

    /**
     * This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     * Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public CourseDiaryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemLayout = layoutInflater.inflate(R.layout.list_item_course_diary, null);
        return new ViewHolder(itemLayout, viewType, context);
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(CourseDiaryRecyclerAdapter.ViewHolder holder, int position) {

        /**
         *  Set Course Diary events on each view.
         */
        holder.tvTitleOfEvent.setText(CourseDiaryData.get(position).getTitle());
        holder.tvCategoryOfEvent.setText(CourseDiaryData.get(position).getCategory());
        holder.tvTimeOfEvent.setText(CourseDiaryData.get(position).getStartTime() + " - " + CourseDiaryData.get(position).getEndTime());
        holder.tvDescOfEvent.setText(CourseDiaryData.get(position).getDesc());
        holder.tvDateOfEvent.setText(CourseDiaryData.get(position).getCourseEventDate());
        holder.tvDayOfEvent.setText(CourseDiaryData.get(position).getDayName());

    }

    /**
     * @param strCourseEventDate <br>
     *                           Implements a method to return the format the day of
     *                           event.
     *                           <p/>
     *                           Exapmle: 2016-03-04T00:00:00
     * @Return : 04
     */
    private String formatDateOfEvent(String strCourseEventDate) {

        String strEventDate = strCourseEventDate.substring(strCourseEventDate.lastIndexOf("-") + 1, strCourseEventDate.lastIndexOf("T"));

        return strEventDate;
    }

    /**
     * @param strDayName <br>
     *                   Implements a method to return the format the day of
     *                   event.
     *                   <p/>
     *                   Exapmle: NAME OF DAY : Friday
     * @Return : Fri
     */
    private String formatDayOfEvent(String strDayName) {
        return (strDayName.substring(0, 3));
    }

    /**
     * It returns the total no. of items . We +1 count to include the header view.
     * So , it the total count is 5 , the method returns 6.
     * This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */

    @Override
    public int getItemCount() {
        return CourseDiaryData.size();
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
     * Its a inner class to NavDrawerRecyclerAdapter Class.
     * This ViewHolder class implements View.OnClickListener to handle click events.
     * If the itemType==1 ; it implies that the view is a single list_item_alphabet_row with TextView and ImageView.
     * This ViewHolder describes an item view with respect to its place within the RecyclerView.
     * For every item there is a ViewHolder associated with it .
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvDateOfEvent, tvDayOfEvent, tvTimeOfEvent;
        TextView tvCategoryOfEvent, tvTitleOfEvent, tvDescOfEvent;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

         //   tvDateOfEvent = (TextView) itemView.findViewById(R.id.tvDateOfEvent);
          //  tvDayOfEvent = (TextView) itemView.findViewById(R.id.tvDayOfEvent);
            tvTimeOfEvent = (TextView) itemView.findViewById(R.id.tvTimeOfEvent);
          //  tvTitleOfEvent = (TextView) itemView.findViewById(R.id.tvTitleOfEvent);
           // tvDescOfEvent = (TextView) itemView.findViewById(R.id.tvDescOfEvent);
        }
    }
}
