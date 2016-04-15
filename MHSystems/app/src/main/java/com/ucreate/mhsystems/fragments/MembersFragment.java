package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display
 * <br>NEWS
 * <br>tabs content on 12/23/2015.
 */


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.MemberDetailActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.MembersAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.util.pojo.AJsonParamsMembers;
import com.ucreate.mhsystems.util.pojo.MembersAPI;
import com.ucreate.mhsystems.util.pojo.MembersData;
import com.ucreate.mhsystems.util.pojo.MembersItems;
import com.ucreate.mhsystems.util.pojo.MembersList;
import com.ucreate.mhsystems.utils.CircularContactView;
import com.ucreate.mhsystems.utils.async_task_thread_pool.AsyncTaskEx;
import com.ucreate.mhsystems.utils.async_task_thread_pool.AsyncTaskThreadPool;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import lb.library.PinnedHeaderListView;
import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MembersFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MembersFragment.class.getSimpleName();
    ArrayList<MembersData> membersDatas = new ArrayList<>();

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    //  ListView lvMembers;

    MembersAdapter membersAdapter;

    //Create instance of Model class MembersItems.
    MembersItems membersItems;
    AJsonParamsMembers aJsonParamsMembers;

    //List of type books this list will store type Book which is our data model
    private MembersAPI membersAPI;


    //Members list demo.
    private PinnedHeaderListView mListView;
    AlphabaticalListAdapter mAdapter = null;
    public LayoutInflater mInflater;

    /**
     * Implements a field to define Members List click event
     * to display detail of Member.
     */
    private AdapterView.OnItemClickListener mListMemberListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(LOG_TAG,""+membersDatas.get(0).getMembersList());
            Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
            //intent.putExtra(ApplicationGlobal.KEY_MEMBER_ID, membersDatas.get(0).getMembersList().get(position).);
            intent.putExtra(ApplicationGlobal.KEY_MEMBER_ID, membersDatas.get(0).getMembersList().get(0).getMemberID());
            startActivity(intent);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());

        mRootView = inflater.inflate(R.layout.fragment_members, container, false);

        //lvMembers = (ListView) mRootView.findViewById(R.id.lvMembers);
        mListView = (PinnedHeaderListView) mRootView.findViewById(R.id.lvMembersList);

        //Set Members list click listener.
        mListView.setOnItemClickListener(mListMemberListener);

        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {

            /**
             *  Check internet connection before hitting server request.
             */
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                //Method to hit Members list API.
                requestMemberService();
            } else {
                ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
            }
        }
    }


    /**
     * Implement a method to hit Members
     * web service to get response.
     */
    public void requestMemberService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsMembers = new AJsonParamsMembers();
        aJsonParamsMembers.setCallid("1456315336575");
        aJsonParamsMembers.setVersion(1);
        aJsonParamsMembers.setMemberid(10784);
        aJsonParamsMembers.setGenderFilter(MembersTabFragment.iMemberType);

        membersAPI = new MembersAPI(44118078, "GetAllMembers", aJsonParamsMembers, "WEBSERVICES", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getMembers(membersAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();

                ((BaseActivity) getActivity()).showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<MembersItems>() {
        }.getType();
        membersItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        membersDatas.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersItems.getMessage().equalsIgnoreCase("Success")) {

                membersDatas.add(membersItems.getData());

                if (membersDatas.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

//                    membersAdapter = new MembersAdapter(getActivity(), membersDatas.get(0).getMembersList());
//                    lvMembers.setAdapter(membersAdapter);

                    setMembersListAdapter(membersDatas.get(0).getMembersList());

                    Log.e(LOG_TAG, "getMembersList : " + membersDatas.get(0).getMembersList().size());
                    Log.e(LOG_TAG, "getMember().getDisplayName : " + membersDatas.get(0).getMember().getDisplayName());
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(membersItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
//                Dismiss progress dialog.
                ((BaseActivity) getActivity()).hideProgress();
            }
        }, 500);

    }


    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param membersList
     */
    private void setMembersListAdapter(ArrayList<MembersList> membersList) {
        //Members list demo.
       Collections.sort(membersList, new Comparator<MembersList>() {
            @Override
            public int compare(MembersList lhs, MembersList rhs) {
                char lhsFirstLetter = TextUtils.isEmpty(lhs.getDisplayName()) ? ' ' : lhs.getDisplayName().charAt(0);
                char rhsFirstLetter = TextUtils.isEmpty(rhs.getDisplayName()) ? ' ' : rhs.getDisplayName().charAt(0);
                int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                if (firstLetterComparison == 0)
                    return lhs.getDisplayName().compareTo(rhs.getDisplayName());
                return firstLetterComparison;
            }
        });

        mAdapter = new AlphabaticalListAdapter(membersList);

        int pinnedHeaderBackgroundColor = getResources().getColor(getResIdFromAttribute(getActivity(), android.R.attr.colorBackground));
        mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
        mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
        mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mListView, false));
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mAdapter);
        mListView.setEnableHeaderTransparencyChanges(false);
