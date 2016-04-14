package com.zhenglee.framework.network.callback;

import android.support.annotation.Nullable;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhenglee on 16/4/13.
 */
public class Callback<T> extends StringCallback {
    @Override
    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
    }
}
