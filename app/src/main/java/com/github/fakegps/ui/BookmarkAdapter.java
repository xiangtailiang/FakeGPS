package com.github.fakegps.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.fakegps.model.LocBookmark;
import com.tencent.fakegps.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Bookmark Adapter
 * Created by tiger on 7/23/16.
 */
public class BookmarkAdapter extends BaseAdapter {

    private Context mContext;
    private List<LocBookmark> mLocBookmarkList = new ArrayList<>();

    public BookmarkAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mLocBookmarkList.size();
    }

    @Override
    public LocBookmark getItem(int position) {
        return mLocBookmarkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_bookmark_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.loc = (TextView) convertView.findViewById(R.id.tv_loc);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LocBookmark item = getItem(position);
        viewHolder.name.setText(item.getName());
        viewHolder.loc.setText(item.getLocPoint().toString());

        return convertView;
    }

    public void setLocBookmarkList(List<LocBookmark> locBookmarkList) {
        if (locBookmarkList == null || locBookmarkList.size() <= 0) return;
        mLocBookmarkList.clear();
        mLocBookmarkList.addAll(locBookmarkList);
        notifyDataSetChanged();
    }

    public void addBookmark(LocBookmark bookmark) {
        mLocBookmarkList.add(bookmark);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mLocBookmarkList.clear();
    }


    class ViewHolder {
        TextView name;
        TextView loc;
    }
}
