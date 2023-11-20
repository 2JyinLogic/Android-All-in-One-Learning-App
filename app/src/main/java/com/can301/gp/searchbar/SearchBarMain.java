package com.can301.gp.searchbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.can301.gp.Demonstration;
import com.can301.gp.GlobalData;
import com.can301.gp.MainActivity;
import com.can301.gp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchBarMain extends AppCompatActivity {

    EditText my_edit;
    ListView my_list;
    SearchAdapder myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar_main);

        myAdapter = new SearchAdapder(this, GlobalData.demoList);

        initView();
        initComponent();
    }

    private void initView() {
        my_edit = (EditText) findViewById(R.id.my_edit);
        my_list = (ListView) findViewById(R.id.my_list);
    }

    /**
     * 初始化部件 所有功能放在这底下 包括搜索框中的editView监听文本是否改动 以及listView监听单击
     */
    private void initComponent() {
        my_list.setAdapter(myAdapter);
        my_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /**
         * Which demo to go is decided by position
         */
        Context ctx = this;
        my_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Demonstration demo = (Demonstration)my_list.getItemAtPosition(position);
                demo.goToEffectActivity(ctx);
            }
        });

    }

}
