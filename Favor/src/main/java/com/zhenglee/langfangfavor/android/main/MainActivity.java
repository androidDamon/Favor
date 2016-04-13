package com.zhenglee.langfangfavor.android.main;

import android.content.Intent;
import android.os.Bundle;

import com.zhenglee.framework.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
