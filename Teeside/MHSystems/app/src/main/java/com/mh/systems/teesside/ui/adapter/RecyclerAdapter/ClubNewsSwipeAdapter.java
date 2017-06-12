package com.mh.systems.teesside.ui.adapter.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.mh.systems.teesside.R;
import com.mh.systems.teesside.ui.activites.ClubNewsActivity;
import com.mh.systems.teesside.ui.activites.ClubNewsDetailActivity;
import com.mh.systems.teesside.web.models.clubnewsthumbnail.ClubNewsThumbnailData;
import com.mh.systems.teesside.web.api.WebAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by karan@ucreate.co.in on 17/6/2016 to display Club News and Swipe to remove.
 */
public class ClubNewsSwipeAdapter extends RecyclerSwipeAdapter<ClubNewsSwipeAdapter.SimpleViewHolder> {

    private Context mContext;
    private ArrayList<ClubNewsThumbnailData> clubNewsDataArrayList;

    private String strThumbnailURL = "";

    private final int POSITION_THUMBNAIL = 0;
    private final int POSITION_NO_THUMBNAIL = 1;

    public ClubNewsSwipeAdapter(ClubNewsActivity context, ArrayList<ClubNewsThumbnailData> clubNewsDataArrayList) {
        this.mContext = context;
        this.clubNewsDataArrayList = clubNewsDataArrayList;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case POSITION_NO_THUMBNAIL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_club_news, parent, false);
                return new SimpleViewHolder(view, mContext, viewType);

            case POSITION_THUMBNAIL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_club_news_thumb, parent, false);
                return new SimpleViewHolder(view, mContext, viewType);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return (clubNewsDataArrayList.get(position).getMessage().length() == 0) ? POSITION_NO_THUMBNAIL : POSITION_THUMBNAIL;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        viewHolder.tvTitleOfNews.setText(clubNewsDataArrayList.get(position).getTitle());
        viewHolder.tvTimeOfNews.setText(clubNewsDataArrayList.get(position).getDate());

        if (clubNewsDataArrayList.get(position).getIsRead()) {
            viewHolder.ivReadStatus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ivReadStatus.setVisibility(View.VISIBLE);
        }

        strThumbnailURL = "";

        if (clubNewsDataArrayList.get(position).getMessage().length() > 0) {
            Picasso.with(mContext)
                    .load(WebAPI.API_BASE_URL + "/webapi/" + clubNewsDataArrayList.get(position).getMessage())
                    .placeholder(R.mipmap.ic_no_image)
                    .error(R.mipmap.ic_no_image)
                    .into(viewHolder.ivNewsThumbnail);
        }

        viewHolder.flRemoveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ClubNewsActivity) mContext).deleteClubNewsService(position, clubNewsDataArrayList.get(position).getClubNewsID());
            }
        });

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

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

    public class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeLayout swipeLayout;
        FrameLayout flRemoveGroup;
        ImageView ivReadStatus;
        RelativeLayout rlNewsGroup;
        TextView tvTitleOfNews, tvTimeOfNews;
        ImageView ivNewsThumbnail;
        Context mContext;

        public SimpleViewHolder(View itemView, Context context, int viewType) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

            flRemoveGroup = (FrameLayout) itemView.findViewById(R.id.flRemoveGroup);
            ivReadStatus = (ImageView) itemView.findViewById(R.id.ivReadStatus);
            rlNewsGroup = (RelativeLayout) itemView.findViewById(R.id.rlNewsGroup);

            tvTitleOfNews = (TextView) itemView.findViewById(R.id.tvTitleOfNews);
            tvTimeOfNews = (TextView) itemView.findViewById(R.id.tvTimeOfNews);

            if (viewType == POSITION_THUMBNAIL) {
                ivNewsThumbnail = (ImageView) itemView.findViewById(R.id.ivNewsThumbnail);
            }

            mContext = context;

            rlNewsGroup.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {

            Intent detailNewsIntent = new Intent(mContext, ClubNewsDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("club_news_content", clubNewsDataArrayList.get(getAdapterPosition()));
            bundle.putInt("iPosition", getAdapterPosition());
            detailNewsIntent.putExtras(bundle);
            ((ClubNewsActivity) mContext).startActivityForResult(detailNewsIntent, 111);
        }
    }
}

