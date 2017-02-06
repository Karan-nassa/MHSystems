package com.mh.systems.redlibbets.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mh.systems.redlibbets.R;
import com.mh.systems.redlibbets.models.Contact;
import com.mh.systems.redlibbets.utils.CircularContactView;
import com.mh.systems.redlibbets.utils.ContactImageUtil;
import com.mh.systems.redlibbets.utils.ImageCache;
import com.mh.systems.redlibbets.utils.async_task_thread_pool.AsyncTaskEx;
import com.mh.systems.redlibbets.utils.async_task_thread_pool.AsyncTaskThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;

// ////////////////////////////////////////////////////////////
// ContactsAdapter //
// //////////////////
class ContactsAdapter extends SearchablePinnedHeaderListViewAdapter<Contact> {
    private ArrayList<Contact> mContacts;
    private final int CONTACT_PHOTO_IMAGE_SIZE;
    private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
    private final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);

    Context mContext;
    LayoutInflater mInflater = null;

    @Override
    public CharSequence getSectionTitle(int sectionIndex) {
        return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
    }

    public ContactsAdapter(final ArrayList<Contact> contacts, Context mContext) {
        setData(contacts);

        this.mContext = mContext;

        mInflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PHOTO_TEXT_BACKGROUND_COLORS = mContext.getResources().getIntArray(R.array.contacts_text_background_colors);
        CONTACT_PHOTO_IMAGE_SIZE = mContext.getResources().getDimensionPixelSize(
                R.dimen.list_item__contact_imageview_size);
    }

    public void setData(final ArrayList<Contact> contacts) {
        this.mContacts = contacts;
        final String[] generatedContactNames = generateContactNames(contacts);
        setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
    }

    private String[] generateContactNames(final List<Contact> contacts) {
        final ArrayList<String> contactNames = new ArrayList<String>();
        if (contacts != null)
            for (final Contact contactEntity : contacts)
                contactNames.add(contactEntity.getFullName());
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
            holder.headerView = (TextView) rootView.findViewById(R.id.header_text);
            rootView.setTag(holder);
        } else {
            rootView = convertView;
            holder = (ViewHolder) rootView.getTag();
        }
        final Contact contact = getItem(position);
        final String displayName = contact.getFullName();
        holder.friendName.setText(displayName);
        boolean hasPhoto = !TextUtils.isEmpty(contact.getPhotoId());
        if (holder.updateTask != null && !holder.updateTask.isCancelled())
            holder.updateTask.cancel(true);
        final Bitmap cachedBitmap = hasPhoto ? ImageCache.INSTANCE.getBitmapFromMemCache(contact.getPhotoId()) : null;
        if (cachedBitmap != null)
            holder.friendProfileCircularContactView.setImageBitmap(cachedBitmap);
        else {
            final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position
                    % PHOTO_TEXT_BACKGROUND_COLORS.length];
            if (TextUtils.isEmpty(displayName))
                holder.friendProfileCircularContactView.setImageResource(R.drawable.background_pressed_c0995b,
                        backgroundColorToUse);
            else {
                final String characterToShow = TextUtils.isEmpty(displayName) ? "" : displayName.substring(0, 1).toUpperCase(Locale.getDefault());
                holder.friendProfileCircularContactView.setTextAndBackgroundColor(characterToShow, backgroundColorToUse);
            }
            if (hasPhoto) {
                holder.updateTask = new AsyncTaskEx<Void, Void, Bitmap>() {

                    @Override
                    public Bitmap doInBackground(final Void... params) {
                        if (isCancelled())
                            return null;
                        final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(mContext, contact.getPhotoId(), CONTACT_PHOTO_IMAGE_SIZE);
                        if (b != null)
                            return ThumbnailUtils.extractThumbnail(b, CONTACT_PHOTO_IMAGE_SIZE,
                                    CONTACT_PHOTO_IMAGE_SIZE);
                        return null;
                    }

                    @Override
                    public void onPostExecute(final Bitmap result) {
                        super.onPostExecute(result);
                        if (result == null)
                            return;
                        ImageCache.INSTANCE.addBitmapToCache(contact.getPhotoId(), result);
                        holder.friendProfileCircularContactView.setImageBitmap(result);
                    }
                };
                mAsyncTaskThreadPool.executeAsyncTask(holder.updateTask);
            }
        }
        bindSectionHeader(holder.headerView, null, position);
        return rootView;
    }

    @Override
    public boolean doFilter(final Contact item, final CharSequence constraint) {
        if (TextUtils.isEmpty(constraint))
            return true;
        final String displayName = item.getFullName();
        return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()));
    }

    @Override
    public ArrayList<Contact> getOriginalList() {
        return mContacts;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
// ViewHolder //
// /////////////
    class ViewHolder {
        public CircularContactView friendProfileCircularContactView;
        TextView friendName, headerView;
        public AsyncTaskEx<Void, Void, Bitmap> updateTask;
    }
}

