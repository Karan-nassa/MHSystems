package com.mh.systems.demoapp.ui.adapter.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.activites.TeeBookingDetailActivity;
import com.mh.systems.demoapp.web.models.teetimebooking.booking.Booking;
import com.mh.systems.demoapp.web.models.teetimebooking.getdaydata.Product;
import com.mh.systems.demoapp.web.models.teetimebooking.getdaydata.Slot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


/**
 * Created by  karan@ucreate.co.in to create Handicap History
 * data.
 */
public class MyBookingRecyclerAdapter extends RecyclerView.Adapter<MyBookingRecyclerAdapter.ViewHolder> {

    Context context;
    boolean isFromMyBooking = false;
    List<Booking> mBookingList;

    Typeface tfRobotoRegular;

    NumberFormat formatter = new DecimalFormat("0.00");
    Integer fundsAvail;

    public MyBookingRecyclerAdapter(Context context, List<Booking> mBookingList, boolean isFromMyBooking, Integer fundsAvail) {
        this.context = context;
        this.isFromMyBooking = isFromMyBooking;
        this.mBookingList = mBookingList;
        this.fundsAvail = fundsAvail;

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    public MyBookingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(MyBookingRecyclerAdapter.ViewHolder holder, int position) {

        String strStartTime = mBookingList.get(position).getDate();
        //holder.tvMottTime.setText(strStartTime.substring(strStartTime.indexOf('T') + 1,strStartTime.lastIndexOf(':')));

        holder.tvMottTime.setText(strStartTime.substring(0, strStartTime.indexOf(' ')));
        holder.tvMottPrice.setText(strStartTime.substring(strStartTime.indexOf(' '),strStartTime.length()));

        holder.tvMottTitle.setText(mBookingList.get(position).getDescription());

        holder.llBuggyRow.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = holder.llBookingDescRow.getLayoutParams();
        params.height = 80;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.llBookingDescRow.setLayoutParams(params);
        holder.llTeeBookingRow.setPadding(10,10,10,10);
    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
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
        TextView tvBuggyPrice;
        LinearLayout llTeeBookingRow, llBookingDescRow, llBuggyRow;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            llTeeBookingRow = (LinearLayout) itemView.findViewById(R.id.llTeeBookingRow);
            llBookingDescRow = (LinearLayout) itemView.findViewById(R.id.llBookingDescRow);
            llBuggyRow = (LinearLayout) itemView.findViewById(R.id.llBuggyRow);

            tvMottTime = (TextView) itemView.findViewById(R.id.tvMottDate);
            tvMottTitle = (TextView) itemView.findViewById(R.id.tvMottTitle);
            tvMottPrice = (TextView) itemView.findViewById(R.id.tvMottText);

            tvBuggyPrice = (TextView) itemView.findViewById(R.id.tvBuggyPrice);

            setFontTypeFace();

            llTeeBookingRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Booking booking =  mBookingList.get(getAdapterPosition());

            Intent intent = new Intent(context, TeeBookingDetailActivity.class);
            intent.putExtra("FundsAvail", fundsAvail);
            intent.putExtra("FROM_MY_BOOKING", isFromMyBooking);
            intent.putExtra("SlotStart", booking.getDate());
            intent.putExtra("BookingId", booking.getBookingId());
            intent.putExtra("Description", booking.getDescription());
            intent.putExtra("CanCancel", booking.getCanCancel());
            context.startActivity(intent);
        }

        private void setFontTypeFace() {
            tvMottTime.setTypeface(tfRobotoRegular);
            tvMottTime.setTypeface(tfRobotoRegular);
            tvMottTime.setTypeface(tfRobotoRegular);
        }
    }
}
