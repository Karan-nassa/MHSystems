package com.mh.systems.demoapp.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * Created by karan@ucreate.co.in to display Recycle
 * or List View in scroll view on 12/23/2015.
 */
public class ScrollGridView {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void getListViewSize(GridView gridView) {
        ListAdapter myListAdapter = gridView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }

        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        Log.e("HEIGHT", ""+gridView.getChildCount());

      /*  //ic_home_settings list view item in adapter
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getChildCount() * (myListAdapter.getCount() - 1));
        gridView.setLayoutParams(params);
        // print height of adapter on log
        Log.e("height of listItem:", String.valueOf(totalHeight));*/

        //setting list view item in adapter
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getColumnWidth() * (myListAdapter.getCount() - 1));
        gridView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }
}
