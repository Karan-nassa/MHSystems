package com.mh.systems.sunningdale.ui.adapter.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.ui.activites.TeeBookingDetailActivity;
import com.mh.systems.sunningdale.web.models.teetimebooking.getdaydata.Product;
import com.mh.systems.sunningdale.web.models.teetimebooking.getdaydata.Slot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


/**
 * Created by  karan@ucreate.co.in to create Handicap History
 * data.
 */
public class TeeBookingRecyclerAdapter extends RecyclerView.Adapter<TeeBookingRecyclerAdapter.ViewHolder> {

    Context context;
    boolean isFromMyBooking = false;
    List<Slot> mSlotsList;

    Typeface tfRobotoRegular;

    NumberFormat formatter = new DecimalFormat("0.00");

    public TeeBookingRecyclerAdapter(Context context, List<Slot> mSlotsList, boolean isFromMyBooking) {
        this.context = context;
        this.isFromMyBooking = isFromMyBooking;
        this.mSlotsList = mSlotsList;

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    public TeeBookingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemLayout = layoutInflater.inflate(R.layout.list_item_tee_booking_row, null);
        return new ViewHolder(itemLayout, viewType, context);
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */
    @Override
    public void onBindViewHolder(TeeBookingRecyclerAdapter.ViewHolder holder, int position) {

        String strStartTime = mSlotsList.get(position).getSlotStart();
        holder.tvMottTime.setText(strStartTime.substring(strStartTime.indexOf(' '), strStartTime.length()));

        holder.tvMottTitle.setText(mSlotsList.get(position).getProducts().get(0).getDescription());

        double strPriceOfEvent = (mSlotsList.get(position).getProducts().get(0).getPrice() / 100.00);
        holder.tvMottPrice.setText(("" + mSlotsList.get(position).getProducts().get(0).getCrnSymbol()
                + formatter.format(strPriceOfEvent)));

        Product mProduct = mSlotsList.get(position).getProducts().get(0);
        float buggyPrice = mProduct.getBuggyPrice();
        buggyPrice = (buggyPrice/100);
        if (mProduct.getBuggyIsValid()) {

            switch (mSlotsList.get(position).getMaxBuggies()){
                case 0:

                    break;

                case 1:
                    holder.llBuggyRow1.setVisibility(View.VISIBLE);
                    holder.llTeeBookingRow.setPadding(10,10,10,10);
                    holder.tvBuggyTitle1.setText(context.getString(R.string.text_with_buggy));
                    holder.tvBuggyPrice1.setText(("" + mSlotsList.get(position).getProducts().get(0).getCrnSymbol()
                            + formatter.format(buggyPrice)));
                    break;

                case 2:
                    holder.llBuggyRow1.setVisibility(View.VISIBLE);
                    holder.llTeeBookingRow.setPadding(10,10,10,10);
                    holder.tvBuggyTitle1.setText(context.getString(R.string.text_with_buggy));
                    holder.tvBuggyPrice1.setText(("" + mSlotsList.get(position).getProducts().get(0).getCrnSymbol()
                            + formatter.format(buggyPrice)));

                    holder.llBuggyRow2.setVisibility(View.VISIBLE);
                    holder.tvBuggyTitle2.setText(context.getString(R.string.text_with_buggies));
                    holder.llTeeBookingRow.setPadding(10,10,10,10);
                    holder.tvBuggyPrice2.setText(("" + mSlotsList.get(position).getProducts().get(0).getCrnSymbol()
                            + formatter.format(buggyPrice*2)));
                    break;
            }


        } else {
            holder.llBuggyRow1.setVisibility(View.GONE);
            holder.llBuggyRow2.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = holder.llBookingDescRow.getLayoutParams();
            params.height = 80;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.llBookingDescRow.setLayoutParams(params);
            holder.llTeeBookingRow.setPadding(10,10,10,10);
        }
    }

    @Override
    public int getItemCount() {
        return mSlotsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Create custom view and initialize the font style.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMottTime, tvMottTitle, tvMottPrice;
        TextView tvBuggyPrice1, tvBuggyPrice2;
        TextView tvBuggyTitle1, tvBuggyTitle2;
        LinearLayout llBookingDescRow;
        LinearLayout llTeeBookingRow, llBuggyRow1, llBuggyRow2;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            llTeeBookingRow = (LinearLayout) itemView.findViewById(R.id.llTeeBookingRow);

            llBookingDescRow = (LinearLayout) itemView.findViewById(R.id.llBookingDescRow);
            llBuggyRow1 = (LinearLayout) itemView.findViewById(R.id.llBuggyRow1);
            llBuggyRow2 = (LinearLayout) itemView.findViewById(R.id.llBuggyRow2);

            tvMottTime = (TextView) itemView.findViewById(R.id.tvMottDate);
            tvMottTitle = (TextView) itemView.findViewById(R.id.tvMottTitle);
            tvMottPrice = (TextView) itemView.findViewById(R.id.tvMottText);

            tvBuggyTitle1 = (TextView) itemView.findViewById(R.id.tvBuggyTitle1);
            tvBuggyTitle2 = (TextView) itemView.findViewById(R.id.tvBuggyTitle2);
            tvBuggyPrice1 = (TextView) itemView.findViewById(R.id.tvBuggyPrice1);
            tvBuggyPrice2 = (TextView) itemView.findViewById(R.id.tvBuggyPrice2);

            setFontTypeFace();

            llTeeBookingRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Slot slotInstance = mSlotsList.get(getAdapterPosition());
            Product productInstance = slotInstance.getProducts().get(0);

            Intent intent = new Intent(context, TeeBookingDetailActivity.class);
            intent.putExtra("FROM_MY_BOOKING", isFromMyBooking);
            intent.putExtra("SlotStart", slotInstance.getSlotStart());
            intent.putExtra("SlotStartDateTime", slotInstance.getSlotStartDateTime());
            intent.putExtra("Description", productInstance.getDescription());
            intent.putExtra("Price", productInstance.getPrice());
            intent.putExtra("PLU", productInstance.getPLU());
            intent.putExtra("BuggyIsOptional", productInstance.getBuggyIsOptional());
            intent.putExtra("BuggyPrice",  productInstance.getBuggyPrice());
            intent.putExtra("BuggyPLU", productInstance.getBuggyPLU());
            intent.putExtra("CrnSymbol", productInstance.getCrnSymbol());
            intent.putExtra("BuggyIsValid", productInstance.getBuggyIsValid());
            intent.putExtra("MaxBuggies", slotInstance.getMaxBuggies());
            context.startActivity(intent);
        }

        private void setFontTypeFace() {
            tvMottTime.setTypeface(tfRobotoRegular);
            tvMottTime.setTypeface(tfRobotoRegular);
            tvMottTime.setTypeface(tfRobotoRegular);
        }
    }
}
