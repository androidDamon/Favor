package com.zhenglee.framework.http.ext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Serialize the data to form format
 * 
 * @author johnsonlee
 *
 */
public class FormSerializer extends JsonSerializer {

    public FormSerializer(final Type type) {
        super(type);
    }

    @Override
    public InputStream serialize(final Serializable o) throws IOException {
        final Gson gson = this.getGson();
        final StringBuilder builder = new StringBuilder();
        final JsonElement json = gson.toJsonTree(o, getType());
        this.serialize(json.getAsJsonObject(), builder);
        return new ByteArrayInputStream(builder.toString().getBytes());
    }

    private void serialize(final JsonObject json, final StringBuilder builder) throws IOException {
        for (final Iterator<Entry<String, JsonElement>> it = json.entrySet().iterator(); it.hasNext();) {
            final Entry<String, JsonElement> entry = it.next();
            final String key = entry.getKey();
            final JsonElement value = entry.getValue();

            if (value.isJsonObject()) {
                continue;
            } else if (value.isJsonArray()) {
                final JsonArray array = value.getAsJsonArray();
                final int n = array.size();
                if (n > 0) {
                    builder.append(key);
                    builder.append('=');
                    builder.append(URLEncoder.encode(array.get(0).toString(), "UTF-8"));
                    for (int i = 1; i < n; i++) {
                        builder.append('&');
                        builder.append(key);
                        builder.append('=');
                        builder.append(URLEncoder.encode(array.get(i).toString(), "UTF-8"));
                    }
                }
            } else {
                builder.append(key);
                builder.append('=');
                builder.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }

            if (it.hasNext()) {
                builder.append('&');
            }
        }
    }

}
