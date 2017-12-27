package yin.source.com.yinandroidutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPermissionManager = findViewById(R.id.btn_permission_manager);

        btnPermissionManager.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_permission_manager:
                intent = new Intent(getApplicationContext(), PermissionManagerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
