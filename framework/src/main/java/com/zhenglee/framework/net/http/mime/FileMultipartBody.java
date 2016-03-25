package com.zhenglee.framework.net.http.mime;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author johnsonlee
 */
class FileMultipartBody extends AbstractMultipartBody {

    private final File file;

    public FileMultipartBody(final File file) {
        this(file, null);
    }

    public FileMultipartBody(final File file, final com.zhenglee.framework.net.http.mime.MimeType mimeType) {
        this(file, mimeType, null == file ? null : file.getName());
    }

    public FileMultipartBody(final File file, final com.zhenglee.framework.net.http.mime.MimeType mimeType, final String filename) {
        super(null == mimeType ? com.zhenglee.framework.net.http.mime.MimeType.APPLICATION_OCTET_STREAM : mimeType, filename);
        this.file = file;
    }

    public String getTransferEncoding() {
        return CONTENT_TRANSFER_ENCODING_BINARY;
    }

    public long getContentLength() {
        return this.file.length();
    }

    public void writeTo(final OutputStream out) throws IOException {
        final byte[] buf = new byte[4096];
        final InputStream in = new FileInputStream(this.file);

        try {
            for (int n; (n = in.read(buf)) != -1;) {
                out.write(buf, 0, n);
            }

            out.flush();
        } finally {
            in.close();
        }
    }

    public File getFile() {
        return this.file;
    }

}
