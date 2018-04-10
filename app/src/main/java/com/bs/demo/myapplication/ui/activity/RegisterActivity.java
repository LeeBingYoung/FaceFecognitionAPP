package com.bs.demo.myapplication.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.BaseActivity;
import com.bs.demo.myapplication.utils.HttpUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private EditText account;
    private EditText password;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setToolbarTitle("注册");
    }


    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String accountString = account.getText().toString().trim();
        if (TextUtils.isEmpty(accountString)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        String api ="Register";
        HttpUtil httpUtil =new HttpUtil();
        httpUtil.addParams("account", accountString);
        httpUtil.addParams("pwd", passwordString);
        httpUtil.sendGetRequest(api, new HttpUtil.Callback() {
            @Override
            public void onSuccess(String json) {
                showToast("注册成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
            }
        });
    }
}

