package com.zhenglee.framework.http.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhenglee.framework.io.GenericDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;

public class JsonDeserializer extends GenericDeserializer {

    private final Gson gson = new GsonBuilder().create();

    public JsonDeserializer(final Type type) {
        super(type);
    }

    public Serializable deserialize(final InputStream stream) throws IOException {
        return this.gson.fromJson(new InputStreamReader(stream), getType());
    }

    protected Gson getGson() {
        return this.gson;
    }
}
