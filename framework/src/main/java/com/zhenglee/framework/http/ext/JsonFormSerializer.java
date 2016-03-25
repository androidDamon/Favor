package com.zhenglee.framework.http.ext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonFormSerializer extends FormSerializer {

    public JsonFormSerializer(final Type type) {
        super(type);
    }

    @Override
    public InputStream serialize(final Serializable o) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final JsonObject json = getGson().toJsonTree(o, getType()).getAsJsonObject();

        for (final Iterator<Entry<String, JsonElement>> it = json.entrySet().iterator(); it.hasNext();) {
            final Entry<String, JsonElement> entry = it.next();
            builder.append(entry.getKey()).append('=').append(entry.getValue());

            if (it.hasNext()) {
                builder.append('&');
            }
        }

        return new ByteArrayInputStream(builder.toString().getBytes());
    }

}
