package com.mh.systems.demoapp.fragments;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.activites.BaseActivity;
import com.mh.systems.demoapp.activites.EligiblePlayersActivity;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.competitionsEntry.AJsonParamsEligiblePlayers;
import com.mh.systems.demoapp.models.competitionsEntry.CompEligiblePlayersAPI;
import com.mh.systems.demoapp.models.competitionsEntry.CompEligiblePlayersResponse;
import com.mh.systems.demoapp.models.competitionsEntry.EligibleMember;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.mh.systems.demoapp.utils.CircularContactView;
import com.mh.systems.demoapp.utils.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.demoapp.utils.async_task_thread_pool.AsyncTaskThreadPool;
import com.newrelic.com.google.gson.reflect.TypeToken;

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
 * The {@link EligibleMemberFragment} used to display the eligible players list
 * for book paid COMPETITION with {@link AlphabaticalListAdapter} indexing
 * and {@link android.support.v7.widget.SearchView}.
 * <p>
 *
 * @author karan@ucreate.co.in
 * @version 1.0
 * @since 1 May, 2016
 */
public class EligibleMemberFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = EligibleMemberFragment.class.getSimpleName();
    ArrayList<EligibleMember> eligibleMemberArrayList = new ArrayList<>();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    //Create instance of Model class MembersItems.
    CompEligiblePlayersResponse compEligiblePlayersResponse;

    //List of type books this list will store type Book which is our data model
    private CompEligiblePlayersAPI compEligiblePlayersAPI;
    AJsonParamsEligiblePlayers aJsonParamsEligiblePlayers;

    //Members list demo.
    private PinnedHeaderListView mPinnedHeaderListView;
    public static AlphabaticalListAdapter mAdapter = null;
    public LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());

        viewRootFragment = inflater.inflate(R.layout.fragment_members, container, false);

        mPinnedHeaderListView = (PinnedHeaderListView) viewRootFragment.findViewById(R.id.lvMembersList);

        ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());

        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Members list API.
            requestMemberService();
            ((EligiblePlayersActivity) getActivity()).updateNoInternetUI(true);
        } else {
            ((EligiblePlayersActivity) getActivity()).updateNoInternetUI(false);
        }

        return viewRootFragment;
    }

    /**
     * Implement a method to hit Members web service to get response.
     */
    public void requestMemberService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsEligiblePlayers = new AJsonParamsEligiblePlayers();
        aJsonParamsEligiblePlayers.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsEligiblePlayers.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsEligiblePlayers.setMemberId(((EligiblePlayersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784"));
        aJsonParamsEligiblePlayers.setEventId(((EligiblePlayersActivity) getActivity()).getStrEventId());

        compEligiblePlayersAPI = new CompEligiblePlayersAPI(getClientId(), "GETCOMPELIGIBLEPLAYERS", aJsonParamsEligiblePlayers, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getEligiblePlayersList(compEligiblePlayersAPI, new Callback<JsonObject>() {
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
        return ((EligiblePlayersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CompEligiblePlayersResponse>() {
        }.getType();
        compEligiblePlayersResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        eligibleMemberArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (compEligiblePlayersResponse.getMessage().equalsIgnoreCase("Success")) {
                eligibleMemberArrayList.addAll(compEligiblePlayersResponse.getData().getEligibleMembers());

                if (eligibleMemberArrayList.size() == 0) {
                    ((EligiblePlayersActivity) getActivity()).updateNoDataUI(false);
                    // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    ((EligiblePlayersActivity) getActivity()).updateNoDataUI(true);
                    setMembersListAdapter(eligibleMemberArrayList);
                }
            } else {
                ((EligiblePlayersActivity) getActivity()).updateNoDataUI(false);
                //If web service not respond in any case.
                // ((BaseActivity) getActivity()).showAlertMessage(membersItems.getMessage());
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
     * @param eligibleMemberArrayList
     */
    private void setMembersListAdapter(ArrayList<EligibleMember> eligibleMemberArrayList) {
        //Members list demo.
        Collections.sort(eligibleMemberArrayList, new Comparator<EligibleMember>() {
            @Override
            public int compare(EligibleMember lhs, EligibleMember rhs) {
                char lhsFirstLetter = TextUtils.isEmpty(lhs.getFullName()) ? ' ' : lhs.getFullName().charAt(0);
                char rhsFirstLetter = TextUtils.isEmpty(rhs.getFullName()) ? ' ' : rhs.getFullName().charAt(0);
                int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                if (firstLetterComparison == 0)
                    return lhs.getFullName().compareTo(rhs.getFullName());
                return firstLetterComparison;
            }
        });

        mAdapter = new AlphabaticalListAdapter(eligibleMemberArrayList);

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
    public class AlphabaticalListAdapter extends SearchablePinnedHeaderListViewAdapter<EligibleMember> {
        private ArrayList<EligibleMember> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        public final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);


        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        public AlphabaticalListAdapter(final ArrayList<EligibleMember> contacts) {
            setData(contacts);

            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<EligibleMember> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<EligibleMember> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final EligibleMember contactEntity : contacts)
                    contactNames.add(contactEntity.getFullName()/*getFullName()*/);
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            ViewHolder holder;
            final View rootView;
            final EligibleMember contact;


            ((EligiblePlayersActivity) getActivity()).setFragmentInstance(new EligibleMemberFragment());

            holder = new ViewHolder();

            rootView = mInflater.inflate(R.layout.list_item_alpha_eligible_member, parent, false);

            holder.friendProfileCircularContactView = (CircularContactView) rootView.findViewById(R.id.listview_item__friendPhotoImageView);
            holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);

            holder.friendName = (TextView) rootView.findViewById(R.id.listview_item__friendNameTextView);
            holder.tvPlayHCapStr = (TextView) rootView.findViewById(R.id.tvPlayHCapStr);
            holder.headerView = (TextView) rootView.findViewById(R.id.header_text);

            holder.cbSelectedMember = (CheckBox) rootView.findViewById(R.id.cbSelectedMember);

            rootView.setTag(holder);

            holder = (ViewHolder) rootView.getTag();
            contact = getItem(position);
            final String displayName = contact.getFullName();
            holder.friendName.setText(displayName);
            holder.tvPlayHCapStr.setText(contact.getPlayHCapStr());
            holder.cbSelectedMember.setChecked(contact.getIsMemberSelected());

            holder.cbSelectedMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if (isChecked) {
                        if (EligiblePlayersActivity.iTotalAddedMembers > 0) {
                            ((EligiblePlayersActivity) getActivity()).addMemberToList(eligibleMemberArrayList.get(position).getMemberID());
                            // selectedMemberList.add(eligibleMemberArrayList.get(position).getMemberID());
                            //displaySelecteMemberList();
                        }
                    } else {
                            ((EligiblePlayersActivity) getActivity()).removeMemberFromList(eligibleMemberArrayList.get(position).getMemberID());
                            //selectedMemberList.remove();
                            //displaySelecteMemberList();

                        eligibleMemberArrayList.get(position).setIsMemberSelected(isChecked);
                    }
                }
            });

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

           /* rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((EligiblePlayersActivity) getActivity()).passMemberName(contact.getFullName());
                }
            });*/
            return rootView;
        }

        @Override
        public boolean doFilter(final EligibleMember item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.getFullName()/*getFullName()*/;
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<EligibleMember> getOriginalList() {
            return mContacts;
        }

        // /////////////
        // ViewHolder //
        // /////////////
        class ViewHolder {
            public CircularContactView friendProfileCircularContactView;
            TextView friendName, headerView, tvPlayHCapStr;
            CheckBox cbSelectedMember;
            public AsyncTaskEx<Void, Void, Bitmap> updateTask;
        }
    }
}