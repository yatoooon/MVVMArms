package com.common.core.crash;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.common.core.R;
import com.common.core.base.BaseActivity;
import com.common.core.base.BaseApplication;
import com.common.res.config.AppConfig;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/11/29
 * desc   : 重启应用
 */
public final class RestartActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, RestartActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
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
        restart();
        finish();
        toast(R.string.res_common_crash_hint);
    }

    public static void restart() {
        if (AppConfig.INSTANCE.getLogin()) {
            // 如果是未登录的情况下跳转到闪屏页
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(BaseApplication.getApp(), "com.common.login.mvvm.activity.LoginActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.getApp().startActivity(intent);
        } else {
            // 如果是已登录的情况下跳转到首页
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(BaseApplication.getApp(), "com.common.login.mvvm.activity.LoginActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.getApp().startActivity(intent);
        }
    }
}