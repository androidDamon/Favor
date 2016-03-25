package com.zhenglee.framework.http.api;

public interface RpcServiceInvocationHandler {

    public Object handle(final RpcServiceInvocation invocation) throws RpcException;

}
