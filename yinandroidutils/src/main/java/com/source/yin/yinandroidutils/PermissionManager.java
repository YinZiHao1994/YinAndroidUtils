package com.source.yin.yinandroidutils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * android 6.0 权限管理工具，调用获取相应权限方法，并在调用（构造器传入）的 activity 的
 * {@link Activity#onRequestPermissionsResult(int, String[], int[])}回调处
 * 调用{@link #onRequestPermissionsResult(int, String[], int[])}
 * Created by yin on 2017/12/27.
 */

public class PermissionManager {

    private Activity activity;
    private PermissionResultCallBack permissionResultCallBack;

    private static final int REQUEST_CODE = 1994;

    public PermissionManager(Activity activity, PermissionResultCallBack permissionResultCallBack) {

        this.activity = activity;
        this.permissionResultCallBack = permissionResultCallBack;
    }


    public void cameraPermission() {
        String[] permissions = {Manifest.permission.CAMERA};
        dealWithPermission(permissions);
    }

    public void readExternalStorage() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        dealWithPermission(permissions);
    }

    public void writeExternalStorage() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        dealWithPermission(permissions);
    }


    private void dealWithPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0 以下系统
            if (permissionResultCallBack != null) {
                permissionResultCallBack.onPermissionGranted(permissions[0]);
            }
            return;
        }
        if (isPermissionGranted(permissions[0])) {
            if (permissionResultCallBack != null) {
                permissionResultCallBack.onPermissionGranted(permissions[0]);
            }
        } else {
            // 注意：如果AndroidManifest.xml中没有进行权限声明，这里配置了也是无效的，不会有弹窗提示。
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
        }
    }


    //如果返回true表示已经授权了
    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionResultCallBack != null) {
            if (requestCode == REQUEST_CODE) {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        permissionResultCallBack.onPermissionGranted(permissions[0]);
                    } else {
                        permissionResultCallBack.onPermissionDenied(permissions[0]);
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                            permissionResultCallBack.shouldShowRequestPermissionRationale(permissions[0]);
                        }
                    }
                } else {
                    permissionResultCallBack.onPermissionDenied(permissions[0]);
                }
            }
        }
    }


    public interface PermissionResultCallBack {
        void onPermissionGranted(String permission);

        void onPermissionDenied(String permission);

        void shouldShowRequestPermissionRationale(String permission);

    }
}
