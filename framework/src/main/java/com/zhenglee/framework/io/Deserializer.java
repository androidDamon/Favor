package com.zhenglee.framework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * The interface for object deserialization
 *
 * @author johnsonlee
 */
public interface Deserializer<T extends Serializable> {

    /**
     * Deserialize object from input stream
     *
     * @param stream
     *            The data stream
     * @return An object
     * @throws IOException
     */
    T deserialize(final InputStream stream) throws IOException;

}
