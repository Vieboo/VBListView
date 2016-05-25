package com.vb.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vb.list.pinned.PinnedSectionListView;

/**
 * Created by Vieboo on 2016/5/25.
 */
public class PinnedSectionAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;

    public PinnedSectionAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 0;
    }

    @Override
    public int getCount() {
        return 20 + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(isItemViewTypePinned(getItemViewType(position))) {
            return LayoutInflater.from(mContext).inflate(R.layout.layout_pinned_item_title, null);
        }
        else {
            if(null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_pinned_item, null);
                holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_item.setText("Item" + (position - 1));
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_item;
    }
}
