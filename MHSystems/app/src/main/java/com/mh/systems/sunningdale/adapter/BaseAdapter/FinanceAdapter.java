package com.mh.systems.sunningdale.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mh.systems.sunningdale.R;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by  karan@mh.co.in to used to create adapter
 * to display TRANSACTIONS list items on 30/6/2016.
 */
public class FinanceAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    public ArrayList<String> CourseDiaryData = new ArrayList<>();

    Activity context;
    LayoutInflater inflater = null;

    /**
     * Declares the Typeface here for custom font style.
     */
    Typeface tfRobotoRegular;

    /**
     * Transaction list Adapter constructor to initialize all instances.
     *
     * @param context:        : To hold context.
     * @param stringArrayList : Used for Videos data.
     */
    public FinanceAdapter(Activity context, ArrayList<String> stringArrayList) {

        this.context = context;
        this.CourseDiaryData = CourseDiaryData;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf.otf");
    }

    /**
     * Initialize the constructor.
     *
     * @param context
     */
    public FinanceAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * @return Transaction list array size.
     */
    @Override
    public int getCount() {
        return CourseDiaryData.size();
    }

    /**
     * @return Transaction list item position
     */
    @Override
    public String getItem(int position) {
        return CourseDiaryData.get(position);
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
        View_Holder viewHolder = new View_Holder();

        int rowType = getItemViewType(position);
        switch (rowType) {
            case TYPE_SEPARATOR:
                rowView = inflater.inflate(R.layout.list_item_finance_header, parent, false);
                viewHolder.tvTimeTitle = (TextView) rowView.findViewById(R.id.tvTimeTitle);

                //For now static data display in Transaction list.
                if (position == 0) {
                    viewHolder.tvTimeTitle.setText("Today");
                } else {
                    viewHolder.tvTimeTitle.setText("Last week");
                }

                viewHolder.tvTimeTitle.setTypeface(tfRobotoRegular);
                break;

            case TYPE_ITEM: {
                rowView = inflater.inflate(R.layout.list_item_finance_row, parent, false);

                viewHolder = new View_Holder();
                viewHolder.tvTransTitle = (TextView) rowView.findViewById(R.id.tvTransTitle);
                viewHolder.tvPayOfTrans = (TextView) rowView.findViewById(R.id.tvPayOfTrans);
                viewHolder.tvTimeOfTrans = (TextView) rowView.findViewById(R.id.tvTimeOfTrans);

                rowView.setTag(viewHolder);
                viewHolder = (View_Holder) rowView.getTag();

                //Set Font Style Typeface
                setEventTypeFace(viewHolder);

                switch (position){
                    case 0:
                    case 1:
                        viewHolder.tvTransTitle.setText("Burger");
                       // viewHolder.tvPayOfTrans.setText(CourseDiaryData.get(position).getTitle());
                        viewHolder.tvTimeOfTrans.setText("11:05");
                        break;

                    case 2:
                    case 3:
                        viewHolder.tvTransTitle.setText("Pint of Carslberg");
                        //viewHolder.tvPayOfTrans.setText(CourseDiaryData.get(position).getTitle());
                        viewHolder.tvTimeOfTrans.setText("10:43");
                        break;
                }
            }
            break;
        }
        return rowView;
    }

    /**
     * Implements a method to set typeface.
     */
    public void setEventTypeFace(View_Holder viewHolder) {
        viewHolder.tvTransTitle.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvPayOfTrans.setTypeface(tfRobotoRegular, Typeface.NORMAL);
        viewHolder.tvTimeOfTrans.setTypeface(tfRobotoRegular, Typeface.NORMAL);
    }

    /**
     * Implements a method to add ITEMS programmatically. Note here its the data of list
     * not heading.
     */
    public void addItem(final String item) {
        CourseDiaryData.add(item);
        notifyDataSetChanged();
    }

    /**
     * Implements a method to add section heading for COURSE DIARY
     * events.
     *
     * @param strTitle
     */
    public void addSectionHeaderItem(final String strTitle) {
        CourseDiaryData.add(strTitle);
        sectionHeader.add(CourseDiaryData.size() - 1);
        notifyDataSetChanged();
    }

    /**
     * View_Holder to create COURSE DIARY row to
     * display EVENTS of COURSE DIARY.
     */
    class View_Holder {
        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvTransTitle, tvPayOfTrans, tvTimeOfTrans;
        TextView tvTimeTitle;
    }
}