package com.mh.systems.hartsbourne.ui.adapter.RecyclerAdapter;

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
import com.mh.systems.hartsbourne.ui.activites.WeatherDetailActivity;
import com.mh.systems.hartsbourne.web.models.forecast.ListOfDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by  karan@ucreate.co.in to create Handicap History
 * data.
 */
public class WeatherMainRecyclerAdapter extends RecyclerView.Adapter<WeatherMainRecyclerAdapter.ViewHolder> {

    Context context;
    List<List<ListOfDay>> weatherListOfList;

    Typeface tfSfMedium, tfSfLight;

    View mLastSelectedView = null;

    // The default constructor to receive titles,icons and context from DashboardActivity.
    public WeatherMainRecyclerAdapter(Context context, List<List<ListOfDay>> weatherListOfList) {

        this.weatherListOfList = weatherListOfList;
        this.context = context;

        tfSfMedium = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Text-Medium.otf");
        tfSfLight = Typeface.createFromAsset(context.getAssets(), "fonts/SF-UI-Display-Light.otf");
    }

    /**
     * This is called every time when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     * Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemLayout = layoutInflater.inflate(R.layout.list_item_weather_main, null);
        return new ViewHolder(itemLayout, viewType, context);
    }

    /**
     * This method is called by RecyclerView.Adapter to display the data at the specified position. 
     * This method should update the contents of the itemView to reflect the item at the given position.
     * So here , if position!=0 it implies its a list_item_alphabet_row and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvDayName.setText(getFormateDayName(weatherListOfList.get(position).get(0).getDtTxt()));
        holder.tvTempDay.setText(("" + getFormateDayName("" + ((int) (weatherListOfList.get(position).get(0).getMain().getTemp() - 273.15f)) + "°C")));
        holder.ivWeatherDay.setImageDrawable(getWeatherIcon(weatherListOfList.get(position).get(0).getWeather().get(0).getIcon()));

       /* if (position == 0 && mLastSelectedView != null) {
            mLastSelectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.color313130));
        }*/
    }

    /**
     * It returns the total no. of items . We +1 count to include the header view.
     * So , it the total count is 5 , the method returns 6.
     * This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */
    @Override
    public int getItemCount() {
        return weatherListOfList.size();
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * Text Row VIEW INSTANCES DECLARATION
         */
        TextView tvDayName, tvTempDay;
        ImageView ivWeatherDay;

        public ViewHolder(View drawerItem, int itemType, Context context) {
            super(drawerItem);

            tvDayName = (TextView) itemView.findViewById(R.id.tvDayName);
            tvTempDay = (TextView) itemView.findViewById(R.id.tvTempDay);

            ivWeatherDay = (ImageView) itemView.findViewById(R.id.ivWeatherDay);

            setFontTypeFace();

            /*if(getAdapterPosition() == 0){
                mLastSelectedView = drawerItem;
                mLastSelectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.color313130));
            }*/

            drawerItem.setOnClickListener(this);
        }

        private void setFontTypeFace() {
            tvDayName.setTypeface(tfSfMedium);
            tvTempDay.setTypeface(tfSfLight);
        }

        @Override
        public void onClick(View view) {
            if (mLastSelectedView != view) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.color313130));

                if (mLastSelectedView != null) {
                    mLastSelectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.color242422));
                }

                mLastSelectedView = view;

                //Update 3 hours forecast list.
                ((WeatherDetailActivity) context).updateDetailUI(getAdapterPosition());
            }
        }
    }

    /**
     * Generate Weather Icon from string.
     *
     * @param strNameOfIcon : Name of String.
     * @return drawable     : Drawable type icon.
     */
    public Drawable getWeatherIcon(String strNameOfIcon) {
        int resID = context.getResources().getIdentifier("e" + strNameOfIcon, "mipmap", context.getPackageName());
        Drawable drawable = ContextCompat.getDrawable(context, resID);
        return drawable;
    }

    /**
     * Implements a method to RETURN the name of DAY from
     * specific date format.
     *
     * @param strDate : Example => "yyyy-MM-dd HH:mm:ss"
     * @return strDate  : E [Tue]
     */
    public static String getFormateDayName(String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E");

        try {
            Date date = inputFormat.parse(strDate);
            strDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strDate;
    }
}
