package com.mh.systems.hartsbourne.adapter.RecyclerAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.models.forecast.ListOfDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by  karan@ucreate.co.in to create Handicap History
 * data.
 */
public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder> {

    Context context;
    List<ListOfDay> weatherListOfList;

    Typeface tfRobotoRegular, tfRobotoLight;

    // The default constructor to receive titles,icons and context from DashboardActivity.
    public ForecastRecyclerAdapter(Context context, List<ListOfDay> weatherListOfList) {

        this.weatherListOfList = weatherListOfList;
        this.context = context;

        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        tfRobotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }

    /**
     * This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     * Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public ForecastRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemLayout = layoutInflater.inflate(R.layout.list_item_weather_detail, null);
        return new ViewHolder(itemLayout, viewType, context);
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(ForecastRecyclerAdapter.ViewHolder holder, int position) {

        String strDateTime = weatherListOfList.get(position).getDtTxt();

        holder.tvTimeSlot.setText(getFormateTime(strDateTime));
        holder.tvTempSlot.setText("" + ((int) (weatherListOfList.get(position).getMain().getTemp() - 273.15f)) + "°C");
        holder.ivWeatherIcon.setImageDrawable(getWeatherIcon(weatherListOfList.get(position).getWeather().get(0).getIcon()));
    }

    /**
     * It returns the total no. of items . We +1 count to include the header view.
     * So , it the total count is 5 , the method returns 6.
     * This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */
    @Override
    public int getItemCount() {
        return  weatherListOfList.size();
    }

    /**
     * This methods returns 0 if the position of the item is '0'.
     * If the position is zero its a header view and if its anything else
     * its a list_item_alphabet_row with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Create custom view and initialize the font style.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvTimeSlot, tvTempSlot;
        ImageView ivWeatherIcon;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            tvTimeSlot = (TextView) itemView.findViewById(R.id.tvTimeSlot);
            tvTempSlot = (TextView) itemView.findViewById(R.id.tvTempSlot);

            ivWeatherIcon = (ImageView) itemView.findViewById(R.id.ivWeatherIcon);

            setFontTypeFace();
        }

        private void setFontTypeFace() {
            tvTimeSlot.setTypeface(tfRobotoRegular);
            tvTempSlot.setTypeface(tfRobotoLight);
        }
    }

    /**
     * Implements a method to RETURN the name of DAY from
     * specific date format.
     *
     * @param strTime : Example => "yyyy-MM-dd HH:mm:ss"
     * @return strTime  : HH:mm [14:00]
     */
    public static String getFormateTime(String strTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        try {
            Date date = inputFormat.parse(strTime);
            strTime = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strTime;
    }

    /**
     * Generate Weather Icon from string.
     *
     * @param strNameOfIcon : Name of String.
     * @return drawable     : Drawable type icon.
     */
    private Drawable getWeatherIcon(String strNameOfIcon) {
        int resID = context.getResources().getIdentifier("e" + strNameOfIcon, "mipmap", context.getPackageName());
        Drawable drawable = ContextCompat.getDrawable(context, resID);
        return drawable;
    }
}
