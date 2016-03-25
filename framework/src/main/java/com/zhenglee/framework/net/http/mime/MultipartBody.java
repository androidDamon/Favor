package com.zhenglee.framework.net.http.mime;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * The body of the multipart form part
 *
 * @author johnsonlee
 */
public interface MultipartBody {

    public MimeType getMimeType();

    public Charset getCharset();

    public String getTransferEncoding();

    public long getContentLength();

    public String getFilename();

    public void writeTo(final OutputStream out) throws IOException;

}
