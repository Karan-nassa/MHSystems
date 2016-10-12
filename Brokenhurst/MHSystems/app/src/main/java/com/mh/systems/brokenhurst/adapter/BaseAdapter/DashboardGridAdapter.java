package com.mh.systems.brokenhurst.adapter.BaseAdapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.activites.DashboardActivity;

/**
 * Created by karan@ucreate.co.in
 * for Home Grid options on 14-03-2016.
 */
public class DashboardGridAdapter extends BaseAdapter {

    // String [] result;
    Context context;
    // int [] imageId;
    private static LayoutInflater inflater = null;

    TypedArray gridIcons;
    String gridTitles[];
    String hCapExactStr;
    TypedArray gridBackground;

    Typeface tfButtlerMedium, tfRobotoMedium;

    public DashboardGridAdapter(DashboardActivity mainActivity, String gridTitles[], TypedArray gridIcons, TypedArray gridBackground, String hCapExactStr) {

        context = mainActivity;
        this.gridTitles = gridTitles;
        this.hCapExactStr = hCapExactStr;
        this.gridIcons = gridIcons;
        this.gridBackground = gridBackground;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tfButtlerMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Butler_Medium.otf");
        tfRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    @Override
    public int getCount() {
        return gridTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Declares a view holder class to
     * contain all resources.
     */
    public class Holder {
        TextView tvHCapExactStr;
        TextView tvGridTitle;
        ImageView ivGridLogo;
        RelativeLayout rlGridMenuItem;
        FrameLayout flBadgerGroup;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView = null;
        if (position == 0) {
            rowView = inflater.inflate(R.layout.item_grid_row_text, null);

            holder.tvHCapExactStr = (TextView) rowView.findViewById(R.id.tvHCapExactStr);
            holder.tvHCapExactStr.setText(hCapExactStr);

            holder.tvHCapExactStr.setTypeface(tfRobotoMedium);
        } else {
            rowView = inflater.inflate(R.layout.item_grid_row_icon, null);
        }
        holder.tvGridTitle = (TextView) rowView.findViewById(R.id.tvGridTitle);
        holder.ivGridLogo = (ImageView) rowView.findViewById(R.id.ivGridLogo);
        holder.rlGridMenuItem = (RelativeLayout) rowView.findViewById(R.id.rlGridMenuItem);
        holder.flBadgerGroup = (FrameLayout) rowView.findViewById(R.id.flBadgerGroup);

        //Set View data according position.
        holder.tvGridTitle.setText(gridTitles[position]);
        holder.ivGridLogo.setImageResource(gridIcons.getResourceId(position, -1));

        if(position==4) {
            holder.flBadgerGroup.setVisibility(View.VISIBLE);
        }

        //Set Font type.
        holder.tvGridTitle.setTypeface(tfButtlerMedium);

        //Set Background colors.
        //holder.rlGridMenuItem.setBackgroundResource(gridBackground.getResourceId(position, -1));

        return rowView;
    }

}