package com.source.yin.yinandroidutils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * android 6.0 权限管理工具，调用获取相应权限方法，并在调用（构造器传入）的 activity 的
 * {@link Activity#onRequestPermissionsResult(int, String[], int[])}回调处
 * 调用{@link #onRequestPermissionsResult(int, String[], int[])}
 * Created by yin on 2017/12/27.
 */

public class PermissionManager {

    private Activity activity;
    private Fragment fragment;
    private PermissionResultCallBack permissionResultCallBack;

    private static final int REQUEST_CODE = 1994;

    public PermissionManager(Activity activity, PermissionResultCallBack permissionResultCallBack) {
        this.activity = activity;
        this.permissionResultCallBack = permissionResultCallBack;
    }

    public PermissionManager(Fragment fragment, PermissionResultCallBack permissionResultCallBack) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        this.permissionResultCallBack = permissionResultCallBack;
    }


    public void camera() {
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

    public void readPhoneState() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE};
        dealWithPermission(permissions);
    }

    public void location() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,};
        dealWithPermission(permissions);
    }


    public void dealWithPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0 以下系统
            if (permissionResultCallBack != null) {
                permissionResultCallBack.onPermissionGranted(permissions);
            }
            return;
        }

        List<String> grantedPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();

        for (String permission : permissions) {
            boolean permissionGranted = isPermissionGranted(permission);
            if (permissionGranted) {
                grantedPermissions.add(permission);
            } else {
                deniedPermissions.add(permission);
            }
        }
        if (grantedPermissions.size() > 0) {
            if (permissionResultCallBack != null) {
                permissionResultCallBack.onPermissionGranted(grantedPermissions.toArray(new String[0]));
            }
        }
        if (deniedPermissions.size() > 0) {
            // 注意：如果AndroidManifest.xml中没有进行权限声明，这里配置了也是无效的，不会有弹窗提示。
            if (fragment != null) {
                fragment.requestPermissions(permissions, REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(activity, deniedPermissions.toArray(new String[0]), REQUEST_CODE);
            }
        }
    }


    //如果返回true表示已经授权了
    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        Log.e("yzh", "onRequestPermissionsResult\npermissions = " + Arrays.toString(permissions) + "\ngrantResults = " + Arrays.toString(grantResults));
        if (requestCode == REQUEST_CODE) {
            if (permissionResultCallBack != null) {
                if (grantResults.length > 0) {
                    List<String> grantedPermissions = new ArrayList<>();
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < permissions.length && i < grantResults.length; i++) {
                        String permission = permissions[i];
                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions.add(permission);
                        } else {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (grantedPermissions.size() > 0) {
                        permissionResultCallBack.onPermissionGranted(grantedPermissions.toArray(new String[0]));
                    }
                    if (deniedPermissions.size() > 0) {
                        permissionResultCallBack.onPermissionDenied(deniedPermissions.toArray(new String[0]));
                        for (String deniedPermission : deniedPermissions) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, deniedPermission)) {
                                permissionResultCallBack.shouldShowRequestPermissionRationale(deniedPermission);
                            }
                        }
                    }
                } else {
                    permissionResultCallBack.onPermissionDenied(permissions);
                }
            }
        }
    }


    public interface PermissionResultCallBack {
        void onPermissionGranted(String[] permissions);

        void onPermissionDenied(String[] permissions);

        void shouldShowRequestPermissionRationale(String permission);

    }
}
