package com.can301.gp.catviewer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.can301.gp.Category;
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

        updateCategoryInformation(curCatIndex);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateCategoryInformation(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * When the category selected changes,
     * update the visual information by calling this method.
     * @param catInd the index of the newly selected category.
     */
    void updateCategoryInformation(int catInd) {
        Category cat = GlobalData.catList.get(catInd);
        tv_text.setText(cat.title);
        tv_text.setCompoundDrawablesWithIntrinsicBounds(0,cat.iconId,0,0);
    }
}
