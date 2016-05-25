package com.vb.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button simple, swipe, horizontalScroll, pinnedSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simple = (Button) findViewById(R.id.simple);
        swipe = (Button) findViewById(R.id.swipe);
        horizontalScroll = (Button) findViewById(R.id.horizontalScroll);
        pinnedSection = (Button) findViewById(R.id.pinnedSection);

        simple.setOnClickListener(this);
        swipe.setOnClickListener(this);
        horizontalScroll.setOnClickListener(this);
        pinnedSection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple:
                startActivity(new Intent(MainActivity.this, SimpleXListActivity.class));
                break;
            case R.id.swipe:
                startActivity(new Intent(MainActivity.this, SwipeXListActivity.class));
                break;
            case R.id.horizontalScroll:
                startActivity(new Intent(MainActivity.this, HorizontalScrollXListActivity.class));
                break;
            case R.id.pinnedSection:
                startActivity(new Intent(MainActivity.this, PinnedSectionXListActivity.class));
                break;
        }
    }
}
