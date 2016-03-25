package com.zhenglee.framework.net.http.mime;


import com.zhenglee.framework.net.http.HttpHeader;

/**
 * MultipartFormPart is a port of {@link MultipartForm}
 *
 * @author johnsonlee
 */
class MultipartFormPart {

    private final String name;

    private final MultipartBody body;

    private final MultipartHeaders headers;

    public MultipartFormPart(final String name, final MultipartBody body) {
        this.name = name;
        this.body = body;
        this.headers = new MultipartHeaders();
        this.headers.add(HttpHeader.CONTENT_DISPOSITION, generateContentDisposition(body));
        this.headers.add(HttpHeader.CONTENT_TYPE, body.getMimeType().toString());
        this.headers.add(HttpHeader.CONTENT_TRANSFER_ENCODING, body.getTransferEncoding());
    }

    public String getName() {
        return this.name;
    }

    public MultipartHeaders getHeaders() {
        return this.headers;
    }

    public MultipartBody getBody() {
        return this.body;
    }

    protected String generateContentDisposition(final MultipartBody body) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("form-data; name=\"");
        buffer.append(getName());
        buffer.append("\"");

        final String filename = body.getFilename();
        if (filename != null) {
            buffer.append("; filename=\"");
            buffer.append(filename);
            buffer.append("\"");
        }

        return buffer.toString();
    }

}
