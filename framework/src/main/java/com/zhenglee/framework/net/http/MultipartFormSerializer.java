package com.zhenglee.framework.net.http;

import com.zhenglee.framework.io.Serializer;
import com.zhenglee.framework.net.http.mime.MimeType;
import com.zhenglee.framework.reflect.Transformer;
import com.zhenglee.framework.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link MultipartFormSerializer} is used to serialize java object as {@code multipart/form-data}
 * format. The rule of data type to MIME type conversion showed as below:
 *
 * <ul>
 *     <li>{@code File} -> {@code application/octet-stream}</li>
 *     <li>{@code InputStream} -> {@code application/octet-stream}</li>
 *     <li>{@code byte[]} -> {@code application/octet-stream}</li>
 *     <li>{@code String} -> {@code text/plain}</li>
 *     <li>{@code Object} -> {@code text/plain}</li>
 * </ul>
 *
 * @author johnsonlee
 */
public class MultipartFormSerializer extends TypeToken<Serializable> implements Serializer<Serializable> {

    private final com.zhenglee.framework.net.http.mime.MultipartFormBuilder formBuilder;

    /**
     * Create an instance with the specified type
     *
     */
    public MultipartFormSerializer() {
        this.formBuilder = new com.zhenglee.framework.net.http.mime.MultipartFormBuilder();
        this.formBuilder.setCharset(com.zhenglee.framework.net.http.mime.Constants.CHARSET_UTF_8);
        this.formBuilder.setBoundary(com.zhenglee.framework.net.http.mime.MultipartForm.generateBoundary());
    }

    public String getMimeType() {
        final StringBuilder builder = new StringBuilder();
        builder.append("multipart/form-data; boundary=");
        builder.append(this.formBuilder.getBoundary());

        if (null != this.formBuilder.getCharset()) {
            builder.append("; charset=").append(this.formBuilder.getCharset().name());
        }

        return builder.toString();
    }

    public InputStream serialize(final Serializable o) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            if (o instanceof Map<?, ?>) {
                serialize((Map<?, ?>) o, baos);
            } else {
                serialize(o, baos);
            }

            return new ByteArrayInputStream(baos.toByteArray());
        } finally {
            baos.close();
        }
    }

    com.zhenglee.framework.net.http.mime.MultipartFormBuilder getMultipartFormBuilder() {
        return this.formBuilder;
    }

    private void serialize(final Object o, final ByteArrayOutputStream baos) throws IOException {
        serialize(Transformer.transform(o), baos);
    }

    private void serialize(final Map<?, ?> map, final ByteArrayOutputStream baos) throws IOException {
        final com.zhenglee.framework.net.http.mime.MimeType TEXT_PLAIN = new MimeType(MimeType.TEXT_PLAIN.getContentType(), com.zhenglee.framework.net.http.mime.Constants.CHARSET_UTF_8);

        for (final Iterator<?> i = map.keySet().iterator(); i.hasNext();) {
            final String name = String.valueOf(i.next());
            final Object value = map.get(name);

            if (value instanceof File) {
                this.formBuilder.addBody(name, (File) value);
            } else if (value instanceof InputStream) {
                this.formBuilder.addBody(name, (InputStream) value);
            } else if (value.getClass().isArray() && byte.class.equals(value.getClass().getComponentType())) {
                this.formBuilder.addBody(name, (byte[]) value);
            } else {
                this.formBuilder.addBody(name, value, TEXT_PLAIN);
            }
        }

        this.formBuilder.build().writeTo(baos);
    }

}

