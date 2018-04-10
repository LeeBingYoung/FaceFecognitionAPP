package com.bs.demo.myapplication.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.bean.KQListBean;
import com.bs.demo.myapplication.common.BaseActivity;
import com.bs.demo.myapplication.common.InfoListAdapter;
import com.bs.demo.myapplication.utils.GlobalValue;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class KQListActivity extends BaseActivity {

    private ListView lv;
    private InfoListAdapter infoListAdapter;
    private List<String> strings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kqlist);
        initView();
        strings=new ArrayList<>();
        infoListAdapter=new InfoListAdapter(this,strings);
        lv.setAdapter(infoListAdapter);
        setToolbarTitle("考勤记录");
        HttpUtil httpUtil =new HttpUtil();
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());

        httpUtil.sendGetRequest("GetKQList", new HttpUtil.Callback() {
            @Override
            public void onSuccess(String json) {
                List<KQListBean> listBeans =new Gson().fromJson(json, new TypeToken<List<KQListBean>>() {}.getType());
                for (int i=0;i<listBeans.size();i++){
                    strings.add("打卡时间: "+listBeans.get(i).getTime());
                }
                infoListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
    }
}
