package com.zhenglee.framework.http.ext;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.okhttp.logging.HttpLoggingInterceptor.Level;
import com.zhenglee.framework.http.api.IllegalRpcServiceException;
import com.zhenglee.framework.http.api.RpcCallback;
import com.zhenglee.framework.http.api.RpcException;
import com.zhenglee.framework.http.api.RpcService;
import com.zhenglee.framework.http.api.RpcServiceFactory;
import com.zhenglee.framework.http.api.RpcServiceInvocation;
import com.zhenglee.framework.http.api.RpcServiceInvocationHandler;
import com.zhenglee.framework.http.api.TargetThread;
import com.zhenglee.framework.http.api.ThreadType;
import com.zhenglee.framework.http.ext.annotation.Patch;
import com.zhenglee.framework.http.ext.annotation.Path;
import com.zhenglee.framework.io.Deserialization;
import com.zhenglee.framework.io.Deserializer;
import com.zhenglee.framework.net.ssl.AllowAllHostnameVerifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

class HttpRpcServiceInvocationHandler implements RpcServiceInvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpRpcServiceInvocationHandler.class);

    private static final X509TrustManager DEFAULT_X509_TRUST_MANAGER = new X509TrustManager() {

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
        }

        public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
        }

    };

    private static final Dispatcher DEFAULT_DISPATCHER = new Dispatcher(HttpRpcServiceInvocationHandlerFactory.THREAD_POOL_EXECUTOR);

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    final HttpRpcServiceInvocationHandlerFactory factory;

    public HttpRpcServiceInvocationHandler(final HttpRpcServiceInvocationHandlerFactory factory) {
        this.factory = factory;
    }

    public Object handle(final RpcServiceInvocation invocation) throws RpcException {
        final Class<? extends RpcService> clazz = invocation.getRpcServiceClass();
        if (!clazz.isAnnotationPresent(Path.class)) {
            throw new IllegalRpcServiceException(clazz.getName() + " does not annotated by " + Path.class.getName());
        }

        final Method method = invocation.getMethod();
        if (!method.isAnnotationPresent(Path.class)) {
            throw new IllegalRpcServiceException(method.getName() + " does not annotated by " + Path.class.getName());
        }

        final OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(CookieHandler.getDefault());
        client.setDispatcher(DEFAULT_DISPATCHER);
        client.setRetryOnConnectionFailure(true);

        final String scheme = invocation.getUri().getScheme();
        final Map<String, String> props = invocation.getProperties();

        if (HttpRpcServiceInvocationHandlerFactory.PROTOCOL_HTTPS.equalsIgnoreCase(scheme)) {
            if (RpcServiceFactory.DISABLE_CERTIFICATE_VERIFICATION.equals(props.get(RpcServiceFactory.PROPERTY_CERTIFICATE_VERIFICATION))) {
                logger.debug("Disable certificate verification");

                try {
                    final SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, new TrustManager[] { DEFAULT_X509_TRUST_MANAGER }, new SecureRandom());
                    client.setSslSocketFactory(ctx.getSocketFactory());
                    client.setHostnameVerifier(AllowAllHostnameVerifier.INSTANCE);
                } catch (final Throwable t) {
                    logger.error("Disable certifacate verification error", t);
                }
            }
        }

        // set connection timeout
        if (HttpRpcServiceInvocationHandlerFactory.PROPERTY_CONNECTION_TIMEOUT.equals(props.get(HttpRpcServiceInvocationHandlerFactory.PROPERTY_CONNECTION_TIMEOUT))) {
            final String timeout = props.get(HttpRpcServiceInvocationHandlerFactory.PROPERTY_CONNECTION_TIMEOUT);

            try {
                client.setConnectTimeout(Long.parseLong(timeout), TimeUnit.MILLISECONDS);
                logger.debug("%s: %s", HttpRpcServiceInvocationHandlerFactory.PROPERTY_CONNECTION_TIMEOUT, timeout);
            } catch (final NumberFormatException e) {
                logger.error("Illegal value of property %s: %s", HttpRpcServiceInvocationHandlerFactory.PROPERTY_CONNECTION_TIMEOUT, timeout);
            }
        }

        // set read timeout
        if (HttpRpcServiceInvocationHandlerFactory.PROPERTY_READ_TIMEOUT.equals(props.get(HttpRpcServiceInvocationHandlerFactory.PROPERTY_READ_TIMEOUT))) {
            final String timeout = props.get(HttpRpcServiceInvocationHandlerFactory.PROPERTY_READ_TIMEOUT);

            try {
                client.setReadTimeout(Long.parseLong(timeout), TimeUnit.MILLISECONDS);
            } catch (final NumberFormatException e) {
                logger.error("Illegal value of property %s: %s", HttpRpcServiceInvocationHandlerFactory.PROPERTY_READ_TIMEOUT, timeout);
            }
        }

        // set write timeout
        if (HttpRpcServiceInvocationHandlerFactory.PROPERTY_WRITE_TIMEOUT.equals(props.get(HttpRpcServiceInvocationHandlerFactory.PROPERTY_WRITE_TIMEOUT))) {
            final String timeout = props.get(HttpRpcServiceInvocationHandlerFactory.PROPERTY_WRITE_TIMEOUT);

            try {
                client.setWriteTimeout(Long.parseLong(timeout), TimeUnit.MILLISECONDS);
            } catch (final NumberFormatException e) {
                logger.error("Illegal value of property %s: %s", HttpRpcServiceInvocationHandlerFactory.PROPERTY_WRITE_TIMEOUT, timeout);
            }
        }

        // add logging interceptor
        if (logger.isDebugEnabled()) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(logger.isTraceEnabled() ? Level.BODY : Level.BASIC);
            interceptor.setLevel(Level.BODY);
            client.interceptors().add(interceptor);
        }

        try {
            final Object result = this.handle(client, invocation);
            if (null != result && method.getReturnType().isAssignableFrom(result.getClass())) {
                return result;
            }
        } catch (final RuntimeException e) {
            throw e;
        } catch (final RpcException e) {
            throw e;
        }

        return null;
    }

    private Object handle(final OkHttpClient client, final RpcServiceInvocation invocation) throws RpcException {
        final ThreadType threadType = getTargetThreadType(invocation);
        final RpcCallback<Serializable> callback = getRpcCallback(invocation);
        final Request request = new RequestBuilder(invocation).build();

        client.newCall(request).enqueue(new Callback() {

            public void onResponse(final Response response) throws IOException {
                if (null == callback) {
                    return;
                }

                try {
                    final Serializable result = processResponse(invocation, response);
                    invokeCallbackOnSuccess(callback, request, threadType, result);
                } catch (final Exception t) {
                    invokeCallbackOnFailure(callback, request, threadType, t);
                }
            }

            public void onFailure(final Request request, final IOException e) {
                if (null == callback) {
                    return;
                }

                invokeCallbackOnFailure(callback, request, threadType, e);
            }

        });

        return request.tag();
    }

    private void invokeCallbackOnSuccess(final RpcCallback<Serializable> cb, final Request request, final ThreadType threadType, final Serializable result) {
        switch (threadType) {
        case MAIN:
            mainHandler.post(new Runnable() {
                public void run() {
                    cb.onSuccess(result);
                }
            });
            break;
        default:
            cb.onSuccess(result);
        }
    }

    private void invokeCallbackOnFailure(final RpcCallback<Serializable> cb, final Request request, final ThreadType threadType, final Exception t) {
        switch (threadType) {
        case MAIN:
            mainHandler.post(new Runnable() {
                public void run() {
                    cb.onFailure(t);
                }
            });
            break;
        default:
            cb.onFailure(t);
            break;
        }
    }

    private Serializable processResponse(final RpcServiceInvocation invocation, final Response response) throws RpcException {
        final Method method = invocation.getMethod();
        if (!method.isAnnotationPresent(Deserialization.class)) {
            throw new IllegalRpcServiceException(method.getName() + " does not annotated by " + Deserialization.class.getName());
        }

        final RpcCallback<? extends Serializable> callback = getRpcCallback(invocation);
        final Deserialization deserialization = method.getAnnotation(Deserialization.class);
        final Class<? extends Deserializer<? super Serializable>> clazz = deserialization.value();

        try {
            Constructor<? extends Deserializer<? super Serializable>> ctor = null;
            Deserializer<? super Serializable> deserializer = null;

            try {
                ctor = clazz.getDeclaredConstructor(Type.class);
                ctor.setAccessible(true);
                deserializer = ctor.newInstance(callback.getType());
            } catch (final NoSuchMethodException e1) {
                try {
                    ctor = clazz.getDeclaredConstructor();
                    ctor.setAccessible(true);
                    deserializer = ctor.newInstance();
                } catch (NoSuchMethodException e2) {
                    throw new IllegalRpcServiceException("Neither " + clazz.getName() + "<init>(" + Type.class.getName() + ") nor " + clazz.getName() + "<init>() found");
                }
            }

            final InputStream stream = response.body().byteStream();

            try {
                return deserializer.deserialize(stream);
            } finally {
                if (null != stream) {
                    try {
                        stream.close();
                    } catch (final IOException e) {
                    }
                }
            }
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RpcException(-1, e);
        }
    }

    @SuppressWarnings("unchecked")
    static RpcCallback<Serializable> getRpcCallback(final RpcServiceInvocation invocation) {
        final Object[] args = invocation.getArguments();
        if (args.length <= 0) {
            return null;
        }

        final Object cb = args[args.length - 1];
        if (null == cb) {
            throw new IllegalArgumentException("Expected " + RpcCallback.class.getName() + ", but null found");
        }

        if (cb instanceof RpcCallback) {
            return (RpcCallback<Serializable>) cb;
        }

        throw new IllegalArgumentException("Expected " + RpcCallback.class.getName() + ", but " + cb.getClass() + " found");
    }

    static ThreadType getTargetThreadType(final RpcServiceInvocation invocation) {
        final Object[] args = invocation.getArguments();
        if (null == args || args.length <= 0)
            return ThreadType.MAIN;

        final Object callback = args[args.length - 1];
        if (!(callback instanceof RpcCallback))
            return ThreadType.MAIN;

        final Annotation[][] pas = invocation.getMethod().getParameterAnnotations();
        if (null == pas || pas.length <= 0)
            return ThreadType.MAIN;

        final Annotation[] annotations = pas[pas.length - 1];
        if (null == annotations || annotations.length <= 0)
            return ThreadType.MAIN;

        for (final Annotation annotation : annotations) {
            if (!TargetThread.class.equals(annotation.annotationType()))
                continue;
            return ((TargetThread) annotation).value();
        }

        return ThreadType.MAIN;
    }
}
