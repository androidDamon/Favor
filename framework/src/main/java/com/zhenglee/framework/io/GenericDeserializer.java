package com.zhenglee.framework.io;

import java.io.Serializable;
import java.lang.reflect.Type;

public abstract class GenericDeserializer implements Deserializer<Serializable> {

    private final Type type;

    public GenericDeserializer(final Type type) {
        this.type = type;
    }

    public final Type getType() {
        return this.type;
    }

}
