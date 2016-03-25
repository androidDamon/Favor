package com.zhenglee.langfangfavor.android.main;

import android.content.Intent;
import android.os.Bundle;

import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.ui.BaseActivity;
import com.zhenglee.langfangfavor.R;

@LayoutResource(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
