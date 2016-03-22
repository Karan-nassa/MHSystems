package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.HomeActivity;

/**
 * Created by karan@ucreate.co.in
 * for Home Grid options on 14-03-2016.
 */
public class GridAdapter extends BaseAdapter {

    // String [] result;
    Context context;
    // int [] imageId;
    private static LayoutInflater inflater = null;

    TypedArray gridIcons;
    String gridTitles[];
    TypedArray gridBackground;

    public GridAdapter(HomeActivity mainActivity, String gridTitles[], TypedArray gridIcons, TypedArray gridBackground) {

        context = mainActivity;
        this.gridTitles = gridTitles;
        this.gridIcons = gridIcons;
        this.gridBackground = gridBackground;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        TextView tvGridTitle;
        ImageView ivGridLogo;
        RelativeLayout rlGridMenuItem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_grid_row, null);
        holder.tvGridTitle = (TextView) rowView.findViewById(R.id.tvGridTitle);
        holder.ivGridLogo = (ImageView) rowView.findViewById(R.id.ivGridLogo);
        holder.rlGridMenuItem = (RelativeLayout) rowView.findViewById(R.id.rlGridMenuItem);

        //Set View data according position.
        holder.tvGridTitle.setText(gridTitles[position]);
        holder.ivGridLogo.setImageResource(gridIcons.getResourceId(position, -1));

        //Set Background colors.
        holder.rlGridMenuItem.setBackgroundResource(gridBackground.getResourceId(position, -1));

        return rowView;
    }

}