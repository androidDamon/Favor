package com.zhenglee.framework.http.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhenglee.framework.io.GenericSerializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;

public class JsonSerializer extends GenericSerializer {

    private final Gson gson = new GsonBuilder().create();

    public JsonSerializer(final Type type) {
        super(type);
    }

    public InputStream serialize(final Serializable o) throws IOException {
        return new ByteArrayInputStream(this.gson.toJson(o, getType()).getBytes());
    }

    protected Gson getGson() {
        return this.gson;
    }

}
