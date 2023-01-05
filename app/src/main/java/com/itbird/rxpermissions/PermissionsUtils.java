package com.itbird.rxpermissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by itbird on 2022/12/26
 */
public class PermissionsUtils {
    public static final int REQUEST_CODE = 5;

    //每个权限是否已授
    public static boolean checkPermission(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(activity, permission);
            Log.e("Permission", "checkPermission = " + checkPermission);
            /***
             * checkPermission返回两个值
             * 有权限: PackageManager.PERMISSION_GRANTED
             * 无权限: PackageManager.PERMISSION_DENIED
             */
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    public static void requestPermissions(Activity activity, String... permission) {
        ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE);
    }
}
