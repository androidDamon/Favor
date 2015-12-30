package com.zhenglee.framework.ui.utils;

import android.content.Context;
import android.view.View;

/**
 * Created by zhenglee on 15/11/12.
 */
public abstract class ViewHolder<T> {

    public static <T extends ViewHolder<?>> T as(View v){
        return (T) v.getTag();
    }

    private View view;

    protected ViewHolder(View view) {
        this.view = view;
        view.setTag(this);
        ViewInjector.inject(view, this);
    }

    public Context getContext(){
        return this.view.getContext();
    }

    public abstract void bind(T t);
}
