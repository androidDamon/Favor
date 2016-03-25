package com.zhenglee.framework.http.api;

public class IllegalRpcServiceException extends RuntimeException {

    private static final long serialVersionUID = 8463293169256006150L;

    public IllegalRpcServiceException() {
        super();
    }

    public IllegalRpcServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRpcServiceException(String message) {
        super(message);
    }

    public IllegalRpcServiceException(Throwable cause) {
        super(cause);
    }

}
