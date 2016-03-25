package com.zhenglee.framework.net.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class UserAgent {

    private final PackageInfo pkgInfo;

    public UserAgent(final Context context) {
        PackageInfo pi = null;

        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (final NameNotFoundException e) {
        }

        this.pkgInfo = pi;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Android/");
        builder.append(Build.VERSION.RELEASE);
        builder.append(" (");
        builder.append(Build.BOARD);
        builder.append("; ");
        builder.append(Build.PRODUCT);
        builder.append("; ");
        builder.append(Build.MODEL);
        builder.append("; ");
        builder.append(Build.MODEL);
        builder.append(")");

        if (null != this.pkgInfo) {
            builder.append(" ");
            builder.append(pkgInfo.packageName);
            builder.append("/");
            builder.append(pkgInfo.versionName);
            builder.append(" ");
            builder.append(pkgInfo.versionCode);
        }

        return builder.toString();
    }

}
