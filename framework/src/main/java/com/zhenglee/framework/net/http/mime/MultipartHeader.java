package com.zhenglee.framework.net.http.mime;



/**
 * @author johnsonlee
 */
class MultipartHeader implements com.zhenglee.framework.net.http.HttpHeader {

    private final String name;

    private final String value;

    public MultipartHeader(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(": ").append(this.value);
        return builder.toString();
    }
}
