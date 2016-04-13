package com.zhenglee.framework.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zhenglee.framework.network.OkRequest;
import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.res.annotation.MenuResource;
import com.zhenglee.framework.ui.utils.ViewInjector;

public abstract class BaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Class<?> clazz = getClass();
        final LayoutResource layout = clazz.getAnnotation(LayoutResource.class);
        final MenuResource menu = clazz.getAnnotation(MenuResource.class);
        final View v;

        if (layout != null) {
            v = inflater.inflate(layout.value(), null);
        } else {
            v = super.onCreateView(inflater, container, savedInstanceState);
        }

        ViewInjector.inject(v, this);
        setHasOptionsMenu(null != menu);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final Class<?> clazz = getClass();
        final MenuResource mr = clazz.getAnnotation(MenuResource.class);

        if (mr != null) {
            inflater.inflate(mr.value(), menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.getActivity().finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        OkRequest.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
