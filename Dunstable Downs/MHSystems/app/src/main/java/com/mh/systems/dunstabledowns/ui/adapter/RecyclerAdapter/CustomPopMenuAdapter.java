package com.mh.systems.dunstabledowns.ui.adapter.RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.systems.dunstabledowns.R;
import com.mh.systems.dunstabledowns.ui.activites.CompetitionsActivity;
import com.mh.systems.dunstabledowns.web.models.CompFilterOptions;

import java.util.ArrayList;

/**
 * Created by user on 3/11/17.
 */

public class CustomPopMenuAdapter extends RecyclerView.Adapter<CustomPopMenuAdapter.ContactViewHolder> {


    Context context;
    Activity activity;
    ArrayList<CompFilterOptions> compFilterOptList;

    public CustomPopMenuAdapter(Context pContext, ArrayList<CompFilterOptions> compFilterOptList) {
        context = pContext;
        this.compFilterOptList = compFilterOptList;
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(context).inflate(R.layout.list_item_filter_comp_row, null);
        return new ContactViewHolder(content);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.tvTitle.setText(compFilterOptList.get(position).getStrTitle());

       //boolean isChecked = holder.cbSelector.isChecked();
        if(compFilterOptList.get(position).isSelected()){
            holder.ivCheckMark.setVisibility(View.VISIBLE);
        }else{
            holder.ivCheckMark.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return compFilterOptList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        //CheckBox cbSelector;
        ImageView ivCheckMark;
        TextView tvTitle;

        public ContactViewHolder(View itemView) {
            super(itemView);

            ivCheckMark = (ImageView) itemView.findViewById(R.id.ivCheckMark);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CompetitionsActivity)context).dismissPopMenu();
                }
            });
        }
    }
}

