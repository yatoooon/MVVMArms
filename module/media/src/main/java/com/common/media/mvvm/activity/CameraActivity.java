package com.common.media.mvvm.activity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.common.core.base.BaseActivity;
import com.common.export.callback.OnCameraListener;
import com.common.media.R;
import com.common.res.aop.Log;
import com.common.res.aop.Permissions;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/12/18
 * desc   : 拍摄图片、视频
 */
public final class CameraActivity extends BaseActivity {

    public static final String INTENT_KEY_IN_FILE = "file";
    public static final String INTENT_KEY_IN_VIDEO = "video";

    public static final String INTENT_KEY_OUT_ERROR = "error";

    @Log
    @Permissions({Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA})
    public static void start(BaseActivity activity, boolean video, OnCameraListener listener) {
        File file = createCameraFile(video);
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(INTENT_KEY_IN_FILE, file);
        intent.putExtra(INTENT_KEY_IN_VIDEO, video);
        activity.startActivityForResult(intent, (resultCode, data) -> {
            if (listener == null) {
                return;
            }

            switch (resultCode) {
                case RESULT_OK:
                    if (file.isFile()) {
                        listener.onSelected(file);
                    } else {
                        listener.onCancel();
                    }
                    break;
                case RESULT_ERROR:
                    String details;
                    if (data == null || (details = data.getStringExtra(INTENT_KEY_OUT_ERROR)) == null) {
                        details = activity.getString(R.string.res_common_unknown_error);
                    }
                    listener.onError(details);
                    break;
                case RESULT_CANCELED:
                default:
                    listener.onCancel();
                    break;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        Intent intent = new Intent();
        // 启动系统相机
        if (getBoolean(INTENT_KEY_IN_VIDEO)) {
            // 录制视频
            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        } else {
            // 拍摄照片
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        }

        if (intent.resolveActivity(getPackageManager()) == null ||
                !XXPermissions.isGranted(this, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)) {
            setResult(RESULT_ERROR, new Intent().putExtra(INTENT_KEY_OUT_ERROR, getString(R.string.res_camera_launch_fail)));
            finish();
            return;
        }

        File file = getSerializable(INTENT_KEY_IN_FILE);
        if (file == null) {
            setResult(RESULT_ERROR, new Intent().putExtra(INTENT_KEY_OUT_ERROR, getString(R.string.res_camera_image_error)));
            finish();
            return;
        }

        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过 FileProvider 创建一个 Content 类型的 Uri 文件
            imageUri = FileProvider.getUriForFile(this, getContext().getPackageName() + ".provider", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        // 对目标应用临时授权该 Uri 所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 将拍取的照片保存到指定 Uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, (resultCode, data) -> {
            if (resultCode == RESULT_OK) {
                // 通知系统多媒体扫描该文件，否则会导致拍摄出来的图片或者视频没有及时显示到相册中，而需要通过重启手机才能看到
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getPath()}, null, null);
            }
            setResult(resultCode);
            finish();
        });
    }

    /**
     * 创建一个拍照图片文件路径
     */
    private static File createCameraFile(boolean video) {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (!folder.exists() || !folder.isDirectory()) {
            if (!folder.mkdirs()) {
                folder = Environment.getExternalStorageDirectory();
            }
        }

        return new File(folder, (video ? "VID" : "IMG") + "_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) +
                (video ? ".mp4" : ".jpg"));
    }


}