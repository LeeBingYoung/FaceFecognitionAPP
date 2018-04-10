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
import com.bs.demo.myapplication.bean.XBBean;
import com.bs.demo.myapplication.common.BtnListAdapter;
import com.bs.demo.myapplication.utils.FileUtils;
import com.bs.demo.myapplication.utils.GlobalValue;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XBActivity extends TakePhotoActivity {
    private TResult result;
    private TImage tImage;
    private ImageView iv;
    private ListView lv;
    private List<String> strings=new ArrayList<>();
    private BtnListAdapter btnListAdapter;
    public List<String> resultString = new LinkedList<>();
    private int pathID;
    private CompressConfig compressConfig;
    private static final String File_Common = "/storage/emulated/0/Classification/";
    private static final String File_Female = File_Common+"Gender/Female/";//female
    private static final String File_Male = File_Common+"Gender/Male/";//male
    private static final String File_Yellow = File_Common+"Race/Yellow/";//yellow
    private static final String File_Black = File_Common+"Race/Black/";//black
    private static final String File_White = File_Common+"Race/White/";//white
    private static final String File_Arabs = File_Common+"Race/Arabs/";//arabs//
    private static final String File_Normal = File_Common+"Expression/Normal/";//0
    private static final String File_Smile = File_Common+"Expression/Smile/";//1
    private static final String File_Laugh = File_Common+"Expression/Laugh/";//2
    private static final String File_Childhood = File_Common+"Age/Childhood/";//0-12
    private static final String File_Juvenile = File_Common+"Age/Juvenils/";//13-34
    private static final String File_MiddleAge = File_Common+"Age/MiddleAge/";//35-60
    private static final String File_Agedness = File_Common+"Age/Agedness/";//60-


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq);
        initView();
        strings.add("进行检测");
        btnListAdapter=new BtnListAdapter(this,strings);
        lv.setAdapter(btnListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (result!=null){
                            Observable.create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    AipFace client = new AipFace(GlobalValue.APP_ID, GlobalValue.API_KEY, GlobalValue.SECRET_KEY);
                                    HashMap<String,String> map=new HashMap<>();
                                    map.put("face_fields","gender,age,race,beauty,expression");
                                    resultString.clear();
                                    JSONObject res = new JSONObject();
                                    pathID = 0;
                                    FileUtils fileUtils = new FileUtils();
                                    for(TImage x: result.getImages()){

                                        res = client.detect(x.getOriginalPath(), map);
                                        XBBean xbBean =new Gson().fromJson(res.toString(),XBBean.class);

                                        try {
                                            pathID++;
                                            String originalPath = result.getImages().get(pathID-1).getOriginalPath();
                                            resultString.add("第" + pathID + "张照片信息为");

                                            String tempGender = xbBean.getResult().get(0).getGender();
                                            if (tempGender.equals("female")){
                                                fileUtils.copyPicture(originalPath,File_Female);
                                                resultString.add("女性");
                                            }else if (tempGender.equals("male")){
                                                fileUtils.copyPicture(originalPath,File_Male);
                                                resultString.add("男性");
                                            }

                                            int tempAge=xbBean.getResult().get(0).getAge();
                                            resultString.add(tempAge+"岁");
                                            if (tempAge<13){
                                                fileUtils.copyPicture(originalPath,File_Childhood);
                                            }else if (tempAge>12 && tempAge<35){
                                                fileUtils.copyPicture(originalPath,File_Juvenile);
                                            }else if (tempAge>34 &&tempAge<60){
                                                fileUtils.copyPicture(originalPath,File_MiddleAge);
                                            }else if (tempAge>59){
                                                fileUtils.copyPicture(originalPath,File_Agedness);
                                            }

                                            String tempRace = xbBean.getResult().get(0).getRace();
                                            if (tempRace.equals("yellow")){
                                                fileUtils.copyPicture(originalPath,File_Yellow);
                                                resultString.add("黄种人");
                                            }else if (tempRace.equals("white")){
                                                fileUtils.copyPicture(originalPath,File_White);
                                                resultString.add("白种人");
                                            } else if (tempRace.equals("black")){
                                                fileUtils.copyPicture(originalPath,File_Black);
                                                resultString.add("黑种人");
                                            }else if (tempRace.equals("arabs")){
                                                fileUtils.copyPicture(originalPath,File_Arabs);
                                                resultString.add("阿拉伯人");
                                            }

                                            int tempExpression = xbBean.getResult().get(0).getExpression();
                                            if (tempExpression==0){
                                                fileUtils.copyPicture(originalPath,File_Normal);
                                                resultString.add("表情为不笑");
                                            }else if(tempExpression==1){
                                                fileUtils.copyPicture(originalPath,File_Smile);
                                                resultString.add("表情为微笑");
                                            }else if(tempExpression==2){
                                                fileUtils.copyPicture(originalPath,File_Laugh);
                                                resultString.add("表情为大笑");
                                            }

                                            resultString.add("颜值为"+ xbBean.getResult().get(0).getBeauty() + "分");



                                        }catch (Exception e){
                                            //Toast.makeText(XBActivity.this,"出错,请检查图片",Toast.LENGTH_SHORT).show();
                                            resultString.add("无法检测,请检查图片");
                                        }

                                    }
                                    emitter.onNext(res.toString());
                                }
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            Toast.makeText(XBActivity.this,resultString.toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }else {
                            Toast.makeText(XBActivity.this,"请选择图片",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;

                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(XBActivity.this).setItems(new String[]{"本地上传","拍照上传"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //getTakePhoto().onEnableCompress(compressConfig,true).onPickFromGallery();
                                //getTakePhoto().onEnableCompress(compressConfig,true).onPickFromGalleryWithCrop(imageUri,cropOptions);
                                //getTakePhoto().onEnableCompress(new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create(),true).onPicSelectCrop(imageUri);
                                //getTakePhoto().onPickFromGallery();
                                getTakePhoto().onPickMultiple(9);
                                //getTakePhoto().onEnableCompress(config,true);
//                                TakePhoto takePhoto=getTakePhoto();
//                                takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(50*270).setMaxPixel(200).create(),true);
//                                takePhoto.onPickFromGallery();
                                break;
                            case 1:
                                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                                Uri imageUri = Uri.fromFile(file);
                                //getTakePhoto().onEnableCompress(compressConfig,true);
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
        //tImage.setCompressed(true);
        this.result=result;
        Glide.with(this).load(result.getImage().getOriginalPath()).into(iv);
        Log.d("take",result.getImages().get(0).getOriginalPath());
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
