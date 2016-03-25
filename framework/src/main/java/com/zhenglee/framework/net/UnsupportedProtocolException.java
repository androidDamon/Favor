package com.zhenglee.framework.net;

import android.net.Uri;

public class UnsupportedProtocolException extends RuntimeException {

    private static final long serialVersionUID = -2160824710845290698L;

    private final Uri uri;

    public UnsupportedProtocolException(final Uri url, final String message) {
        super(message);
        this.uri = url;
    }

    public String getReason() {
        return super.getMessage();
    }

    @Override
    public String getMessage() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getReason()).append(": ").append(this.uri);
        return builder.toString();
    }
}
