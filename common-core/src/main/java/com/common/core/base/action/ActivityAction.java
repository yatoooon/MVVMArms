package com.common.core.base.action;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/03/08
 * desc   : Activity 相关意图
 */
public interface ActivityAction {

    /**
     * 获取 Context 对象
     */
    Context getContext();

    /**
     * 获取 Activity 对象
     */
    default Activity getActivity() {
        Context context = getContext();
        do {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return null;
            }
        } while (context != null);
        return null;
    }

    /**
     * 跳转 Activity
     */
    default void startActivity(Intent intent) {
        if (!(getContext() instanceof Activity)) {
            // 如果当前的上下文不是 Activity，调用 startActivity 必须加入新任务栈的标记，否则会报错：android.util.AndroidRuntimeException
            // Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        getContext().startActivity(intent);
    }

    default void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    default Intent newIntent(Class<?> cls) {
        return new Intent(getContext(), cls);
    }

    default Intent newIntent(Class<?> cls, int flags) {
        Intent intent = newIntent(cls);
        intent.addFlags(flags);
        return intent;
    }

    default void startActivity(Class<?> cls) {
        startActivity(newIntent(cls));
    }

    default void startActivity(Class<?> cls, int flags) {
        startActivity(newIntent(cls, flags));
    }

    default void startActivity(Class<?> cls, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(newIntent(cls), optionsCompat);
    }

    default void startActivity(Class<?> cls, int flags, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(newIntent(cls, flags), optionsCompat);
    }

    default void startActivity(Intent intent, @Nullable ActivityOptionsCompat optionsCompat) {
        if (optionsCompat != null) {
            getContext().startActivity(intent, optionsCompat.toBundle());
        } else {
            startActivity(intent);
        }
    }

    default void startActivityFinish(Class<?> cls) {
        startActivity(cls);
        finish();
    }

    default void startActivityFinish(Class<?> cls, int flags) {
        startActivity(cls, flags);
        finish();
    }

    default void startActivityFinish(Class<?> cls, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(cls, optionsCompat);
        finish();
    }

    default void startActivityFinish(Class<?> cls, int flags, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(newIntent(cls, flags), optionsCompat);
    }

    default void startActivityFinish(Intent intent, @Nullable ActivityOptionsCompat optionsCompat) {
        startActivity(intent, optionsCompat);
    }


}