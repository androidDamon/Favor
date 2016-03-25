package com.zhenglee.framework.net.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Trust all hostname
 *
 * @author johnsonlee
 */
public class AllowAllHostnameVerifier implements HostnameVerifier {

    public static final AllowAllHostnameVerifier INSTANCE = new AllowAllHostnameVerifier();

    private AllowAllHostnameVerifier() {
    }

    public boolean verify(final String hostname, final SSLSession session) {
        return true;
    }

}
