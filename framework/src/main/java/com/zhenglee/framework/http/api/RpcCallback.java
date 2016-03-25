package com.zhenglee.framework.http.api;

import com.zhenglee.framework.reflect.TypeToken;

public abstract class RpcCallback<T> extends TypeToken<T> {

    public abstract void onSuccess(final T t);

    public abstract void onFailure(final Throwable t);

    @Override
    public String toString() {
        return getClass().getName() + "<" + super.toString() + ">";
    }
    
}
