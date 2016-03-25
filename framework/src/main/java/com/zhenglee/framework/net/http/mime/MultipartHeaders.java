package com.zhenglee.framework.net.http.mime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author johnsonlee
 */
class MultipartHeaders implements Iterable<MultipartHeader> {

    private final List<MultipartHeader> headers;

    private final Map<String, List<MultipartHeader>> headerMap;

    public MultipartHeaders() {
        this.headers = new ArrayList<MultipartHeader>();
        this.headerMap = new HashMap<String, List<MultipartHeader>>();
    }

    public void add(final String name, final String value) {
        this.add(new MultipartHeader(name, value));
    }

    public void add(final com.zhenglee.framework.net.http.HttpHeader header) {
        this.add(new MultipartHeader(header.getName(), header.getValue()));
    }

    public void add(final MultipartHeader header) {
        if (null == header)
            return;

        final String key = header.getName().toLowerCase(Locale.US);

        List<MultipartHeader> headers = this.headerMap.get(key);
        if (null == headers) {
            headers = new ArrayList<MultipartHeader>();
            this.headerMap.put(key, headers);
        }
        headers.add(header);

        this.headers.add(header);
    }

    public Iterator<MultipartHeader> iterator() {
        return Collections.unmodifiableList(this.headers).iterator();
    }

    @Override
    public String toString() {
        return this.headers.toString();
    }
}
