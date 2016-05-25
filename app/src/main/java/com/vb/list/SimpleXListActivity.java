package com.vb.list;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.vb.list.xlist.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vieboo on 2016/5/25.
 */
public class SimpleXListActivity extends Activity implements Handler.Callback,
        XListView.IXListViewListener, AdapterView.OnItemClickListener {

    private XListView list_view;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        list_view = (XListView) findViewById(R.id.list_view);
        list_view.setPullRefreshEnable(true);   //下拉刷新
        list_view.setPullLoadEnable(true);      //上拉加载
        list_view.setAutoLoadEnable(true);      //滑倒底部自动加载
        list_view.setRefreshTime(getTime());   //设置刷新时间
        list_view.setXListViewListener(this);
        list_view.setOnItemClickListener(this);


        List<String> array = new ArrayList<>();
        for(int i=0; i<20; i++) {
            array.add("ABC");
        }
        adapter = new ArrayAdapter<String>(
                SimpleXListActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                array);
        list_view.setAdapter(adapter);
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
        if(position == 0) return ;
        list_view.autoRefresh();    //启动下拉刷新
        Toast.makeText(SimpleXListActivity.this,
                "positon-->" + position + "value--->" + adapter.getItem(position - 1),
                Toast.LENGTH_SHORT).show();
    }


    public String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
