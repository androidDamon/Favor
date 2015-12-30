package com.zhenglee.framework.ui.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhenglee on 15/11/12.
 */
public class Toasts {

    private static Toast instance;

    public Toasts() {
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getString(resId), duration);
    }

    public synchronized static void show(Context context, CharSequence text, int duration) {
        if (null == instance) {
            instance = Toast.makeText(context, text, duration);
        } else {
            instance.setText(text);
        }
        instance.show();
    }

    public void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public void show(Context context, int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }
}
