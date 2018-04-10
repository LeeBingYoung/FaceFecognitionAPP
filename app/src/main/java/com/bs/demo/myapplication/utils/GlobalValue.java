package com.bs.demo.myapplication.utils;


import com.bs.demo.myapplication.bean.UserInfo;

public class GlobalValue {
    public static String TAG ="zjw";
    public static final String APP_ID = "10849709";
    public static final String API_KEY = "B8DFMsV748OcmzcTG6jEe9IN";
    public static final String SECRET_KEY = "6OiK1lGtNwo1iWEeNlMQwwXcznsviPu2";

    private static UserInfo userInfo;

    public static UserInfo getUserInfo() {
        if (userInfo==null){
            userInfo=new UserInfo();
        }
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        GlobalValue.userInfo = userInfo;
    }


}
