package com.zhenglee.framework.net.http;

public enum HttpMethod {

    GET,

    POST,

    PUT,

    DELETE,

    HEAD,

    PATCH;

    public static boolean requiresRequestBody(String method) {
        return method.equals("POST")
            || method.equals("PUT")
            || method.equals("PATCH")
            || method.equals("PROPPATCH") // WebDAV
            || method.equals("REPORT");   // CalDAV/CardDAV (defined in WebDAV Versioning)
      }

      public static boolean permitsRequestBody(String method) {
        return requiresRequestBody(method)
            || method.equals("OPTIONS")
            || method.equals("DELETE")    // Permitted as spec is ambiguous.
            || method.equals("PROPFIND")  // (WebDAV) without body: request <allprop/>
            || method.equals("MKCOL")     // (WebDAV) may contain a body, but behaviour is unspecified
            || method.equals("LOCK");     // (WebDAV) body: create lock, without body: refresh lock
      }

}
