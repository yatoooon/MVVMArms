package com.common.personal.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.common.core.base.BaseActivity;
import com.common.personal.R;
import com.common.res.aop.SingleClick;
import com.common.res.dialog.InputDialog;
import com.common.res.glide.config.ImageConfigImpl;
import com.common.res.layout.SettingBar;
import com.common.res.other.FileContentResolver;
import com.common.res.router.RouterHub;
import com.common.res.utils.ArmsUtil;


import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/04/20
 * desc   : 个人资料
 */
@Route(path = RouterHub.PUBLIC_PERSONAL_PERSONALDATAACTIVITY)
public final class PersonalDataActivity extends BaseActivity {

    private ViewGroup mAvatarLayout;
    private ImageView mAvatarView;
    private SettingBar mIdView;
    private SettingBar mNameView;
    private SettingBar mAddressView;

    /**
     * 省
     */
    private String mProvince = "广东省";
    /**
     * 市
     */
    private String mCity = "广州市";
    /**
     * 区
     */
    private String mArea = "天河区";

    /**
     * 头像地址
     */
    private Uri mAvatarUrl;

    @Override
    public int getLayoutId() {
        return R.layout.personal_data_activity;
    }

    @Override
    public void initView() {
        mAvatarLayout = findViewById(R.id.fl_person_data_avatar);
        mAvatarView = findViewById(R.id.iv_person_data_avatar);
        mIdView = findViewById(R.id.sb_person_data_id);
        mNameView = findViewById(R.id.sb_person_data_name);
        mAddressView = findViewById(R.id.sb_person_data_address);
        setOnClickListener(mAvatarLayout, mAvatarView, mNameView, mAddressView);
    }

    @Override
    public void initData() {
        ArmsUtil.obtainAppComponent().imageLoader.loadImage(getActivity(),
                new ImageConfigImpl
                        .Builder()
                        .res(R.drawable.res_avatar_placeholder_ic)
                        .isCircle(true)
                        .isCenterCrop(true)
                        .imageView(mAvatarView)
                        .build());

        mIdView.setRightText("880634");
        mNameView.setRightText("Android 轮子哥");

        String address = mProvince + mCity + mArea;
        mAddressView.setRightText(address);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mAvatarLayout) {
            ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGESELECTACTIVITY)
                    .withInt("maxSelect", 1)
                    .navigation(this, getImageSelectRequestCode());
        } else if (view == mAvatarView) {
            if (mAvatarUrl != null) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add(mAvatarUrl.toString());
                ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGEPREVIEWACTIVITY)
                        .withStringArrayList("imageList", strings)
                        .withInt("imageIndex", 0)
                        .navigation();
            } else {
                // 选择头像
                onClick(mAvatarLayout);
            }
        } else if (view == mNameView) {
            new InputDialog.Builder(this)
                    // 标题可以不用填写
                    .setTitle(getString(R.string.res_personal_data_name_hint))
                    .setContent(mNameView.getRightText())
                    //.setHint(getString(R.string.personal_data_name_hint))
                    //.setConfirm("确定")
                    // 设置 null 表示不显示取消按钮
                    //.setCancel("取消")
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setListener((dialog, content) -> {
                        if (!mNameView.getRightText().equals(content)) {
                            mNameView.setRightText(content);
                        }
                    })
                    .show();
        } else if (view == mAddressView) {
//            new AddressDialog.Builder(this)
//                    //.setTitle("选择地区")
//                    // 设置默认省份
//                    .setProvince(mProvince)
//                    // 设置默认城市（必须要先设置默认省份）
//                    .setCity(mCity)
//                    // 不选择县级区域
//                    //.setIgnoreArea()
//                    .setListener((dialog, province, city, area) -> {
//                        String address = province + city + area;
//                        if (!mAddressView.getRightText().equals(address)) {
//                            mProvince = province;
//                            mCity = city;
//                            mArea = area;
//                            mAddressView.setRightText(address);
//                        }
//                    })
//                    .show();
        }
    }

    private int getImageSelectRequestCode() {
        return 100;
    }

    private int getImageCropRequestCode() {
        return 101;
    }

    /**
     * 裁剪图片
     */
    private void cropImageFile(File sourceFile) {
        ARouter.getInstance().build(RouterHub.PUBLIC_MEDIA_IMAGECROPACTIVITY)
                .withString("imagePath", sourceFile.toString())
                .withInt("cropRatioX", 1)
                .withInt("cropRatioY", 1)
                .navigation(this, getImageCropRequestCode());
    }

    /**
     * 上传裁剪后的图片
     */
    private void updateCropImage(File file, boolean deleteFile) {
        if (true) {
            if (file instanceof FileContentResolver) {
                mAvatarUrl = ((FileContentResolver) file).getContentUri();
            } else {
                mAvatarUrl = Uri.fromFile(file);
            }
            ArmsUtil.obtainAppComponent().imageLoader.loadImage(getActivity(),
                    new ImageConfigImpl
                            .Builder()
                            .res(mAvatarUrl)
                            .isCircle(true)
                            .isCenterCrop(true)
                            .imageView(mAvatarView)
                            .build());
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getImageSelectRequestCode() && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> list = data.getStringArrayListExtra("imageList");
            if (!list.isEmpty()) {
                cropImageFile(new File(list.get(0)));
            }
        } else if (requestCode == getImageCropRequestCode() && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getParcelableExtra("fileUri");
            String fileName = data.getStringExtra("fileName");
            File outputFile;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                outputFile = new FileContentResolver(getActivity(), uri, fileName);
            } else {
                try {
                    outputFile = new File(new URI(uri.toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    outputFile = new File(uri.toString());
                }
            }
            updateCropImage(outputFile, true);
        }
    }
}