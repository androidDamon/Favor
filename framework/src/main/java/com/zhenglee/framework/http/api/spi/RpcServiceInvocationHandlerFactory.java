package com.zhenglee.framework.http.api.spi;


import android.content.Context;
import android.net.Uri;

import com.zhenglee.framework.http.api.RpcServiceInvocationHandler;

public interface RpcServiceInvocationHandlerFactory {

    public String[] getSupportedSchemes();

    public RpcServiceInvocationHandler newRpcServiceInvocationHandler(final Context context, final Uri uri);

}
