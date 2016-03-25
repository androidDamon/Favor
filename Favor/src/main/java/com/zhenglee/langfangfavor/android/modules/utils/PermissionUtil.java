package com.zhenglee.langfangfavor.android.modules.utils;

import android.content.pm.PackageManager;

/**
 * Created by zhenglee on 16/1/4.
 */
public abstract class PermissionUtil {

    public static boolean verifyPermissions(int[] grantResults) {
        if(grantResults.length < 1){
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
