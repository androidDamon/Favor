package com.zhenglee.framework.http.api;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Map;


import android.content.Context;
import android.net.Uri;

/**
 * The {@link RpcServiceFactory} is used for {@link RpcService} creation to
 * simplify the service invocation by intercepting the method invocation of the
 * proxy interface and cooperates with {@link RpcServiceInvocationHandler} which
 * is created by {@link RpcServiceInvocationHandlerFactory} to communicate with
 * service server.
 *
 * @author johnsonlee
 */
public class RpcServiceFactory {

    /**
     * Property name for certificate authority verification
     */
    public static final String PROPERTY_CERTIFICATE_VERIFICATION = "rpc.certificate.verification";

    /**
     * Turn off the verification of certificate
     */
    public static final String DISABLE_CERTIFICATE_VERIFICATION  = "disable";

    private final Context context;

    /**
     * Create an instance
     *
     * @param context
     *           The android context
     */
    public RpcServiceFactory(final Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Returns the android context
     *
     * @return the android context
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Create a proxy instance of the specified interface
     *
     * @param clazz
     *           The sub interface of {@link RpcService}
     * @param uri
     *           The target service uri
     * @param <T>
     *           The interface type which extends {@link RpcService}
     * @return a proxy instance of the specified interface
     */
    public <T extends RpcService> T newRpcService(final Class<T> clazz, final String uri) {
        return this.newRpcService(clazz, uri, Collections.<String, String>emptyMap());
    }

    /**
     * Create a proxy instance of the specified interface
     *
     * @param clazz
     *           The sub interface of {@link RpcService}
     * @param uri
     *           The target service uri
     * @param properties
     *           Properties for the specified service
     * @param <T>
     *           The interface type which extends {@link RpcService}
     * @return a proxy instance of the specified interface
     * @see #PROPERTY_CERTIFICATE_VERIFICATION
     */
    public <T extends RpcService> T newRpcService(final Class<T> clazz, final String uri, final Map<String, String> properties) {
        return this.newRpcService(clazz, Uri.parse(uri), properties);
    }

    /**
     * Create a proxy instance of the specified interface
     *
     * @param clazz
     *           The sub interface of {@link RpcService}
     * @param uri
     *           The target service uri
     * @param properties
     *           Properties for the specified service
     * @param <T>
     *           The interface type which extends {@link RpcService}
     * @return a proxy instance of the specified interface
     * @see #PROPERTY_CERTIFICATE_VERIFICATION
     */
    @SuppressWarnings("unchecked")
    public <T extends RpcService> T newRpcService(final Class<T> clazz, final Uri uri, final Map<String, String> properties) {
        final RpcServiceProxy proxy = new RpcServiceProxy(this, clazz, uri, properties);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxy);
    }

}
