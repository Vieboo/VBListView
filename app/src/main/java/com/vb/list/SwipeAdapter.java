package com.vb.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vieboo on 2016/5/25.
 */
public class SwipeAdapter extends BaseAdapter {

    public interface CollectionItemListener {
        int FLAG_ADD = 1, FLAG_DELETE = 2;
        void itemClick(String str);
        void swipeClick(int flag, String str);
    }

    private CollectionItemListener collectionItemListener;
    private List<SwipeLayout> swipeLayoutList = new ArrayList<>();
    private boolean hasOpened = false;
    private Context mContext;

    public SwipeAdapter(Context context, CollectionItemListener listener) {
        this.mContext = context;
        this.collectionItemListener = listener;
    }

    @Override
    public int getCount() {
        return 20;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_swipe_item, null);
            holder.swipe = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
            holder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);

            holder.swipe.addSwipeDenier(new MSwipeDenier(holder.swipe));
            holder.swipe.addSwipeListener(new MSwiperListener());
            swipeLayoutList.add(holder.swipe);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasOpened) {
                    hasOpened = false;
                    return ;
                }
                collectionItemListener.itemClick(position + "");
            }
        });

        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionItemListener.swipeClick(collectionItemListener.FLAG_ADD, position + "");
                cleanList(null);
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionItemListener.swipeClick(collectionItemListener.FLAG_DELETE, position + "");
                cleanList(null);
            }
        });

        return convertView;
    }


    class ViewHolder {
        SwipeLayout swipe;
        LinearLayout ll_main;
        TextView tv_add;
        TextView tv_delete;
    }

    public void cleanList(SwipeLayout layout) {
        for(SwipeLayout s : swipeLayoutList) {
            if(s != layout) {
                s.close();
            }
        }
    }

    class MSwipeDenier implements SwipeLayout.SwipeDenier {

        SwipeLayout swipe;

        MSwipeDenier(SwipeLayout sl) {
            this.swipe = sl;
        }

        @Override
        public boolean shouldDenySwipe(MotionEvent ev) {
            cleanList(swipe);
            return false;
        }
    }

    class MSwiperListener implements SwipeLayout.SwipeListener {

        @Override
        public void onStartOpen(SwipeLayout layout) {

        }

        @Override
        public void onOpen(SwipeLayout layout) {
            hasOpened = true;
        }

        @Override
        public void onStartClose(SwipeLayout layout) {

        }

        @Override
        public void onClose(SwipeLayout layout) {
            hasOpened = false;
        }

        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

        }
    }
}
