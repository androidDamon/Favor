package com.zhenglee.framework.net.http.mime;


import java.nio.charset.Charset;

/**
 * @author johnsonlee
 */
abstract class AbstractMultipartBody implements MultipartBody, Constants {

    private final MimeType mimeType;

    private final String filename;

    public AbstractMultipartBody(final MimeType mimeType, final String filename) {
        this.mimeType = mimeType;
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

    public MimeType getMimeType() {
        return this.mimeType;
    }

    public Charset getCharset() {
        return this.mimeType.getCharset();
    }

}
