package com.vb.list;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vb.list.pinned.PinnedSectionListView;
import com.vb.list.xlist.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vieboo on 2016/5/25.
 */
public class PinnedSectionXListActivity extends Activity implements XListView.IXListViewListener,
        Handler.Callback, AdapterView.OnItemClickListener {

    private PinnedSectionListView list_view;
    private PinnedSectionAdapter adapter;
    private TextView headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned);

        list_view = (PinnedSectionListView) findViewById(R.id.list_view);
        list_view.setShadowVisible(false);  //阴影
        list_view.setPullRefreshEnable(true);   //下拉刷新
        list_view.setPullLoadEnable(true);      //上拉加载
        list_view.setAutoLoadEnable(true);      //滑倒底部自动加载
        list_view.setRefreshTime(getTime());   //设置刷新时间
        list_view.setXListViewListener(this);

        headView = new TextView(this);
        headView.setText("HeadView");
        headView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                600));
        headView.setGravity(Gravity.CENTER);
        headView.setBackgroundColor(Color.YELLOW);
        list_view.addHeaderView(headView);
        adapter = new PinnedSectionAdapter(this);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position < 3) return ;
        Toast.makeText(this, "position--->" + (position - 3), Toast.LENGTH_SHORT).show();
    }

    public String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

}
