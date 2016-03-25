package com.zhenglee.framework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by zhenglee on 16/1/4.
 */
public interface Serializer<T extends Serializable> {

    /**
     * Serialize the specified object into input stream
     *
     * @param t
     *            The object to serialize
     * @return The data stream
     * @throws IOException
     */
    InputStream serialize(final T t) throws IOException;

}
