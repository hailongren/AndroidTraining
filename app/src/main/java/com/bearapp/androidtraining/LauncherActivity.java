package com.bearapp.androidtraining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LauncherActivity extends Activity {

    @BindView(R.id.btn_runtime_permission)
    Button btnRuntimePermission;
    @BindView(R.id.btn_activity_result)
    Button btnActivityResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_activity_result)
    void activityResultClick() {
        Intent intent = new Intent(this, ActivityResultActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_runtime_permission)
    void runtimePermissionClick() {
        Intent intent = new Intent(this, RuntimePermissionActivity.class);
        startActivity(intent);
    }
}
