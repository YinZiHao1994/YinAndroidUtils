package com.source.yin.yinandroidutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPermissionManagerInActivity;
    private Button btnPermissionManagerInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPermissionManagerInActivity = findViewById(R.id.btn_permission_manager_in_activity);
        btnPermissionManagerInFragment = findViewById(R.id.btn_permission_manager_in_fragment);

        btnPermissionManagerInActivity.setOnClickListener(this);
        btnPermissionManagerInFragment.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_permission_manager_in_activity:
                intent = new Intent(getApplicationContext(), PermissionManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_permission_manager_in_fragment:
                intent = new Intent(getApplicationContext(), ContainFragmentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
