# YinAndroidUtils
an android utils library

# 引入
compile 'com.yinzihao:YinAndroidUtils:{latest-version}'

# 使用指南

## PermissionManager
Android 6.0 权限管理工具
  
```
    permissionManager = new PermissionManager(this, new PermissionManager.PermissionResultCallBack() {
        @Override
        public void onPermissionGranted(String permission) {
            toast(permission + "权限成功");
        }

        @Override
        public void onPermissionDenied(String permission) {
            toast(permission + "权限失败");
        }

        @Override
        public void shouldShowRequestPermissionRationale(String permission) {
            toast("为什么需要" + permission + "权限 : 亲爱的用户，我是你爹");
        }
    });
```
  
在需要申请权限爱你的地方创建 `PermissionManager` ，然后调用 `permissionManager.cameraPermission()`、
`permissionManager.readExternalStorage()`、
`permissionManager.writeExternalStorage()`
来申请摄像头、读写储存卡权限。或者调用 `permissionManager.dealWithPermission()` 申请其他权限。  
并在 `onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)` 中调用 `PermissionManager` 的 `onRequestPermissionsResult(requestCode, permissions, grantResults)` 方法。  

```
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```



