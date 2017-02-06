package com.mh.systems.redlibbets.adapter.RecyclerAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mh.systems.redlibbets.R;
import com.mh.systems.redlibbets.activites.TopUpActivity;
import com.mh.systems.redlibbets.models.TopUp.TopupList;

import java.util.ArrayList;


/**
 * Created by  karan@ucreate.co.in to create Handicap History
 * data.
 */
public class TopUpPriceListRecyclerAdapter extends RecyclerView.Adapter<TopUpPriceListRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TopupList> topUpPriceListDataList;

    private Typeface tfRobotoMedium;

    private Button btLastSelectedPrice;

    public TopUpPriceListRecyclerAdapter(Context context, ArrayList<TopupList> topUpPriceListDataList) {

        this.topUpPriceListDataList = topUpPriceListDataList;
        this.context = context;

        tfRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
    }

    /**
     * This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     * Then this ViewHolder is passed to onBindViewHolder to display items.
     */
    @Override
    public TopUpPriceListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemLayout = layoutInflater.inflate(R.layout.grid_item_time_view, null);
        return new ViewHolder(itemLayout, viewType, context);
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */
    @Override
    public void onBindViewHolder(TopUpPriceListRecyclerAdapter.ViewHolder holder, int position) {

        holder.btTimeSlot.setText(("" + topUpPriceListDataList.get(position).getText()));
    }

    @Override
    public int getItemCount() {
        return topUpPriceListDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Create custom view and initialize the font style.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btTimeSlot;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            btTimeSlot = (Button) itemView.findViewById(R.id.btTimeSlot);
            btTimeSlot.setTypeface(tfRobotoMedium);

            btTimeSlot.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (btLastSelectedPrice != view) {

                setSlotSelected(view);
                ((Button) view).setTextColor(ContextCompat.getColor(context, R.color.colorWhiteffffff));

                if (btLastSelectedPrice != null) {
                    setSlotNotSelected(btLastSelectedPrice);
                    btLastSelectedPrice.setTextColor(ContextCompat.getColor(context, R.color.colorBlack000000));
                }

                btLastSelectedPrice = (Button) view;

                //UPDATE price UI changes.
                ((TopUpActivity) context).updatePriceUI(getAdapterPosition());
            }
        }
    }

    /**
     * Implements this method set price slot as
     * selected.
     */
    private void setSlotSelected(View btPriceView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btPriceView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_time_buttonc0995b));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btPriceView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_time_buttonc0995b));
        }
    }

    /**
     * Implements this method set price slot as
     * un-selected.
     */
    private void setSlotNotSelected(View btPriceView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btPriceView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_time_buttone4e4e4));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btPriceView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_time_buttone4e4e4));
        }
    }

    /**
     * Implements this method to display as no
     * selected view.
     */
    public void markAsUnselected() {

        if (btLastSelectedPrice != null) {
            setSlotNotSelected(btLastSelectedPrice);
            btLastSelectedPrice.setTextColor(ContextCompat.getColor(context, R.color.colorBlack000000));
            btLastSelectedPrice = null;
        }
    }
}
