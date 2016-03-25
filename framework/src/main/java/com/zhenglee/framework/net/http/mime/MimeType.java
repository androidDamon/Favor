package com.zhenglee.framework.net.http.mime;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * @author johnsonlee
 */
public class MimeType {

    /**
     * application/atom+xml
     */
    public static final MimeType APPLICATION_ATOM_XML = new MimeType("application/atom+xml", "ISO8859-1");

    /**
     * application/x-www-form-urlencoded
     */
    public static final MimeType APPLICATION_FORM_URLENCODED = new MimeType("application/x-www-form-urlencoded", "ISO8859-1");

    /**
     * application/json
     */
    public static final MimeType APPLICATION_JSON = new MimeType("application/json", "UTF-8");

    /**
     * application/octet-stream
     */
    public static final MimeType APPLICATION_OCTET_STREAM = new MimeType("application/octet-stream", (Charset) null);

    /**
     * application/xml
     */
    public static final MimeType APPLICATION_XML = new MimeType("application/xml", "UTF-8");

    /**
     * multipart/form-data
     */
    public static final MimeType MULTIPART_FORM_DATA = new MimeType("multipart/form-data", "ISO8859-1");

    /**
     * text/html
     */
    public static final MimeType TEXT_HTML = new MimeType("text/html", "ISO8859-1");

    /**
     * text/plain
     */
    public static final MimeType TEXT_PLAIN = new MimeType("text/plain", "ISO8859-1");

    /**
     * text/xml
     */
    public static final MimeType TEXT_XML = new MimeType("text/xml", "ISO8859-1");

    /**
     * Any MIME type
     */
    public static final MimeType WILDCARD = new MimeType("*/*", (Charset) null);

    /**
     * Guess the MIME type of the specified file
     *
     * @param file The file to guess
     * @return The MIME type of the specified file or
     * {@link #APPLICATION_OCTET_STREAM} instead if the specified file
     * is unrecognizable
     */
    public static MimeType guess(final File file) {
        final Uri uri = Uri.fromFile(file);
        final String ext = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        if (TextUtils.isEmpty(ext)) {
            return APPLICATION_OCTET_STREAM;
        }

        final String contentType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        if (TextUtils.isEmpty(contentType)) {
            return APPLICATION_OCTET_STREAM;
        }

        return new MimeType(contentType, (Charset) null);
    }

    private final String contentType;

    private final Charset charset;

    /**
     * Create an instance with content type and charset name
     *
     * @param contentType The content type
     * @param charset     The charset name
     * @throws UnsupportedCharsetException
     */
    public MimeType(final String contentType, final String charset) throws UnsupportedCharsetException {
        this(contentType, null == charset ? null : Charset.forName(charset));
    }

    /**
     * Create an instance with content type and charset
     *
     * @param contentType The content type
     * @param charset     The charset
     * @throws UnsupportedCharsetException
     */
    public MimeType(final String contentType, final Charset charset) {
        this.contentType = contentType;
        this.charset = charset;
    }

    /**
     * Returns the charset
     *
     * @return
     */
    public Charset getCharset() {
        return this.charset;
    }

    /**
     * Returns the content type
     *
     * @return
     */
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.contentType);

        if (null != this.charset) {
            builder.append("; charset=").append(this.charset.name());
        }

        return builder.toString();
    }

}
