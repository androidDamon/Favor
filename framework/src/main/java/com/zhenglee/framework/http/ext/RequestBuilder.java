package com.zhenglee.framework.http.ext;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.zhenglee.framework.http.api.IllegalRpcServiceException;
import com.zhenglee.framework.http.api.RpcException;
import com.zhenglee.framework.http.api.RpcService;
import com.zhenglee.framework.http.api.RpcServiceInvocation;
import com.zhenglee.framework.http.ext.annotation.BodyParameter;
import com.zhenglee.framework.http.ext.annotation.Delete;
import com.zhenglee.framework.http.ext.annotation.Head;
import com.zhenglee.framework.http.ext.annotation.Header;
import com.zhenglee.framework.http.ext.annotation.Patch;
import com.zhenglee.framework.http.ext.annotation.Path;
import com.zhenglee.framework.http.ext.annotation.PathParameter;
import com.zhenglee.framework.http.ext.annotation.Post;
import com.zhenglee.framework.http.ext.annotation.Put;
import com.zhenglee.framework.http.ext.annotation.QueryParameter;
import com.zhenglee.framework.io.Serialization;
import com.zhenglee.framework.io.Serializer;
import com.zhenglee.framework.net.http.HttpHeader;
import com.zhenglee.framework.net.http.HttpMethod;
import com.zhenglee.framework.net.http.UserAgent;
import com.zhenglee.framework.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import okio.Buffer;
import okio.BufferedSink;

class RequestBuilder {

    private final RpcServiceInvocation invocation;

    public RequestBuilder(final RpcServiceInvocation invocation) {
        this.invocation = invocation;
    }

    public Request build() throws RpcException {
        final HttpMethod method = this.getRequestMethod();
        final RequestBody body = this.buildRequestBody(method);
        final Request.Builder builder = new Request.Builder();

        if (this.invocation.getMethod().isAnnotationPresent(Header.class)) {
            final Header header = this.invocation.getMethod().getAnnotation(Header.class);
            final String name = header.name();
            final String value = header.value();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                throw new NullPointerException(header.toString());
            }

            builder.header(header.name(), header.value());
        }

        builder.header(HttpHeader.ACCEPT_LANGUAGE, Locale.getDefault().getLanguage());
        builder.header(HttpHeader.USER_AGENT, new UserAgent(this.invocation.getContext()).toString());
        builder.url(this.buildUrl());
        builder.tag(this.invocation.getId());
        builder.method(method.name(), body);
        return builder.build();
    }

    @SuppressWarnings("resource")
    private RequestBody buildRequestBody(final HttpMethod httpMethod) throws RpcException {
        if (!HttpMethod.permitsRequestBody(httpMethod.name())) {
            return null;
        }

        final Map<BodyParameter, Serializable> parameters = this.invocation.getAnnotatedParameters(BodyParameter.class);
        if (null == parameters || parameters.isEmpty()) {
            return null;
        }

        final Method method = this.invocation.getMethod();
        if (!method.isAnnotationPresent(Serialization.class)) {
            throw new IllegalRpcServiceException(method.getName() + " does not annotated by " + Serialization.class.getName());
        }

        final Serialization serialization = method.getAnnotation(Serialization.class);
        final Class<? extends Serializer<? super Serializable>> clazz = serialization.value();
        final HashMap<String, Serializable> args = new LinkedHashMap<String, Serializable>(parameters.size());

        try {
            Constructor<? extends Serializer<? super Serializable>> ctor = null;
            Serializer<? super Serializable> serializer = null;
            
            try {
                ctor = clazz.getDeclaredConstructor(Type.class);
                ctor.setAccessible(true);
                serializer = ctor.newInstance(TypeToken.get(args.getClass()));
            } catch (final NoSuchMethodException e1) {
                try {
                    ctor = clazz.getDeclaredConstructor();
                    serializer = ctor.newInstance();
                } catch (final NoSuchMethodException e2) {
                    throw new IllegalRpcServiceException("Neither " + clazz.getName() + "<init>(" + Type.class.getName() + ") nor " + clazz.getName() + "<init>() found");
                }
            }

            for (final Map.Entry<BodyParameter, Serializable> entry : parameters.entrySet()) {
                args.put(entry.getKey().value(), entry.getValue());
            }

            final InputStream stream = serializer.serialize(args);
            final Buffer buffer = new Buffer().readFrom(stream);

            try {
                return new RequestBody() {

                    @Override
                    public void writeTo(final BufferedSink sink) throws IOException {
                        sink.write(buffer, buffer.size());
                    }

                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                };
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

    private HttpUrl buildUrl() {
        final Method method = this.invocation.getMethod();
        final String baseUrl = this.invocation.getUri().toString();
        final Class<? extends RpcService> clazz = this.invocation.getRpcServiceClass();
        final StringBuilder url = new StringBuilder(baseUrl.trim());

        // Process @Path
        if (clazz.isAnnotationPresent(Path.class)) {
            final String segment = clazz.getAnnotation(Path.class).value();
            if (!TextUtils.isEmpty(segment)) {
                if (!segment.startsWith("/") && '/' != url.charAt(url.length() - 1)) {
                    url.append("/");
                }
                url.append(segment);
            }
        }

        // Process @PathParameter
        if (method.isAnnotationPresent(Path.class)) {
            String segment = method.getAnnotation(Path.class).value();
            if (!TextUtils.isEmpty(segment)) {
                final Map<PathParameter, Serializable> parameters = this.invocation.getAnnotatedParameters(PathParameter.class);
                if (null != parameters && !parameters.isEmpty()) {
                    String tmp = segment;
                    for (final Map.Entry<PathParameter, Serializable> nvp : parameters.entrySet()) {
                        tmp = segment.replaceAll("\\{" + nvp.getKey().value() + "\\}", String.valueOf(nvp.getValue()));
                    }
                    segment = tmp;
                }

                if (!segment.startsWith("/") && '/' != url.charAt(url.length() - 1)) {
                    url.append("/");
                }

                url.append(segment);
            }
        }

        // Process @QueryParameter
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(url.toString()).newBuilder();
        final Map<QueryParameter, Serializable> queryParams = this.invocation.getAnnotatedParameters(QueryParameter.class);
        if (null != queryParams && !queryParams.isEmpty()) {
            for (final Map.Entry<QueryParameter, Serializable> param : queryParams.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey().value(), String.valueOf(param.getValue()));
            }
        }

        return urlBuilder.build();
    }

    private HttpMethod getRequestMethod() {
        final Method method = this.invocation.getMethod();

        if (method.isAnnotationPresent(Post.class)) {
            return HttpMethod.POST;
        }

        if (method.isAnnotationPresent(Put.class)) {
            return HttpMethod.PUT;
        }

        if (method.isAnnotationPresent(Delete.class)) {
            return HttpMethod.DELETE;
        }

        if (method.isAnnotationPresent(Head.class)) {
            return HttpMethod.HEAD;
        }

        if (method.isAnnotationPresent(Patch.class)) {
            return HttpMethod.PATCH;
        }

        return HttpMethod.GET;

    }

}
