
package com.mh.systems.littlehampton.utils;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.mh.systems.littlehampton.web.models.HCapRecords;

import java.util.List;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent, tvHandicapDate;
    private List<HCapRecords> hCapRecordses;

    public MyMarkerView(Context context, int layoutResource, List<HCapRecords> hCapRecordses) {
        super(context, layoutResource);

        this.hCapRecordses = hCapRecordses;

//        tvContent = (TextView) findViewById(R.id.tvHandicapPlayStr);
//        tvHandicapDate = (TextView) findViewById(R.id.tvHandicapDate);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


       // HandicapFragment.mv.refreshContent(e, highlight);
       // HandicapFragment.showHandicapInfo(hCapRecordses.get(e.getXIndex()).getDatePlayedStr(), hCapRecordses.get(e.getXIndex()).getNewExactHCapOnlyStr());
//          tvHandicapDate.setText(hCapRecordses.get(e.getXIndex()).getDatePlayedStr());

//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;
//            //    tvContent.setText("" + /*Utils.formatNumber(*/ce.getHigh()/*, 0, true)*/);
//        } else {
//            //   tvContent.setText("" + /*Utils.formatNumber(*/e.getVal()/*, 0, true)*/);
//        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -(getHeight() * 2);
    }
}
