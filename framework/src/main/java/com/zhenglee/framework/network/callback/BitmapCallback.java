package com.zhenglee.framework.network.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

public abstract class BitmapCallback extends AbsCallback<Bitmap> {

    @Override
    public Bitmap parseNetworkResponse(Response response) {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
