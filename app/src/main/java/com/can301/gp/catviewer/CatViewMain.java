package com.can301.gp.catviewer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.can301.gp.GlobalData;
import com.can301.gp.MainActivity;
import com.can301.gp.R;

import java.util.ArrayList;
import java.util.List;

public class CatViewMain extends AppCompatActivity {

    public static final String CAT_VIEW_INDEX_KEY = "cat view index";

    TextView tv_text;
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catview_main);

        tv_text = findViewById(R.id.tv_text);
        viewpager = findViewById(R.id.viewpager);

        CatViewAdapter adapter = new CatViewAdapter(getSupportFragmentManager());
        adapter.setCount(GlobalData.catList.size());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(GlobalData.catList.size());

        int curCatIndex = getIntent().getIntExtra(CAT_VIEW_INDEX_KEY, 0);
        viewpager.setCurrentItem(curCatIndex);
        tv_text.setText(GlobalData.catList.get(curCatIndex).title);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_text.setText(GlobalData.catList.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
