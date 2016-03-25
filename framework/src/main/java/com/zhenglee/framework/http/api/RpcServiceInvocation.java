package com.zhenglee.framework.http.api;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import android.content.Context;
import android.net.Uri;

public class RpcServiceInvocation {

    private static final AtomicLong counter = new AtomicLong();

    private final Object id = counter.incrementAndGet();

    private final RpcServiceFactory factory;

    private final Uri uri;

    private final Class<? extends RpcService> clazz;

    private final Method method;

    private final Object[] args;

    private final Map<String, String> properties;

    private final Map<Class<? extends Annotation>, Map<Annotation, ? super Serializable>> parameters = new HashMap<Class<? extends Annotation>, Map<Annotation, ? super Serializable>>();

    public RpcServiceInvocation(final RpcServiceFactory factory, final Uri uri, final Class<? extends RpcService> clazz, final Method method, final Object[] args, final Map<String, String> properties) {
        this.factory = factory;
        this.uri = uri;
        this.clazz = clazz;
        this.method = method;
        this.args = args;
        this.properties = new HashMap<String, String>(properties);

        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
        if (null != paramAnnotations && paramAnnotations.length > 0) {
            for (int i = 0; i < paramAnnotations.length; i++) {
                final Object arg = args[i];
                final Annotation[] annotations = paramAnnotations[i];

                if (null != annotations && annotations.length > 0) {
                    for (int j = 0; j < annotations.length; j++) {
                        final Annotation annotation = annotations[j];
                        final Class<? extends Annotation> type = annotation.annotationType();

                        if (!this.parameters.containsKey(type)) {
                            this.parameters.put(type, new LinkedHashMap<Annotation, Serializable>());
                        }

                        if (arg instanceof Serializable) {
                            this.parameters.get(type).put(annotation, (Serializable) arg);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns an unique identifier of the invocation
     * 
     * @return an unique identifier
     */
    public Object getId() {
        return this.id;
    }

    /**
     * Returns the {@link RpcServiceFactory}
     * 
     * @return
     */
    public RpcServiceFactory getRpcServiceFactory() {
        return this.factory;
    }

    /**
     * Returns the android context
     * 
     * @return
     */
    public Context getContext() {
        return this.factory.getContext();
    }

    /**
     * Returns the request URI
     * 
     * @return
     */
    public Uri getUri() {
        return this.uri;
    }

    /**
     * Returns the class of sub interface of {@link RpcService}
     * 
     * @return
     */
    public Class<? extends RpcService> getRpcServiceClass() {
        return clazz;
    }

    /**
     * Returns the RPC method in the sub interface of {@link RpcService}
     * 
     * @return
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Returns the arguments of RPC method
     * 
     * @return
     */
    public Object[] getArguments() {
        return this.args;
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation, T extends Serializable> Map<A, T> getAnnotatedParameters(final Class<A> annotationType) {
        if (this.parameters.containsKey(annotationType)) {
            return (Map<A, T>) this.parameters.get(annotationType);
        }

        return null;
    }

    /**
     * Returns the properties copied from {@link RpcServiceFactory}
     * 
     * @return
     */
    public Map<String, String> getProperties() {
        return this.properties;
    }

}
