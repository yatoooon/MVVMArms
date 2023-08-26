package com.common.personal.mvvm.activity;

import static com.common.export.arouter.RouterUtilKt.routerNavigation;

import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.common.core.base.BaseActivity;
import com.common.export.arouter.RouterHub;
import com.common.personal.R;
import com.common.res.aop.SingleClick;
import com.common.res.dialog.BaseDialog;
import com.common.res.dialog.MenuDialog;
import com.common.res.dialog.SafeDialog;
import com.common.res.dialog.UpdateDialog;
import com.common.res.ext.Context_ExtensionKt;
import com.common.res.layout.SettingBar;
import com.common.res.utils.AppManager;
import com.common.res.utils.ArmsUtil;
import com.common.res.utils.CacheUtil;
import com.common.res.view.SwitchButton;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/01
 * desc   : 设置界面
 */
@Route(path = RouterHub.PUBLIC_PERSONAL_SETTINGACTIVITY)
public final class SettingActivity extends BaseActivity
        implements SwitchButton.OnCheckedChangeListener {

    private SettingBar mLanguageView;
    private SettingBar mPhoneView;
    private SettingBar mPasswordView;
    private SettingBar mCleanCacheView;
    private SwitchButton mAutoSwitchView;

    @Override
    public int getLayoutId() {
        return R.layout.personal_setting_activity;
    }

    @Override
    public void initView() {
        mLanguageView = findViewById(R.id.sb_setting_language);
        mPhoneView = findViewById(R.id.sb_setting_phone);
        mPasswordView = findViewById(R.id.sb_setting_password);
        mCleanCacheView = findViewById(R.id.sb_setting_cache);
        mAutoSwitchView = findViewById(R.id.sb_setting_switch);

        // 设置切换按钮的监听
        mAutoSwitchView.setOnCheckedChangeListener(this);

        setOnClickListener(R.id.sb_setting_language, R.id.sb_setting_update, R.id.sb_setting_phone,
                R.id.sb_setting_password, R.id.sb_setting_agreement, R.id.sb_setting_about,
                R.id.sb_setting_cache, R.id.sb_setting_auto, R.id.sb_setting_exit);
    }

    @Override
    public void initData() {
        // 获取应用缓存大小
        mCleanCacheView.setRightText(CacheUtil.INSTANCE.getTotalCacheSize(this));

        mLanguageView.setRightText("简体中文");
        mPhoneView.setRightText("181****1413");
        mPasswordView.setRightText("密码强度较低");
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.sb_setting_language) {

            // 底部选择框
            new MenuDialog.Builder(this)
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setList(R.string.res_setting_language_simple, R.string.res_setting_language_complex)
                    .setListener((MenuDialog.OnListener<String>) (dialog, position, string) -> {
                        mLanguageView.setRightText(string);
                        ARouter.getInstance().build(RouterHub.PUBLIC_WEBPAGEACTIVITY)
                                .withString("url", "https://github.com/getActivity/MultiLanguages")
                                .navigation();
                    })
                    .setGravity(Gravity.BOTTOM)
                    .setAnimStyle(BaseDialog.ANIM_BOTTOM)
                    .show();

        } else if (viewId == R.id.sb_setting_update) {

            // 本地的版本码和服务器的进行比较
            if (20 > Context_ExtensionKt.getAppVersionCode(this)) {
                new UpdateDialog.Builder(this)
                        .setVersionName("2.0")
                        .setForceUpdate(false)
                        .setUpdateLog("修复Bug\n优化用户体验")
                        .setDownloadUrl("https://down.qq.com/qqweb/QQ_1/android_apk/Android_8.5.0.5025_537066738.apk")
                        .setFileMd5("560017dc94e8f9b65f4ca997c7feb326")
                        .show();
            } else {
                toast(R.string.res_update_no_update);
            }

        } else if (viewId == R.id.sb_setting_phone) {

            new SafeDialog.Builder(this)
                    .setListener((dialog, phone, code) -> ARouter.getInstance().build(RouterHub.PUBLIC_LOGIN_PHONERESETACTIVITY).withString("code",code).navigation())
                    .show();

        } else if (viewId == R.id.sb_setting_password) {

            new SafeDialog.Builder(this)
                    .setListener((dialog, phone, code) -> ARouter.getInstance().build(RouterHub.PUBLIC_LOGIN_PASSWORDRESETACTIVITY).withString("code",code).navigation())
                    .show();

        } else if (viewId == R.id.sb_setting_agreement) {
            ARouter.getInstance().build(RouterHub.PUBLIC_WEBPAGEACTIVITY)
                    .withString("url", "https://www.baidu.com/")
                    .withString("title", "百度一下")
                    .navigation();
        } else if (viewId == R.id.sb_setting_about) {


        } else if (viewId == R.id.sb_setting_auto) {

            // 自动登录
            mAutoSwitchView.setChecked(!mAutoSwitchView.isChecked());

        } else if (viewId == R.id.sb_setting_cache) {

            // 清除内存缓存（必须在主线程）
            CacheUtil.INSTANCE.clearImageMemoryCache(this);
            ArmsUtil.obtainAppComponent().executorService.execute(() -> {
                CacheUtil.INSTANCE.clearAllCache(this);
                // 清除本地缓存（必须在子线程）
                CacheUtil.INSTANCE.clearImageDiskCache(this);
                post(() -> {
                    // 重新获取应用缓存大小
                    mCleanCacheView.setRightText(CacheUtil.INSTANCE.getTotalCacheSize(getActivity()));
                });
            });

        } else if (viewId == R.id.sb_setting_exit) {

            if (true) {
                routerNavigation(RouterHub.PUBLIC_LOGIN_LOGINACTIVITY,null);
                // 进行内存优化，销毁除登录页之外的所有界面
                try {
                    AppManager.getAppManager().killAll(Class.forName("com.common.login.ui.activity.LoginActivity"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean checked) {
        toast(checked);
    }
}