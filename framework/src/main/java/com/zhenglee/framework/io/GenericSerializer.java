package com.zhenglee.framework.io;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by zhenglee on 16/1/4.
 */
public abstract class GenericSerializer implements Serializer<Serializable> {

    private final Type type;

    public GenericSerializer(final Type type) {
        this.type = type;
    }

    public final Type getType() {
        return this.type;
    }

}
