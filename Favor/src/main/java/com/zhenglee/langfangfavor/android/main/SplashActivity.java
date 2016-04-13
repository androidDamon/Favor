package com.zhenglee.langfangfavor.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.ui.BaseActivity;
import com.zhenglee.langfangfavor.R;
import com.zhenglee.langfangfavor.android.modules.home.HomeActivity;

@LayoutResource(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK, getIntent());
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }, 1000L);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}
