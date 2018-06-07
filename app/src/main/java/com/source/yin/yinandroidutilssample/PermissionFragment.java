package com.source.yin.yinandroidutilssample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.source.yin.yinandroidutils.PermissionManager;

public class PermissionFragment extends Fragment {

    private PermissionManager permissionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_permission_manager, container, false);
        View writeStorage = inflate.findViewById(R.id.btn_write_storage);
        writeStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionManager = new PermissionManager(PermissionFragment.this, new PermissionManager.PermissionResultCallBack() {
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
                permissionManager.writeExternalStorage();
            }
        });
        return inflate;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionManager != null) {
            permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
