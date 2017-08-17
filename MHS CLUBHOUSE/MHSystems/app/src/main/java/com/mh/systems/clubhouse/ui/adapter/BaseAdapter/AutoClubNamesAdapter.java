package com.mh.systems.clubhouse.ui.adapter.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.mh.systems.clubhouse.R;
import com.mh.systems.clubhouse.web.models.clubnames.ListOfClubsData;

import java.util.ArrayList;

/**
 * Created by admin on 09-08-2017.
 */

public class AutoClubNamesAdapter extends ArrayAdapter<ListOfClubsData> {
    ArrayList<ListOfClubsData> customers, tempCustomer, suggestions;

    public AutoClubNamesAdapter(Context context, ArrayList<ListOfClubsData> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.customers = objects;
        this.tempCustomer = new ArrayList<ListOfClubsData>(objects);
        this.suggestions = new ArrayList<ListOfClubsData>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListOfClubsData customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_clubname_row, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvNameOfClub);

        txtCustomer.setText(customer.getClubName());

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            ListOfClubsData customer = (ListOfClubsData) resultValue;

            return customer.getClubName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ListOfClubsData people : tempCustomer) {
                    if (people.getClubName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<ListOfClubsData> c = (ArrayList<ListOfClubsData>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ListOfClubsData cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}