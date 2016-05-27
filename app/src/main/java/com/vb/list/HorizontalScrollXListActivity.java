package com.vb.list;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
public class HorizontalScrollXListActivity extends Activity implements XListView.IXListViewListener,
        Handler.Callback{

    private GestureDetector mGesture;
    // 事件状态
    private final char FLING_NORMAL = 0;
    private final char FLING_CLICK = 1;
    private final char FLING_LEFT = 2;
    private final char FLING_RIGHT = 3;
    private char flingState = FLING_NORMAL;


    private XListView list_view;
    private LinearLayout head;
    private HorizontalScrollAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscroll);

        mGesture = new GestureDetector(this, mOnGesture);

        head = (LinearLayout) findViewById(R.id.head);
        head.setFocusable(true);
        head.setClickable(true);
        head.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        List<String> dataList = new ArrayList<>();
        int i=0;
        while(i < 50) {
            i++;
            dataList.add("");
        }
        adapter = new HorizontalScrollAdapter(this, dataList, head);
        list_view = (XListView) findViewById(R.id.list_view);
        list_view.setPullRefreshEnable(true);   //下拉刷新
        list_view.setPullLoadEnable(true);      //上拉加载
        list_view.setAutoLoadEnable(true);      //滑倒底部自动加载
        list_view.setRefreshTime(getTime());   //设置刷新时间
        list_view.setXListViewListener(this);
        list_view.setAdapter(adapter);
        list_view.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
    }

    /**
     * 手势
     */
    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {
            if(e2.getX()-e1.getX()>20){
                flingState = FLING_LEFT;

            }else if(e1.getX()-e2.getX()>20){
                flingState = FLING_RIGHT;
            }
            return false;
        }

        /** 滚动 */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            flingState = FLING_CLICK;
            return false;
        }
    };

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


    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

        float lastX = 0;
        float lastY = 0;
        private boolean isClick = false;

        @Override
        public boolean onTouch(View arg0, MotionEvent ev) {
            //判断是否是点击
            float tempX = ev.getX();
            float tempY = ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = tempX;
                    lastY = tempY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(Math.abs(lastX - tempX) > 2 || Math.abs(lastY - tempY) > 2) {
                        isClick = false;
                        adapter.setTouchPosition(-1);
                    }else {
                        isClick = true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(Math.abs(lastX - tempX) > 2 || Math.abs(lastY - tempY) > 2) {
                        isClick = false;
                        adapter.setTouchPosition(-1);
                    }else {
                        isClick = true;
                    }
                    if(isClick && adapter.getTouchPosition() >= 0) {
                        Toast.makeText(HorizontalScrollXListActivity.this, "position--->" + adapter.getTouchPosition(), Toast.LENGTH_SHORT).show();
                        isClick = false;
                        adapter.setTouchPosition(-1);
                    }
                    break;
            }


            // 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) head.findViewById(R.id.head_scroll);
            headSrcrollView.onTouchEvent(ev);
            mGesture.onTouchEvent(ev);
            return false;
        }

    }


    public String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

}
