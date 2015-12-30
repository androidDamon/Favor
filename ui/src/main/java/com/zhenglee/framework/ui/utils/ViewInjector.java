package com.zhenglee.framework.ui.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import com.zhenglee.framework.res.annotation.IdResource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhenglee on 15/10/8.
 */
public final class ViewInjector {

    private static final String FIND_VIEW_BY_ID = "findViewById";

    private final Object mView;
    private final Method mFindViewById;

    public ViewInjector(View view){
        mView = view;

        try {
            final Class<?> clazz = view.getClass();
            mFindViewById = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public ViewInjector(Activity activity){
        mView = activity;

        try {
            final Class<?> clazz = activity.getClass();
            mFindViewById = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ViewInjector(Window window){
        mView = window;

        try {
            final Class<?> clazz = window.getClass();
            mFindViewById = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void inject(Object o){
        final Class<?> clazz = o.getClass();
        final Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];

            if (!View.class.isAssignableFrom(field.getType())) continue;

            final IdResource id = field.getAnnotation(IdResource.class);
            if (null == id) continue;

            field.setAccessible(true);

            try {
                field.set(o, mFindViewById.invoke(mView, id.value()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void inject(View v, Object o) {
        new ViewInjector(v).inject(o);
    }

    public static void inject(Activity a, Object o) {
        new ViewInjector(a).inject(o);
    }

    public static void inject(Window w, Object o) {
        new ViewInjector(w).inject(o);
    }
}
