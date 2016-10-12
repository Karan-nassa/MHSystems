package com.mh.systems.brokenhurst.adapter.RecyclerAdapter;

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

import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.activites.ClubNewsActivity;
import com.mh.systems.brokenhurst.activites.ClubNewsDetailActivity;
import com.mh.systems.brokenhurst.models.ClubNews.ClubNewsData;

import java.util.ArrayList;


/**
 * Created by karan@ucreate.co.in on 17/6/2016 to display Club News and Swipe to remove.
 */
public class ClubNewsSwipeAdapter extends RecyclerSwipeAdapter<ClubNewsSwipeAdapter.SimpleViewHolder> {

    private Context mContext;
    public ArrayList<ClubNewsData> clubNewsDataArrayList;

    public ClubNewsSwipeAdapter(ClubNewsActivity context, ArrayList<ClubNewsData> clubNewsDataArrayList) {
        this.mContext = context;
        this.clubNewsDataArrayList = clubNewsDataArrayList;
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

        viewHolder.tvTitleOfNews.setText(clubNewsDataArrayList.get(position).getTitle());
        viewHolder.tvTimeOfNews.setText(clubNewsDataArrayList.get(position).getCreatedDate());

        if(clubNewsDataArrayList.get(position).getIsRead()){
            viewHolder.ivReadStatus.setVisibility(View.INVISIBLE);
        }

        viewHolder.flRemoveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ClubNewsActivity)mContext).deleteClubNewsService(position, clubNewsDataArrayList.get(position).getClubNewsID());

//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                clubNewsDataArrayList.remove(position);
//                notifyItemRemoved(position);
//                notifyDataSetChanged();
//                notifyItemRangeChanged(position, clubNewsDataArrayList.size());
//                mItemManger.closeAllItems();
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
        return clubNewsDataArrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    //  ViewHolder Class
    public class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeLayout swipeLayout;
        FrameLayout flRemoveGroup;
        ImageView ivReadStatus;
        RelativeLayout rlNewsGroup;
        TextView tvTitleOfNews, tvTimeOfNews;
        Context mContext;

        public SimpleViewHolder(View itemView, Context context) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

            flRemoveGroup = (FrameLayout) itemView.findViewById(R.id.flRemoveGroup);
            ivReadStatus = (ImageView) itemView.findViewById(R.id.ivReadStatus);
            rlNewsGroup = (RelativeLayout) itemView.findViewById(R.id.rlNewsGroup);

            tvTitleOfNews = (TextView) itemView.findViewById(R.id.tvTitleOfNews);
            tvTimeOfNews = (TextView) itemView.findViewById(R.id.tvTimeOfNews);

            mContext = context;

            rlNewsGroup.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            Intent detailNewsIntent = new Intent(mContext, ClubNewsDetailActivity.class);
            detailNewsIntent.putExtra("ClubNewsID", clubNewsDataArrayList.get(getAdapterPosition()).getClubNewsID());
            detailNewsIntent.putExtra("CreatedDate", clubNewsDataArrayList.get(getAdapterPosition()).getCreatedDate());
            detailNewsIntent.putExtra("Message", clubNewsDataArrayList.get(getAdapterPosition()).getMessage());
            detailNewsIntent.putExtra("IsRead", clubNewsDataArrayList.get(getAdapterPosition()).getIsRead());
            mContext.startActivity(detailNewsIntent);
        }
    }
}

