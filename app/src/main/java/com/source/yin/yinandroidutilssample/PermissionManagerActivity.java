package com.source.yin.yinandroidutilssample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.source.yin.yinandroidutils.PermissionManager;

public class PermissionManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCamera;
    private Button btnReadStorage;
    private Button btnWriteStorage;

    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_manager);

        btnCamera = findViewById(R.id.btn_camera);
        btnReadStorage = findViewById(R.id.btn_read_storage);
        btnWriteStorage = findViewById(R.id.btn_write_storage);

        btnCamera.setOnClickListener(this);
        btnReadStorage.setOnClickListener(this);
        btnWriteStorage.setOnClickListener(this);


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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                permissionManager.cameraPermission();
                break;
            case R.id.btn_read_storage:
                permissionManager.readExternalStorage();
                break;
            case R.id.btn_write_storage:
                permissionManager.writeExternalStorage();
                break;
        }
    }


    public void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