//            mAdapter.getFilter().filter(mQueryText,new FilterListener() ...
//        You can also perform operations on selected item by using :
    }


    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Implements a method to get Background theme color
     * to set same on Listview.
     */
    public static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0)
            return 0;
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }

    // ////////////////////////////////////////////////////////////
// ContactsAdapter //
// //////////////////
    class AlphabaticalListAdapter extends SearchablePinnedHeaderListViewAdapter<MembersList> {
        private ArrayList<MembersList> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        private final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);


        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        public AlphabaticalListAdapter(final ArrayList<MembersList> contacts) {
            setData(contacts);

            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<MembersList> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<MembersList> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final MembersList contactEntity : contacts)
                    contactNames.add(contactEntity.getDisplayName());
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            final View rootView;
            if (convertView == null) {
                holder = new ViewHolder();

                rootView = mInflater.inflate(R.layout.list_item_alphabets_member, parent, false);

                holder.friendProfileCircularContactView = (CircularContactView) rootView
                        .findViewById(R.id.listview_item__friendPhotoImageView);

                holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);

                holder.friendName = (TextView) rootView
                        .findViewById(R.id.listview_item__friendNameTextView);
                holder.tvPlayHCapStr = (TextView) rootView
                        .findViewById(R.id.tvPlayHCapStr);

                holder.headerView = (TextView) rootView.findViewById(R.id.header_text);

                rootView.setTag(holder);
            } else {
                rootView = convertView;
                holder = (ViewHolder) rootView.getTag();
            }
            final MembersList contact = getItem(position);
            final String displayName = contact.getDisplayName();
            holder.friendName.setText(displayName);
            holder.tvPlayHCapStr.setText(contact.getPlayHCapStr());

//            boolean hasPhoto = !TextUtils.isEmpty(contact.getPlayHCapStr());
//            if (holder.updateTask != null && !holder.updateTask.isCancelled())
//                holder.updateTask.cancel(true);
//            final Bitmap cachedBitmap = hasPhoto ? ImageCache.INSTANCE.getBitmapFromMemCache(contact.getPlayHCapStr()) : null;
//            if (cachedBitmap != null)
//                holder.friendProfileCircularContactView.setImageBitmap(cachedBitmap);
//            else {
//                final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position
//                        % PHOTO_TEXT_BACKGROUND_COLORS.length];
//                if (TextUtils.isEmpty(displayName))
//                    holder.friendProfileCircularContactView.setImageResource(R.drawable.background_pressed_c0995b,
//                            backgroundColorToUse);
//                else {
//                    final String characterToShow = TextUtils.isEmpty(displayName) ? "" : displayName.substring(0, 1).toUpperCase(Locale.getDefault());
//                    holder.friendProfileCircularContactView.setTextAndBackgroundColor(contact.getPlayHCapStr(), backgroundColorToUse);
//                }
//                if (hasPhoto) {
//                    holder.updateTask = new AsyncTaskEx<Void, Void, Bitmap>() {
//
//                        @Override
//                        public Bitmap doInBackground(final Void... params) {
//                            if (isCancelled())
//                                return null;
//                            final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(getActivity(), contact.getDisplayName(), CONTACT_PHOTO_IMAGE_SIZE);
//                            if (b != null)
//                                return ThumbnailUtils.extractThumbnail(b, CONTACT_PHOTO_IMAGE_SIZE,
//                                        CONTACT_PHOTO_IMAGE_SIZE);
//                            return null;
//                        }
//
//                        @Override
//                        public void onPostExecute(final Bitmap result) {
//                            super.onPostExecute(result);
//                            if (result == null)
//                                return;
//                            ImageCache.INSTANCE.addBitmapToCache(contact.getPlayHCapStr(), result);
//                            holder.friendProfileCircularContactView.setImageBitmap(result);
//                        }
//                    };
//                    mAsyncTaskThreadPool.executeAsyncTask(holder.updateTask);
//                }
//            }
            bindSectionHeader(holder.headerView, null, position);
            return rootView;
        }

        @Override
        public boolean doFilter(final MembersList item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.getDisplayName();
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<MembersList> getOriginalList() {
            return mContacts;
        }

        // /////////////////////////////////////////////////////////////////////////////////////
// ViewHolder //
// /////////////
        class ViewHolder {
            public CircularContactView friendProfileCircularContactView;
            TextView friendName, headerView, tvPlayHCapStr;
            public AsyncTaskEx<Void, Void, Bitmap> updateTask;
        }
    }
}