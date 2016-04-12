package com.zhenglee.framework.network;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.zhenglee.framework.network.cache.CacheMode;
import com.zhenglee.framework.network.cookie.CookieJarImpl;
import com.zhenglee.framework.network.cookie.store.MemoryCookieStore;
import com.zhenglee.framework.network.https.HttpsUtils;
import com.zhenglee.framework.network.interceptor.LoggerInterceptor;
import com.zhenglee.framework.network.model.HttpHeaders;
import com.zhenglee.framework.network.model.HttpParams;
import com.zhenglee.framework.network.request.DeleteRequest;
import com.zhenglee.framework.network.request.GetRequest;
import com.zhenglee.framework.network.request.HeadRequest;
import com.zhenglee.framework.network.request.OptionsRequest;
import com.zhenglee.framework.network.request.PostRequest;
import com.zhenglee.framework.network.request.PutRequest;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okio.Buffer;

public class OkRequest {

    //默认的超时时间
    public static final int DEFAULT_MILLISECONDS = 20000;

    private static OkRequest mInstance;
    private static Application context;

    private Handler handler;
    private OkHttpClient.Builder okHttpClientBuilder;
    private HttpParams commonParams;
    private HttpHeaders commonHeaders;
    private CacheMode cacheMode;

    private OkRequest() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        //允许cookie的自动化管理
        okHttpClientBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        okHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkRequest getInstance() {
        if (mInstance == null) {
            synchronized (OkRequest.class) {
                if (mInstance == null) {
                    mInstance = new OkRequest();
                }
            }
        }
        return mInstance;
    }

    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public static void init(Application app) {
        context = app;
    }

    /** 获取全局上下文 */
    public static Context getContext() {
        return context;
    }

    public Handler getHandler() {
        return handler;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClientBuilder.build();
    }

    /** get请求 */
    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    /** post请求 */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    /** put请求 */
    public static PutRequest put(String url) {
        return new PutRequest(url);
    }

    /** head请求 */
    public static HeadRequest head(String url) {
        return new HeadRequest(url);
    }

    /** delete请求 */
    public static DeleteRequest delete(String url) {
        return new DeleteRequest(url);
    }

    /** patch请求 */
    public static OptionsRequest options(String url) {
        return new OptionsRequest(url);
    }

    /** 调试模式 */
    public OkRequest debug(String tag) {
        okHttpClientBuilder.addInterceptor(new LoggerInterceptor(tag, true));
        return this;
    }

    /**
     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
     */
    private class DefaultHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /** https的全局访问规则 */
    public OkRequest setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        okHttpClientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }

    /** https的全局自签名证书 */
    public OkRequest setCertificates(InputStream... certificates) {
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null));
        return this;
    }

    /** https的全局自签名证书 */
    public OkRequest setCertificates(String... certificates) {
        for (String certificate : certificates) {
            InputStream inputStream = new Buffer().writeUtf8(certificate).inputStream();
            setCertificates(inputStream);
        }
        return this;
    }

    /** 全局读取超时时间 */
    public OkRequest setReadTimeOut(int readTimeOut) {
        okHttpClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        return this;
    }

    /** 全局写入超时时间 */
    public OkRequest setWriteTimeOut(int writeTimeout) {
        okHttpClientBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /** 全局连接超时时间 */
    public OkRequest setConnectTimeout(int connectTimeout) {
        okHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /** 全局的缓存模式 */
    public OkRequest setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return this;
    }

    /** 获取全局的缓存模式 */
    public CacheMode getCacheMode() {
        return cacheMode;
    }

    /** 获取全局公共请求参数 */
    public HttpParams getCommonParams() {
        return commonParams;
    }

    /** 添加全局公共请求参数 */
    public OkRequest addCommonParams(HttpParams commonParams) {
        if (this.commonParams == null) this.commonParams = new HttpParams();
        this.commonParams.put(commonParams);
        return this;
    }

    /** 获取全局公共请求头 */
    public HttpHeaders getCommonHeaders() {
        return commonHeaders;
    }

    /** 添加全局公共请求参数 */
    public OkRequest addCommonHeaders(HttpHeaders commonHeaders) {
        if (this.commonHeaders == null) this.commonHeaders = new HttpHeaders();
        this.commonHeaders.put(commonHeaders);
        return this;
    }

    /** 根据Tag取消请求 */
    public void cancelTag(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

