package com.bearapp.androidtraining;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.content.FileProvider.getUriForFile;

public class ShareFileActivity extends Activity {

    private static final String FILE_NAME = "myfile.txt";

    @BindView(R.id.et_text)
    EditText editText;

    @BindView(R.id.btn_save_file)
    Button btnSaveFile;

    @BindView(R.id.btn_share_file)
    Button btnShareFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_file);
        ButterKnife.bind(this);
        btnShareFile.setEnabled(false);
    }

    @OnClick(R.id.btn_save_file)
    void onSaveFile() {
        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getApplicationContext(), "请输入文本", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        btnShareFile.setEnabled(true);
    }

    @OnClick(R.id.btn_share_file)
    void onShareFile() {
        File myFile = new File(getFilesDir(), FILE_NAME);
        Log.d("HenryTest", myFile.getAbsolutePath());
        Uri fileUri = getUriForFile(getApplicationContext(),
                BuildConfig.APPLICATION_ID + ".provider", myFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        startActivity(intent);
    }
}
