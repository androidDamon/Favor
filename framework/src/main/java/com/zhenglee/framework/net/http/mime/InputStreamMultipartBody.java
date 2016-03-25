package com.zhenglee.framework.net.http.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author johnsonlee
 */
class InputStreamMultipartBody extends AbstractMultipartBody {

    private final InputStream stream;

    public InputStreamMultipartBody(final InputStream stream) {
        this(stream, MimeType.APPLICATION_OCTET_STREAM);
    }

    public InputStreamMultipartBody(final InputStream stream, final MimeType mimeType) {
        this(stream, mimeType, null);
    }

    public InputStreamMultipartBody(final InputStream stream, final MimeType mimeType, final String filename) {
        super(mimeType, filename);
        this.stream = stream;
    }

    public String getTransferEncoding() {
        return CONTENT_TRANSFER_ENCODING_BINARY;
    }

    public long getContentLength() {
        try {
            return this.stream.available();
        } catch (final IOException e) {
            return -1;
        }
    }

    public void writeTo(final OutputStream out) throws IOException {
        final byte[] buf = new byte[4096];

        try {
            for (int n; (n = this.stream.read(buf)) != -1;) {
                out.write(buf, 0, n);
            }

            out.flush();
        } finally {
            this.stream.close();
        }
    }

    public InputStream getInputStream() {
        return this.stream;
    }

}
