package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    public GridAdapter(HomeActivity mainActivity, String gridTitles[], TypedArray gridIcons) {

        context = mainActivity;
        this.gridTitles = gridTitles;
        this.gridIcons = gridIcons;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return gridTitles.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tvGridTitle;
        ImageView ivGridLogo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_grid_row, null);
        holder.tvGridTitle = (TextView) rowView.findViewById(R.id.tvGridTitle);
        holder.ivGridLogo = (ImageView) rowView.findViewById(R.id.ivGridLogo);

        //Set View data according position.
        holder.tvGridTitle.setText(gridTitles[position]);
        holder.ivGridLogo.setImageResource(gridIcons.getResourceId(position, -1));

        return rowView;
    }

}