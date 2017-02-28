package com.bearapp.androidtraining;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RuntimePermissionActivity extends Activity {

    @BindView(R.id.btn)
    Button button;
    @BindView(R.id.btn2)
    Button button2;

    private static final int REQUEST_READ_CONTACTS = 1;
    private static final int REQUEST_WRITE_CONTACTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // should show the explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("应用需要读取电话本")
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
        } else {
            Toast.makeText(getApplicationContext(), "已获得读权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn2)
    void checkWritePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "尚未获取写权限", Toast.LENGTH_SHORT).show();
        } else {
            //！！！如果已获得读权限，将会自动获得写权限！！！
            Toast.makeText(getApplicationContext(), "已获得写权限!", Toast.LENGTH_SHORT).show();
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
    }

    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_WRITE_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "已获得读权限!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "未获得读权限!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case REQUEST_WRITE_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "已获得写权限!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "未获得写权限!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
