package com.common.crash.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.common.core.base.BaseActivity;
import com.common.crash.R;
import com.common.res.ext.Bundle_ExtensionKt;
import com.common.res.router.RouterHub;
import com.common.res.router.RouterUtilKt;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/11/29
 * desc   : 重启应用
 */
@Route(path = RouterHub.PUBLIC_RESTART)
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
        restart(this);
        finish();
        toast(R.string.res_common_crash_hint);
    }

    public static void restart(Context context) {
//        Intent intent = null;
        if (true) {
            // 如果是未登录的情况下跳转到闪屏页
            RouterUtilKt.routerNavigation(RouterHub.PUBLIC_SPLASH);
        } else {
            // 如果是已登录的情况下跳转到首页
            RouterUtilKt.routerNavigation(RouterHub.PUBLIC_HOME_MAIN_ACTIVITY);
        }

//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(intent);
    }
}