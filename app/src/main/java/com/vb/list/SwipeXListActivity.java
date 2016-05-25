package com.vb.list;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.vb.list.xlist.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vieboo on 2016/5/25.
 */
public class SwipeXListActivity extends Activity implements SwipeAdapter.CollectionItemListener,
        XListView.IXListViewListener, Handler.Callback {

    private XListView list_view;
    private SwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        list_view = (XListView) findViewById(R.id.list_view);
        list_view.setPullRefreshEnable(true);   //下拉刷新
        list_view.setPullLoadEnable(true);      //上拉加载
        list_view.setAutoLoadEnable(true);      //滑倒底部自动加载
        list_view.setRefreshTime(getTime());   //设置刷新时间
        list_view.setXListViewListener(this);

        adapter = new SwipeAdapter(this, this);
        list_view.setAdapter(adapter);

        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                adapter.cleanList(null);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void itemClick(String str) {
        Toast.makeText(this, "click---->" + str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeClick(int flag, String str) {
        Toast.makeText(this, flag + "---->" + str, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onRefresh() {
        list_view.setRefreshTime(getTime());
        new Handler(this).sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadMore() {
        new Handler(this).sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what == 0) list_view.stopRefresh();
        if(msg.what == 1) list_view.stopLoadMore();
        return false;
    }


    public String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
