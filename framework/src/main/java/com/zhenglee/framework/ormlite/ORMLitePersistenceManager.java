package com.zhenglee.framework.ormlite;

import com.zhenglee.framework.persistence.PersistentObject;
import com.zhenglee.framework.persistence.android.SQLitePersistenceManager;

public interface ORMLitePersistenceManager extends SQLitePersistenceManager {

    /**
     * Returns the upgrade handlers
     *
     * @return the upgrade handlers
     */
    public <T extends PersistentObject> ORMLiteAccessObject<T> getDataAccessObject(Class<T> clazz);

}
