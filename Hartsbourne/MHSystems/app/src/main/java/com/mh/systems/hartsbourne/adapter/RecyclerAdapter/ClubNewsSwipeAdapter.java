package com.mh.systems.hartsbourne.adapter.RecyclerAdapter;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.activites.ClubNewsDetailActivity;

import java.util.ArrayList;


/**
 * Created by karan@mh.co.in on 17/6/2016 to
 * display Club News and Swipe to remove.
 */
public class ClubNewsSwipeAdapter extends RecyclerSwipeAdapter<ClubNewsSwipeAdapter.SimpleViewHolder> {

    private Context mContext;
    ArrayList<String> stringArrayList;

    public ClubNewsSwipeAdapter(Context context, ArrayList<String> stringArrayList) {
        this.mContext = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_club_news, parent, false);
        return new SimpleViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
       /* if (position % 2 == 0) {
            viewHolder.rllayout.setBackgroundColor(Color.WHITE);
        } else {
            viewHolder.rllayout.setBackgroundColor(Color.parseColor("#fefaf1"));
        }*/
        //    final Student item = studentList.get(position);

     /*   viewHolder.tvTitle.setText(datum.get(position).getBaseLocation() + " To " + datum.get(position).getDestLocation());
        viewHolder.tvdate.setText(datum.get(position).getStartDate());
        viewHolder.tvtime.setText(datum.get(position).getStartTime());
        viewHolder.tvDesplace.setText(datum.get(position).getDestLocation());
        viewHolder.tvNumberOfRiders.setText(datum.get(position).getRiders() + " Riders");
        if (datum.get(position).getMutual() == 0) {
            viewHolder.tvLabelHaveJoined.setText("Not Joined");
        } else {
            viewHolder.tvLabelHaveJoined.setText("Have Joined");
        }*/

        switch (position){
            case 0:
                viewHolder.tvTitleOfNews.setText("50% of select food this weekend:");
                break;

            case 1:
                viewHolder.tvTitleOfNews.setText("Jazz Evening with 2 course meal at £50 per couple:");
                break;

            case 2:
                viewHolder.tvTitleOfNews.setText("Why not bring a friend to play 18 holes at 20% off the standard green fees:");
                break;
        }

        viewHolder.flRemoveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                stringArrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                notifyItemRangeChanged(position, stringArrayList.size());
                mItemManger.closeAllItems();*/
            }
        });

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
        //  viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

      /*  viewHolder.lllinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(v.getContext(), "Clicked on Map " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.frameremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mItemManger.removeShownLayouts(viewHolder.swipeLayout);


            }
        });


        viewHolder.fmchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    //  ViewHolder Class
    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        SwipeLayout swipeLayout;
        FrameLayout flRemoveGroup;
        ImageView ivReadStatus;
        RelativeLayout rlNewsGroup;
        Context mContext;
        TextView tvTimeOfNews, tvTitleOfNews;

        public SimpleViewHolder(View itemView, Context context) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

            flRemoveGroup = (FrameLayout) itemView.findViewById(R.id.flRemoveGroup);
            ivReadStatus = (ImageView) itemView.findViewById(R.id.ivReadStatus);
            rlNewsGroup = (RelativeLayout) itemView.findViewById(R.id.rlNewsGroup);

            tvTimeOfNews = (TextView) itemView.findViewById(R.id.tvTimeOfNews);
            tvTitleOfNews = (TextView) itemView.findViewById(R.id.tvTitleOfNews);

            mContext = context;

            rlNewsGroup.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            Intent detailNewsIntent = new Intent(mContext, ClubNewsDetailActivity.class);
            detailNewsIntent.putExtra("NEWS_POS", getAdapterPosition());
            mContext.startActivity(detailNewsIntent);
        }
    }
}

