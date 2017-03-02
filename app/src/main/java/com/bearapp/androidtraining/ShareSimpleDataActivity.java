package com.bearapp.androidtraining;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareSimpleDataActivity extends Activity {

    private final String TAG = "HenryTest";

    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.btn_send)
    Button btnSend;

    @BindView(R.id.btn_send_image)
    Button btnSendImage;

    @BindView(R.id.btn_share_action)
    Button btnShareActionProvider;


    private static final int REQUEST_IMAGE_OPEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_simple_data);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send)
    void sendSimpleText() {
        String text = etText.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "分享文字..."));
        }
    }

    /**
     * 对于 Android 4.3 及更低版本，如果您想让应用从其他应用中检索文件，它必须调用 ACTION_PICK 或 ACTION_GET_CONTENT 等 Intent。然后，用户必须选择一个要从中选取文件的应用，并且所选应用必须提供一个用户界面，以便用户浏览和选取可用文件。
     * <p>
     * 对于 Android 4.4 及更高版本，您还可以选择使用 ACTION_OPEN_DOCUMENT Intent，后者会显示一个由系统控制的选取器 UI，用户可以通过它浏览其他应用提供的所有文件。用户只需通过这一个 UI 便可从任何受支持的应用中选取文件。
     * <p>
     * ACTION_OPEN_DOCUMENT 并非设计用于替代 ACTION_GET_CONTENT。应使用的 Intent 取决于应用的需要：
     * <p>
     * 如果您只想让应用读取/导入数据，请使用 ACTION_GET_CONTENT。使用此方法时，应用会导入数据（如图像文件）的副本；
     * 如果您想让应用获得对文档提供程序所拥有文档的长期、持久性访问权限，请使用 ACTION_OPEN_DOCUMENT。 例如，允许用户编辑存储在文档提供程序中的图像的照片编辑应用。
     */

    @OnClick(R.id.btn_send_image)
    void sendImageClick() {
        Intent imageOpenIntent = new Intent();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            imageOpenIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        } else {
        imageOpenIntent.setAction(Intent.ACTION_GET_CONTENT);
//        }
        imageOpenIntent.setType("image/*");
        imageOpenIntent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            imageOpenIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        startActivityForResult(imageOpenIntent, REQUEST_IMAGE_OPEN);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            if (Intent.ACTION_SEND_MULTIPLE.equals(data.getAction())) {
                ArrayList<Uri> uriArrayList = data.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                if (uriArrayList != null) {
                    for (Uri uri : uriArrayList) {
                        Log.d(TAG, "uri=" + uri.toString());
                    }
                    sendMultipleImages(uriArrayList);
                }
            } else {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    sendImage(imageUri);
                } else if (data.getClipData() != null) {
                    ArrayList<Uri> uriArrayList = new ArrayList<>();
                    ClipData clipData = data.getClipData();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        Log.d(TAG, "uri=" + uri.toString());
                        uriArrayList.add(uri);
                    }
                    sendMultipleImages(uriArrayList);
                }
            }
        }
    }

    private void sendMultipleImages(ArrayList<Uri> uriArrayList) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriArrayList);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "分享图片到"));
    }

    private void sendImage(Uri uriToImage) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpeg");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        startActivity(Intent.createChooser(sendIntent, "分享图片..."));
    }


    @OnClick(R.id.btn_share_action)
    void openShareActionProviderActivity() {
        Intent intent = new Intent(this, ShareActionProviderActivity.class);
        startActivity(intent);
    }
}
