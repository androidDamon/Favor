package com.zhenglee.framework.net.http.mime;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * @author johnsonlee
 */
class StringMultipartBody extends ByteArrayMultipartBody {

    private static byte[] getBytes(final String text, final MimeType mimeType) {
        final Charset charset = mimeType.getCharset();
        final String charsetName = (null != charset ? charset : CHARSET_ASCII).name();

        try {
            return text.getBytes(charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedCharsetException(charsetName);
        }
    }

    public StringMultipartBody(final String text) {
        super(getBytes(text, MimeType.TEXT_PLAIN), MimeType.TEXT_PLAIN, null);
    }

    public StringMultipartBody(final String text, final MimeType mimeType) {
        super(getBytes(text, mimeType), mimeType, null);
    }

    @Override
    public String getTransferEncoding() {
        return CONTENT_TRANSFER_ENCODING_8BIT;
    }

}
