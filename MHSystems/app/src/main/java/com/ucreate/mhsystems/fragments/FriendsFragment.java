package com.ucreate.mhsystems.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.MemberDetailActivity;
import com.ucreate.mhsystems.activites.MembersActivity;
import com.ucreate.mhsystems.activites.MyAccountActivity;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.AJsonParamsFriends;
import com.ucreate.mhsystems.models.FriendsAPI;
import com.ucreate.mhsystems.models.FriendsData;
import com.ucreate.mhsystems.models.FriendsItems;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
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

/**
 * The {@link FriendsFragment} used to display the Member list
 * with {@link AlphabaticalListAdapter} indexing and {@link android.support.v7.widget.SearchView}
 * <p>
 *
 * @author karan@ucreate.co.in
 * @version 1.0
 * @since 12 May, 2016
 */
public class FriendsFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = FriendsFragment.class.getSimpleName();
    ArrayList<FriendsData> friendsDataArrayList = new ArrayList<>();

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    //List of type books this list will store type Book which is our data model
    private FriendsAPI friendsAPI;
    AJsonParamsFriends aJsonParamsFriends;

    //Create instance of Model class MembersItems.
    FriendsItems friendsItems;

    //Members list demo.
    private PinnedHeaderListView phlvFriends;
    public static AlphabaticalListAdapter mAdapter = null;
    public LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());

        viewRootFragment = inflater.inflate(R.layout.fragment_friends, container, false);

        phlvFriends = (PinnedHeaderListView) viewRootFragment.findViewById(R.id.phlvFriends);

        return viewRootFragment;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (isVisibleToUser) {
//
//            ((MembersActivity) getActivity()).setFragmentInstance(new FriendsFragment());
//
//            callWebService();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

        //Refresh data after REMOVE friend from detail screen.
       // if (MemberDetailActivity.isRefreshData) {
          ((MembersActivity) getActivity()).setFragmentInstance(new FriendsFragment());
            MemberDetailActivity.isRefreshData = false;

            callWebService();
       // }
    }

    /**
     * Implements a method to call web service after check
     * INTERNET connection.
     */
    public void callWebService() {

        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Members list API.
            requestFriendService();
        } else {
            ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Implement a method to hit Members
     * web service to get response.
     */
    public void requestFriendService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsFriends = new AJsonParamsFriends();
        aJsonParamsFriends.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsFriends.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsFriends.setMemberId(((MembersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));

        friendsAPI = new FriendsAPI(getClientId(), ((MembersActivity) getActivity()).getStraFriendCommand(), aJsonParamsFriends,  ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getFriends(friendsAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();

                ((BaseActivity) getActivity()).showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });

    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return ((MembersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "44118078");
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<FriendsItems>() {
        }.getType();
        friendsItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        friendsDataArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (friendsItems.getMessage().equalsIgnoreCase("Success")) {

                friendsDataArrayList.addAll(friendsItems.getData());

                if (friendsDataArrayList.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    setMembersListAdapter(friendsDataArrayList);
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(friendsItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }


    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param friendsDatas
     */
    private void setMembersListAdapter(ArrayList<FriendsData> friendsDatas) {

        //Members list demo.
        Collections.sort(friendsDatas, new Comparator<FriendsData>() {
            @Override
            public int compare(FriendsData lhs, FriendsData rhs) {
                char lhsFirstLetter = TextUtils.isEmpty(lhs.getNameRecord().getDisplayName()) ? ' ' : lhs.getNameRecord().getDisplayName().charAt(0);
                char rhsFirstLetter = TextUtils.isEmpty(rhs.getNameRecord().getDisplayName()) ? ' ' : rhs.getNameRecord().getDisplayName().charAt(0);
                int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                if (firstLetterComparison == 0)
                    return lhs.getNameRecord().getDisplayName().compareTo(rhs.getNameRecord().getDisplayName());
                return firstLetterComparison;
            }
        });

        mAdapter = new AlphabaticalListAdapter(friendsDatas);


        int pinnedHeaderBackgroundColor = (ContextCompat.getColor(getActivity(), getResIdFromAttribute(getActivity(), android.R.attr.colorBackground)));
        mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
        mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(getActivity(), R.color.pinned_header_text));
        phlvFriends.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, phlvFriends, false));
        phlvFriends.setAdapter(mAdapter);
        phlvFriends.setOnScrollListener(mAdapter);
        phlvFriends.setEnableHeaderTransparencyChanges(false);
        mAdapter.notifyDataSetChanged();
    }


    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Implements a method to get Background theme color
     * to set same on ListView.
     */
    public static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0)
            return 0;
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }

    ////////////////////////////
    // Alphabetical ListAdapter /
    // /////////////////////////
    public class AlphabaticalListAdapter extends SearchablePinnedHeaderListViewAdapter<FriendsData> {
        private ArrayList<FriendsData> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        public final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);

        @Override
        public int getCount() {
            return mContacts.size();
        }

        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        public AlphabaticalListAdapter(final ArrayList<FriendsData> contacts) {
            setData(contacts);

            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<FriendsData> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<FriendsData> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final FriendsData contactEntity : contacts)
                    contactNames.add(contactEntity.getNameRecord().getDisplayName());
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            final View rootView;
            final FriendsData contact;
            if (convertView == null) {

                ((MembersActivity) getActivity()).setFragmentInstance(new FriendsFragment());

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
            contact = getItem(position);
            if(contact != null) {
                final String displayName = contact.getNameRecord().getDisplayName();
                holder.friendName.setText(displayName);
                holder.tvPlayHCapStr.setText(contact.getHCapPlayStr());

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

                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
                        // intent.putExtra("A_COMMAND", "REMOVELINKTOMEMBER");
                        intent.putExtra("PASS_FROM", 2); // 1 means from Member Fragment and 2 for Friends Fragment.
                        intent.putExtra("SPINNER_ITEM", ((MembersActivity) getActivity()).getiWhichSpinnerItem());
                        intent.putExtra(ApplicationGlobal.KEY_MEMBER_ID, contact.getMemberID());
                        startActivity(intent);
                    }
                });
            }
            return rootView;
        }

        @Override
        public boolean doFilter(final FriendsData item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.getNameRecord().getDisplayName();
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<FriendsData> getOriginalList() {
            return mContacts;
        }

        // /////////////
        // ViewHolder //
        // /////////////
        class ViewHolder {
            public CircularContactView friendProfileCircularContactView;
            TextView friendName, headerView, tvPlayHCapStr;
            public AsyncTaskEx<Void, Void, Bitmap> updateTask;
        }
    }
}