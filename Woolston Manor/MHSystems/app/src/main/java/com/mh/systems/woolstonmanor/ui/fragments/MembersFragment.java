package com.mh.systems.woolstonmanor.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.woolstonmanor.ui.activites.BaseActivity;
import com.mh.systems.woolstonmanor.ui.activites.MembersActivity;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.mh.systems.woolstonmanor.R;
import com.mh.systems.woolstonmanor.ui.activites.MemberDetailActivity;
import com.mh.systems.woolstonmanor.utils.constants.ApplicationGlobal;
import com.mh.systems.woolstonmanor.web.api.WebAPI;
import com.mh.systems.woolstonmanor.web.api.WebServiceMethods;
import com.mh.systems.woolstonmanor.web.models.AJsonParamsMembers;
import com.mh.systems.woolstonmanor.web.models.MembersAPI;
import com.mh.systems.woolstonmanor.web.models.MembersData;
import com.mh.systems.woolstonmanor.web.models.MembersItems;
import com.mh.systems.woolstonmanor.web.models.MembersList;
import com.mh.systems.woolstonmanor.utils.libAlphaIndexing.CircularContactView;
import com.mh.systems.woolstonmanor.utils.libAlphaIndexing.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.woolstonmanor.utils.libAlphaIndexing.async_task_thread_pool.AsyncTaskThreadPool;


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
 * The {@link MembersFragment} used to display the Member list
 * with {@link AlphabaticalListAdapter} indexing and {@link android.support.v7.widget.SearchView}
 * <p>
 *
 * @author karan@ucreate.co.in
 * @version 1.0
 * @since 1 May, 2016
 */
public class MembersFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MembersFragment.class.getSimpleName();
    ArrayList<MembersData> membersDatas = new ArrayList<>();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    //Create instance of Model class MembersItems.
    MembersItems membersItems;

    //List of type books this list will store type Book which is our data model
    private MembersAPI membersAPI;
    AJsonParamsMembers aJsonParamsMembers;

    //Members list demo.
    private PinnedHeaderListView mPinnedHeaderListView;
    public static AlphabaticalListAdapter mAdapter = null;
    public LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());

        viewRootFragment = inflater.inflate(R.layout.fragment_members, container, false);

        mPinnedHeaderListView = (PinnedHeaderListView) viewRootFragment.findViewById(R.id.lvMembersList);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            ((MembersActivity)getActivity()).setiTabPosition(0);

            /**
             *  Check internet connection before hitting server request.
             */
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                //((MembersActivity) getActivity()).setFragmentInstance(new MembersFragment());

                //Method to hit Members list API.
                requestMemberService();
                ((MembersActivity) getActivity()).updateNoInternetUI(true);
            } else {
                ((MembersActivity) getActivity()).updateNoInternetUI(false);
                //((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
            }
        }
    }

    /**
     * Implements a field to define Members List click event
     * to display detail of Member.
     */
    public AdapterView.OnItemClickListener mListMemberListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Log.e(LOG_TAG, "Member ID " + membersDatas.get(0).getMembersList().get(position).getMemberID());

            Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
            intent.putExtra(ApplicationGlobal.KEY_MEMBER_ID, membersDatas.get(0).getMembersList().get(position).getMemberID());
            startActivity(intent);
        }
    };



    /**
     * Implement a method to hit Members web service to get response.
     */
    public void requestMemberService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsMembers = new AJsonParamsMembers();
        aJsonParamsMembers.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsMembers.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsMembers.setMemberid(((MembersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));
        aJsonParamsMembers.setGenderFilter(MembersTabFragment.iMemberType);

        membersAPI = new MembersAPI(getClientId(), "GetAllMembers", aJsonParamsMembers, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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

                ((BaseActivity) getActivity()).showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return ((MembersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    public void updateSuccessResponse(JsonObject jsonObject) {

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
                    ((MembersActivity) getActivity()).updateNoDataUI(false, 0);
                    // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    ((MembersActivity) getActivity()).updateNoDataUI(true, 0);
                    setMembersListAdapter(membersDatas.get(0).getMembersList());
                }
            } else {
                ((MembersActivity) getActivity()).updateNoDataUI(false, 0);
                //If web service not respond in any case.
                // ((BaseActivity) getActivity()).showAlertMessage(membersItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            ((MembersActivity) getActivity()).reportRollBarException(getActivity().getClass().getSimpleName(), e.toString());
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();

       /* *//**
         * Hide alert dialog after 1500Ms.
         *//*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Dismiss progress dialog.
                ((BaseActivity) getActivity()).hideProgress();
            }
        }, 1500);*/

    }


    /**
     * Implements a method to set Members list in Adapter.
     *
     * @param membersList
     */
    public void setMembersListAdapter(ArrayList<MembersList> membersList) {
        //Members list demo.
        Collections.sort(membersList, new Comparator<MembersList>() {
            @Override
            public int compare(MembersList lhs, MembersList rhs) {
                char lhsFirstLetter = TextUtils.isEmpty(lhs.getFullName()/*getFullName()*/) ? ' ' : lhs.getFullName()/*getFullName()*/.charAt(0);
                char rhsFirstLetter = TextUtils.isEmpty(rhs.getFullName()/*getFullName()*/) ? ' ' : rhs.getFullName()/*getFullName()*/.charAt(0);
                int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                if (firstLetterComparison == 0)
                    return lhs.getFullName()/*getFullName()*/.compareTo(rhs.getFullName()/*getFullName()*/);
                return firstLetterComparison;
            }
        });

        mAdapter = new AlphabaticalListAdapter(membersList);

        //int pinnedHeaderBackgroundColor = (ContextCompat.getColor(getActivity(), getResIdFromAttribute(getActivity(),  android.R.attr.colorBackground)));
        mAdapter.setPinnedHeaderBackgroundColor(Color.parseColor("#F9F8F7")/*pinnedHeaderBackgroundColor*/);
        mAdapter.setPinnedHeaderTextColor(ContextCompat.getColor(getActivity(), R.color.color9B9B9B));
        mPinnedHeaderListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mPinnedHeaderListView, false));
        mPinnedHeaderListView.setAdapter(mAdapter);
        mPinnedHeaderListView.setOnScrollListener(mAdapter);
        mPinnedHeaderListView.setEnableHeaderTransparencyChanges(false);
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

    ////////////////////////////
    // AlphabaticalListAdapter /
    // /////////////////////////
    public class AlphabaticalListAdapter extends SearchablePinnedHeaderListViewAdapter<MembersList> {
        private ArrayList<MembersList> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        public final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);


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
                    contactNames.add(contactEntity.getFullName()/*getFullName()*/);
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            final View rootView;
            final MembersList contact;
            if (convertView == null) {

                ((MembersActivity) getActivity()).setFragmentInstance(new MembersFragment());

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
            final String displayName = contact.getFullName()/*getFullName()*/;
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
//                            final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(getActivity(), contact.getFullName(), CONTACT_PHOTO_IMAGE_SIZE);
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
                    // intent.putExtra("A_COMMAND", "GETMEMBER");
                    intent.putExtra("PASS_FROM", 1); // 1 means from Member Fragment and 2 for friends Fragment.
                    intent.putExtra(ApplicationGlobal.KEY_MEMBER_ID, contact.getMemberID());
                    startActivity(intent);
                }
            });
            return rootView;
        }

        @Override
        public boolean doFilter(final MembersList item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.getFullName()/*getFullName()*/;
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<MembersList> getOriginalList() {
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