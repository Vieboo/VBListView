package com.vb.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vb.list.hscroll.MyHScrollView;

import java.util.List;

/**
 * Created by Vieboo on 2016/5/25.
 */
public class HorizontalScrollAdapter extends BaseAdapter {

    Context mContext;
    List<String> dataList;
    View headView;

    MyHScrollView head_scroll;

    public HorizontalScrollAdapter(Context context, List<String> list, View head) {
        this.mContext = context;
        this.dataList = list;
        this.headView = head;
        head_scroll = (MyHScrollView) headView.findViewById(R.id.head_scroll);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, null, false);
            holder.list_scroll = (MyHScrollView) convertView.findViewById(R.id.list_scroll);
            head_scroll.AddOnScrollChangedListener(new OnScrollChangedListenerImp(holder.list_scroll));
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    /**
     * 加ViewHolder防止数据多了滑动列表位置错乱
     */
    class ViewHolder {
        MyHScrollView list_scroll;
    }

    class OnScrollChangedListenerImp implements MyHScrollView.OnScrollChangedListener {
        MyHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
            mScrollViewArg = scrollViewar;
        }

        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l, t);
        }
    }

}
