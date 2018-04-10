package com.bs.demo.myapplication.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.aip.face.AipFace;
import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.bean.KQBean;
import com.bs.demo.myapplication.common.BtnListAdapter;
import com.bs.demo.myapplication.utils.GlobalValue;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class KQActivity extends TakePhotoActivity {
    private TResult result;
    private ImageView iv;
    private ListView lv;
    private List<String> strings=new ArrayList<>();
    private BtnListAdapter btnListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq);
        initView();
        strings.add("进行考勤");
        btnListAdapter=new BtnListAdapter(this,strings);
        lv.setAdapter(btnListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (result!=null){
                            Observable.create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    AipFace client = new AipFace(GlobalValue.APP_ID, GlobalValue.API_KEY, GlobalValue.SECRET_KEY);
                                    HashMap<String, String> options = new HashMap<String, String>();
//                                    options.put("ext_fields", "faceliveness");
//                                    options.put("user_top_num", "3");
                                    String groupId = "group1";
                                    // 参数为本地图片路径
                                    JSONObject res = client.identifyUser(groupId, result.getImage().getOriginalPath(), options);
                                    emitter.onNext(res.toString());
                                }
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            Log.d("zxc",s);
                                            KQBean kqBean=new Gson().fromJson(s,KQBean.class);
                                            if (kqBean.getResult().get(0).getScores().get(0)>=80){
                                                Toast.makeText(KQActivity.this,"是本人,考勤通过",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(KQActivity.this, UserInfoSettingActivity.class);
                                                startActivity(intent);
                                                HttpUtil httpUtil =new HttpUtil();
                                                httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
                                                httpUtil.sendGetRequest("AddKQ", new HttpUtil.Callback() {
                                                    @Override
                                                    public void onSuccess(String json) {

                                                    }

                                                    @Override
                                                    public void onFailure(String msg) {

                                                    }
                                                });
                                            }else {
                                                Toast.makeText(KQActivity.this,"非本人,考勤不通过",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(KQActivity.this,"请选择图片",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;

                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(KQActivity.this).setItems(new String[]{"本地上传","拍照上传"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                getTakePhoto().onPickFromGallery();
                                break;
                            case 1:
                                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                                Uri imageUri = Uri.fromFile(file);
                                getTakePhoto().onPickFromCapture(imageUri);
                                break;
                        }
                    }
                }).show();


            }
        });
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        this.result=result;
        Glide.with(this).load(result.getImage().getOriginalPath()).into(iv);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }
}
