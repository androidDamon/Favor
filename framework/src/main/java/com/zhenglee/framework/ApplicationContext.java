package com.zhenglee.framework;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.zhenglee.framework.runtime.DefaultUncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhenglee on 15/11/12.
 */
public class ApplicationContext extends Application implements Application.ActivityLifecycleCallbacks {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    private static ApplicationContext instance;

    private Object cache;

    public static final ApplicationContext getInstance() {
        return instance;
    }

    public ApplicationContext(Object cache) {
        ApplicationContext.instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler());

        logger.info(getLineSeparator(80, getPackageName()));
        logger.info("> ro.bootloader                : " + Build.BOOTLOADER);
        logger.info("> ro.build.id                  : " + Build.ID);
        logger.info("> ro.build.display.id          : " + Build.DISPLAY);
        logger.info("> ro.build.version.incremental : " + Build.VERSION.INCREMENTAL);
        logger.info("> ro.build.version.release     : " + Build.VERSION.RELEASE);
        logger.info("> ro.build.version.sdk         : " + Build.VERSION.SDK_INT);
        logger.info("> ro.build.version.codename    : " + Build.VERSION.CODENAME);
        logger.info("> ro.build.type                : " + Build.TYPE);
        logger.info("> ro.build.tags                : " + Build.TAGS);
        logger.info("> ro.build.fingerprint         : " + Build.FINGERPRINT);
        logger.info("> ro.build.date.utc            : " + Build.TIME);
        logger.info("> ro.build.user                : " + Build.USER);
        logger.info("> ro.build.host                : " + Build.HOST);
        logger.info("> ro.hardware                  : " + Build.HARDWARE);
        logger.info("> ro.product.board             : " + Build.BOARD);
        logger.info("> ro.product.brand             : " + Build.BRAND);
        logger.info("> ro.product.cpu.abi           : " + Build.CPU_ABI);
        logger.info("> ro.product.cpu.abi2          : " + Build.CPU_ABI2);
        logger.info("> ro.product.device            : " + Build.DEVICE);
        logger.info("> ro.product.manufacturer      : " + Build.MANUFACTURER);
        logger.info("> ro.product.model             : " + Build.MODEL);
        logger.info("> ro.product.name              : " + Build.PRODUCT);
        logger.info("> ro.serialno                  : " + Build.SERIAL);
        logger.info("===============================================================================");
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private static String getLineSeparator(int width, String title) {
        final int w = Math.max(0, width - 2);
        final int l = (w - title.length()) / 2;
        final int r = (w - title.length() - l);
        final StringBuilder seprator = new StringBuilder();

        for (int i = 0; i < l; i++) {
            seprator.append('=');
        }

        seprator.append(' ').append(title).append(' ');

        for (int i = 0; i < r; i++) {
            seprator.append('=');
        }
        return seprator.toString();
    }
}
