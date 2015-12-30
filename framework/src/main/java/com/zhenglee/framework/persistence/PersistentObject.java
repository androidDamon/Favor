package com.zhenglee.framework.persistence;

import java.io.Serializable;

/**
 * {@link PersistentObject} is used by {@link DataAccessObject} to persist into storage
 */
public interface PersistentObject extends Serializable {

    /**
     * Returns the unique identifier of this instance
     *
     * @return the unique identifier
     */
    public String getId();

}
