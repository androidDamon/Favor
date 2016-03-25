package com.zhenglee.framework.http.api;

import android.content.Context;
import android.net.Uri;

import com.zhenglee.framework.http.api.spi.RpcServiceInvocationHandlerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class RpcServiceInvocationHandlerFactoryService {

    private static final Map<String, RpcServiceInvocationHandler> handlers = Collections.synchronizedMap(new HashMap<String, RpcServiceInvocationHandler>());

    private static RpcServiceInvocationHandlerFactoryService instance = null;

    public static synchronized RpcServiceInvocationHandlerFactoryService getInstance(final ClassLoader classLoader) {
        if (null == instance) {
            instance = new RpcServiceInvocationHandlerFactoryService(classLoader);
        }

        return instance;
    }

    private final ServiceLoader<RpcServiceInvocationHandlerFactory> loader;

    private RpcServiceInvocationHandlerFactoryService(final ClassLoader classLoader) {
        this.loader = ServiceLoader.load(RpcServiceInvocationHandlerFactory.class, classLoader);
    }

    public synchronized RpcServiceInvocationHandler newRpcServiceInvocationHandler(final Context context, final Uri uri) {
        final String scheme = uri.getScheme();

        if (handlers.containsKey(scheme)) {
            return handlers.get(scheme);
        }

        RpcServiceInvocationHandler handler;

        for (final Iterator<RpcServiceInvocationHandlerFactory> i = this.loader.iterator(); i.hasNext();) {
            if (null != (handler = i.next().newRpcServiceInvocationHandler(context, uri))) {
                handlers.put(scheme, handler);
                return handler;
            }
        }

        return null;
    }
}
