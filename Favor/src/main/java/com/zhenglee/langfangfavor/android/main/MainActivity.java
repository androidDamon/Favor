package com.zhenglee.langfangfavor.android.main;

import android.content.Intent;
import android.os.Bundle;

import com.zhenglee.framework.network.OkRequest;
import com.zhenglee.framework.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OkRequest.init(getApplication());
        OkRequest.getInstance().setConnectTimeout(10000).setReadTimeOut(10000).setWriteTimeOut(10000);
        OkRequest.getInstance().debug("OkRequest");

        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
