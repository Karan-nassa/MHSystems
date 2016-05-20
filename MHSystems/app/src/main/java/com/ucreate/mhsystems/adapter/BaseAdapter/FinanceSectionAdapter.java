package com.ucreate.mhsystems.adapter.BaseAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.models.MyAccountData;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by karan@ucreate.co.in on 04-04-2016.
 */
public class FinanceSectionAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<MyAccountData> mData = new ArrayList<MyAccountData>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public FinanceSectionAdapter(Context context, ArrayList<MyAccountData> myAccountDatas) {
        this.mData = myAccountDatas;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final MyAccountData item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final MyAccountData item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MyAccountData getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.list_item_section_data, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.list_item_section_header, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (rowType){
            case TYPE_ITEM:

                switch (position){
                    case 0:
                        Log.e("pos ", ":"+position);
                        Log.e("DATA ", ":"+mData.get(0).getCurrentBills().get(position).getStrInvoiceDate());
                        holder.textView.setText(""+mData.get(0).getCurrentBills().get(position).getStrInvoiceDate());
                        break;

                    case 1:
                        Log.e("pos ", ":"+position);
                        Log.e("DATA ", ":"+mData.get(1).getMemBalance().get(position).getValueStr());
                        holder.textView.setText(""+mData.get(1).getMemBalance().get(position).getValueStr());
                        break;
                }

                break;

            case TYPE_SEPARATOR:
                holder.textView.setText("CURRENT BALANCE");
                break;
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

}
