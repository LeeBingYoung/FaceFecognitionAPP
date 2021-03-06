package com.bs.demo.myapplication.ui.activity;

import android.content.DialogInterface;
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
import com.bs.demo.myapplication.common.BtnListAdapter;
import com.bs.demo.myapplication.utils.GlobalValue;
import com.bumptech.glide.Glide;
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

public class LRActivity extends TakePhotoActivity {
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
        strings.add("录入");
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
                                    options.put("action_type", "replace");
                                    String userInfo = "user's info";
                                    String groupId = "group1";
                                    // 参数为本地图片路径
                                    JSONObject res = client.addUser(GlobalValue.getUserInfo().getId(), userInfo, groupId, result.getImage().getOriginalPath(), options);
                                    emitter.onNext(res.toString());
                                }
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            Log.d("zxc",s);
                                            if (s.contains("error_msg")){
                                                Toast.makeText(LRActivity.this,"录入失败",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(LRActivity.this,"录入成功",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(LRActivity.this,"请选择图片",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;

                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LRActivity.this).setItems(new String[]{"本地上传","拍照上传"}, new DialogInterface.OnClickListener() {
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
