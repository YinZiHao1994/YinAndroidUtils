package com.source.yin.yinandroidutilssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContainFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain_fragment);
        PermissionFragment permissionFragment = new PermissionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.root, permissionFragment).commit();
    }
}
