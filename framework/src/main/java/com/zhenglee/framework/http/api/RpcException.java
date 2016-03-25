package com.zhenglee.framework.http.api;

public class RpcException extends Exception {

    private static final long serialVersionUID = 6795079975742537714L;

    private final int code;

    public RpcException(final int code) {
        this(-1, null, null);
    }

    public RpcException(final int code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public RpcException(final int code, final String message) {
        this(code, message, null);
    }

    public RpcException(final int code, final Throwable cause) {
        this(code, null, cause);
    }

    public int getCode() {
        return this.code;
    }

}
