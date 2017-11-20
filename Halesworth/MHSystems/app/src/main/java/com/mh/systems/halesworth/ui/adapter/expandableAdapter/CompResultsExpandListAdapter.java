package com.mh.systems.halesworth.ui.adapter.expandableAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.web.models.ResultEntries;

import java.util.List;
import java.util.Map;

/**
 * Expandable List on 07-11-2017
 * by Karan Nassa.
 */
public class CompResultsExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    Map<String, List<ResultEntries>> listHashMapOfResults;

    public CompResultsExpandListAdapter(Context context, List<String> expandableListTitle,
                                        Map<String, List<ResultEntries>> listHashMapOfResults) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.listHashMapOfResults = listHashMapOfResults;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.listHashMapOfResults.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LinearLayout llRankGroupRow;
        TextView tvPosOfMember, tvNameOfMember, tvScoreOfMember, tvTotalScoreOfMember;

        final ResultEntries resultEntries = (ResultEntries) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_competition_detail, null);
        }

        llRankGroupRow = (LinearLayout) convertView.findViewById(R.id.llRankGroupRow);
        if (expandedListPosition % 2 == 0) {
            llRankGroupRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorF1F1F0));
        } else {
            llRankGroupRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorF9F8F7));
        }

        tvPosOfMember = (TextView) convertView.findViewById(R.id.tvPosOfMember);
        tvNameOfMember = (TextView) convertView.findViewById(R.id.tvNameOfMember);
        tvScoreOfMember = (TextView) convertView.findViewById(R.id.tvScoreOfMember);
        tvTotalScoreOfMember = (TextView) convertView.findViewById(R.id.tvTotalScoreOfMember);

        tvPosOfMember.setText(resultEntries.getPlaceStr());
        tvNameOfMember.setText(resultEntries.getEntryName());
        tvScoreOfMember.setText(resultEntries.getExactHCap());
        tvTotalScoreOfMember.setText(resultEntries.getScoreSummary());

       /* TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);*/
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.listHashMapOfResults.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_comp_expandable_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        ImageView ivExpandArrow = (ImageView) convertView.findViewById(R.id.ivExpandArrow);

        LinearLayout llTopHeading = (LinearLayout) convertView.findViewById(R.id.llTopHeading);

        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle.substring(1, listTitle.length()));

        if (isExpanded) {
            ivExpandArrow.setImageResource(R.mipmap.ic_expand_up_new);
            llTopHeading.setVisibility(View.VISIBLE);
        } else {
            ivExpandArrow.setImageResource(R.mipmap.ic_expand_down_new);
            llTopHeading.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
