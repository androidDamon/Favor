package com.zhenglee.framework.net.http.mime;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author johnsonlee
 */
class ByteArrayMultipartBody extends AbstractMultipartBody {

    private final byte[] data;

    public ByteArrayMultipartBody(final byte[] data) {
        this(data, MimeType.APPLICATION_OCTET_STREAM);
    }

    public ByteArrayMultipartBody(final byte[] data, final MimeType mimeType) {
        this(data, mimeType, null);
    }

    public ByteArrayMultipartBody(final byte[] data, final MimeType mimeType, final String filename) {
        super(mimeType, filename);

        this.data = data;
    }

    public ByteArrayMultipartBody(final byte[] data, final String filename) {
        this(data, MimeType.APPLICATION_OCTET_STREAM, filename);
    }

    public String getTransferEncoding() {
        return CONTENT_TRANSFER_ENCODING_BINARY;
    }

    public long getContentLength() {
        return this.data.length;
    }

    public void writeTo(final OutputStream out) throws IOException {
        out.write(this.data);
    }
}
