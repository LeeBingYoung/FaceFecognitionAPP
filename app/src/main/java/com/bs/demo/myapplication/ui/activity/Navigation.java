package com.bs.demo.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.BtnListAdapter;

//package com.example.hellodrawer;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.BaseActivity;
import com.bs.demo.myapplication.common.BtnListAdapter;

import java.util.ArrayList;
import java.util.List;

public class Navigation extends BaseActivity
{
    private DrawerLayout mDrawerLayout = null;

    private ListView lv;
    private ListView left;
    private List<String> strings=new ArrayList<>();
    private List<String> leftstrings=new ArrayList<>();
    private BtnListAdapter btnListAdapter;
    private BtnListAdapter leftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initView();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//        setToolbarTitle("主页");
        strings.add("性别识别");
        strings.add("考勤识别");
        strings.add("录入人脸");
        strings.add("考勤记录");
        strings.add("导航");

        btnListAdapter=new BtnListAdapter(this,strings);
        lv.setAdapter(btnListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        go(XBActivity.class);
                        break;
                    case 1:
                        go(KQActivity.class);
                        break;
                    case 2:
                        go(LRActivity.class);
                        break;
                    case 3:
                        go(KQListActivity.class);
                        break;
                    case 4:
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                        break;

                }
            }
        });

        //        setToolbarTitle("主页");

        leftstrings.add("个人中心");
        leftstrings.add("修改密码");
        leftstrings.add("更多");

        leftAdapter=new BtnListAdapter(this,leftstrings);
        left.setAdapter(leftAdapter);
        left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        go(UserInfoSettingActivity.class);
                        break;
                    case 1:
                        go(ChangePwdActivity.class);
                        break;
                    case 2:
                        go(MoreActivity.class);
                        break;
                }
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        left = (ListView) findViewById(R.id.left_drawer);
    }
}

