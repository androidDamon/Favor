package com.zhenglee.framework.persistence.android;

import android.database.sqlite.SQLiteDatabase;

import com.zhenglee.framework.persistence.AbstractUpgradeHandler;
import com.zhenglee.framework.persistence.PersistenceManager;

/**
 * A sub interface of interface {@link PersistenceManager} for SQLite
 */
public interface SQLitePersistenceManager extends PersistenceManager {

    /**
     * Returns the upgrade handlers
     *
     * @return the upgrade handlers
     */
    public abstract AbstractUpgradeHandler[] getUpgradeHandlers();

    /**
     * Returns a writable {@link SQLiteDatabase}
     *
     * @return a writable {@link SQLiteDatabase}
     */
    public abstract SQLiteDatabase getReadableDatabase();

    /**
     * Returns a readable {@link SQLiteDatabase}
     *
     * @return a readable {@link SQLiteDatabase}
     */
    public abstract SQLiteDatabase getWritableDatabase();

}
