package com.zhenglee.framework.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.res.annotation.MenuResource;
import com.zhenglee.framework.ui.utils.ViewInjector;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Class<?> clazz = getClass();
        final LayoutResource layout = clazz.getAnnotation(LayoutResource.class);

        if (null != layout) {
            super.setContentView(layout.value());
        }

        ViewInjector.inject(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final Class<?> clazz = getClass();
        final MenuResource mr = clazz.getAnnotation(MenuResource.class);

        if (null != mr) {
            final MenuInflater mi = getMenuInflater();
            mi.inflate(mr.value(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
