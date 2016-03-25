package com.zhenglee.framework.net.http.mime;


import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * The builder for {@link MultipartForm} building
 *
 * @author johnsonlee
 */
public class MultipartFormBuilder {

    private Charset charset;

    private String boundary;

    private final List<MultipartFormPart> parts;

    public MultipartFormBuilder() {
        this.parts = new ArrayList<MultipartFormPart>();
    }

    public Charset getCharset() {
        return this.charset;
    }

    public MultipartFormBuilder setCharset(final Charset charset) {
        this.charset = charset;
        return this;
    }

    public String getBoundary() {
        return this.boundary;
    }

    public MultipartFormBuilder setBoundary(final String boundary) {
        this.boundary = boundary;
        return this;
    }

    public MultipartFormBuilder addPart(final MultipartFormPart part) {
        this.parts.add(part);
        return this;
    }

    public MultipartFormBuilder addPart(final String name, final com.zhenglee.framework.net.http.mime.MultipartBody body) {
        return addPart(new MultipartFormPart(name, body));
    }

    public MultipartFormBuilder addBody(final String name, final byte[] value) {
        return addPart(name, new ByteArrayMultipartBody(value));
    }

    public MultipartFormBuilder addBody(final String name, final byte[] value, final com.zhenglee.framework.net.http.mime.MimeType mimeType) {
        return addPart(name, new ByteArrayMultipartBody(value, mimeType));
    }

    public MultipartFormBuilder addBody(final String name, final File file) {
        return addPart(name, new FileMultipartBody(file, com.zhenglee.framework.net.http.mime.MimeType.guess(file)));
    }

    public MultipartFormBuilder addBody(final String name, final InputStream input) {
        return addPart(name, new InputStreamMultipartBody(input, com.zhenglee.framework.net.http.mime.MimeType.APPLICATION_OCTET_STREAM));
    }

    public MultipartFormBuilder addBody(final String name, final Object value) {
        return addBody(name, value, com.zhenglee.framework.net.http.mime.MimeType.TEXT_PLAIN);
    }

    public MultipartFormBuilder addBody(final String name, final Object value, final com.zhenglee.framework.net.http.mime.MimeType mimeType) {
        return addPart(name, new com.zhenglee.framework.net.http.mime.StringMultipartBody(String.valueOf(value), mimeType));
    }

    public MultipartForm build() {
        final String boundary = null != this.boundary ? this.boundary : MultipartForm.generateBoundary();
        return new MultipartForm(this.charset, boundary, this.parts);
    }

}
