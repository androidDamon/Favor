package com.zhenglee.framework.business;

/**
 * {@link BusinessObject} is in charge of business transaction
 */
public interface BusinessObject {

    /**
     * Returns the business context
     *
     * @return the business context
     */
    public BusinessContext getContext();

}
