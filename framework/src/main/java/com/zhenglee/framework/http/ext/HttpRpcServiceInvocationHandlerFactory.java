package com.zhenglee.framework.http.ext;

import android.content.Context;
import android.net.Uri;

import com.zhenglee.framework.http.api.RpcServiceInvocationHandler;
import com.zhenglee.framework.http.api.spi.RpcServiceInvocationHandlerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class HttpRpcServiceInvocationHandlerFactory implements RpcServiceInvocationHandlerFactory {

    public static final String PROTOCOL_HTTP = "http";

    public static final String PROTOCOL_HTTPS = "https";

    public static final String PROPERTY_READ_TIMEOUT = "rpc.http.readtimeout";

    public static final String PROPERTY_WRITE_TIMEOUT = "rpc.http.writetimeout";

    public static final String PROPERTY_CONNECTION_TIMEOUT = "rpc.http.connectiontimeout";

    public static final String[] SUPPORTED_PROTOCOLS = { PROTOCOL_HTTP, PROTOCOL_HTTPS };

    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = NCPU + 1;

    private static final int MAX_POOL_SIZE = NCPU << 1 + 1;

    private static final long KEEP_ALIVE_TIME = 1L;

    private static final Logger logger = LoggerFactory.getLogger(HttpRpcServiceInvocationHandlerFactory.class);

    private static final UncaughtExceptionHandler DEFAULT_EXCEPTION_HANDLER = new UncaughtExceptionHandler() {

        public void uncaughtException(final Thread t, final Throwable e) {
            logger.error("Exception occurred in " + t, e);
        }

    };
    
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {

        private final AtomicLong counter = new AtomicLong();

        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName(PROTOCOL_HTTP + "#" + counter.incrementAndGet());
            t.setUncaughtExceptionHandler(DEFAULT_EXCEPTION_HANDLER);
            return t;
        }

    };

    static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128), DEFAULT_THREAD_FACTORY);

    public RpcServiceInvocationHandler newRpcServiceInvocationHandler(final Context context, final Uri uri) {
        final String scheme = uri.getScheme();
        if (PROTOCOL_HTTP.equalsIgnoreCase(scheme) || PROTOCOL_HTTPS.equalsIgnoreCase(scheme)) {
            return new HttpRpcServiceInvocationHandler(this);
        }

        return null;
    }

    public String[] getSupportedSchemes() {
        return SUPPORTED_PROTOCOLS;
    }

}
