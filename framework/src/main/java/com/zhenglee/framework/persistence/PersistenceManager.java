package com.zhenglee.framework.persistence;

/**
 * {@link PersistenceManager} is used for data persistence
 */
public interface PersistenceManager {

    /**
     * Returns the upgrade handlers
     *
     * @return the upgrade handlers
     */
    public UpgradeHandler[] getUpgradeHandlers();

}
