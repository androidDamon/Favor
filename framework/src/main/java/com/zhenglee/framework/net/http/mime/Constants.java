package com.zhenglee.framework.net.http.mime;

import java.nio.charset.Charset;

/**
 * @author johnsonlee
 * 
 * @see <a href="https://www.ietf.org/rfc/rfc2046.txt">https://www.ietf.org/rfc/rfc2046.txt</a>
 */
public interface Constants {

    public static final String CONTENT_TRANSFER_ENCODING_7BIT = "7bit";

    public static final String CONTENT_TRANSFER_ENCODING_8BIT = "8bit";

    public static final String CONTENT_TRANSFER_ENCODING_BINARY = "binary";

    public static final Charset CHARSET_ASCII = Charset.forName("US-ASCII");

    public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    public static final Charset CHARSET_ISO_8859_1 = Charset.forName("ISO-8859-1");

}
