package com.zhenglee.framework.http.api;

import android.content.Context;
import android.net.Uri;

import com.zhenglee.framework.net.UnsupportedProtocolException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class RpcServiceProxy implements InvocationHandler {

    private final RpcServiceFactory factory;

    private final Class<? extends RpcService> clazz;
    
    private final Uri uri;
    
    private final Map<String, String> properties;
    
    private final Set<Method> methods;

    private final RpcServiceInvocationHandler handler;

    public RpcServiceProxy(final RpcServiceFactory factory,
            final Class<? extends RpcService> clazz, final Uri uri,
            final Map<String, String> properties) {
        this.factory = factory;
        this.clazz = clazz;
        this.uri = uri;
        this.properties = null == properties ? Collections.<String, String>emptyMap() : properties;

        final Method[] methods = clazz.getMethods();
        this.methods = new HashSet<Method>(methods.length);
        for (int i = 0; i < methods.length; i++) {
            this.methods.add(methods[i]);
        }

        final Context ctx = factory.getContext();
        final ClassLoader cl = ctx.getClassLoader();
        this.handler = RpcServiceInvocationHandlerFactoryService.getInstance(cl).newRpcServiceInvocationHandler(ctx, uri);
        if (null == this.handler) {
            throw new UnsupportedProtocolException(uri, "Unsupported protocol");
        }
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (this.methods.contains(method)) {
            return this.handler.handle(new RpcServiceInvocation(this.factory, this.uri, this.clazz, method, args, this.properties));
        }

        final String methodName = method.getName();

        if ("equals".equals(methodName) && null != args && 1 == args.length) {
            return this.equals(proxy);
        } else if ("hashCode".equals(methodName) && (null == args || 0 == args.length)) {
            return this.hashCode();
        } else if ("toString".equals(methodName) && (null == args || 0 == args.length)) {
            return this.toString();
        }

        throw new NoSuchMethodException(method.toGenericString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;

        if (!(o instanceof RpcServiceProxy)) {
            return false;
        }

        final RpcServiceProxy proxy = (RpcServiceProxy) o;
        return this.clazz.equals(proxy.clazz)
                && this.uri.equals(proxy.uri)
                && this.properties.equals(proxy.properties);
    }

    @Override
    public int hashCode() {
        int hash = 31 * 17;
        hash = 31 * hash + this.clazz.hashCode();
        hash = 31 * hash + this.uri.hashCode();
        hash = 31 * hash + this.properties.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "Proxy for " + this.clazz.getName();
    }

    public Class<? extends RpcService> getRpcServiceClass() {
        return this.clazz;
    }

    public Uri getUri() {
        return this.uri;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

}
