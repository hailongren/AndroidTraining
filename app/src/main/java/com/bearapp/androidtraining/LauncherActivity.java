package com.bearapp.androidtraining;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LauncherActivity extends Activity {

    @BindView(R.id.btn_runtime_permission)
    Button btnRuntimePermission;
    @BindView(R.id.btn_activity_result)
    Button btnActivityResult;
    @BindView(R.id.btn_share_simple_data)
    Button btnShareSimpleData;
    @BindView(R.id.btn_share_file)
    Button btnShareFile;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // should show the explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("应用需要读取本地存储")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermission();
                            }
                        });
                builder.show();

            } else {
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(), "未获得权限!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
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

    @OnClick(R.id.btn_share_simple_data)
    void shareSimpleDataClick() {
        Intent intent = new Intent(this, ShareSimpleDataActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_share_file)
    void shareFileClick() {
        Intent intent = new Intent(this, ShareFileActivity.class);
        startActivity(intent);
    }
}
