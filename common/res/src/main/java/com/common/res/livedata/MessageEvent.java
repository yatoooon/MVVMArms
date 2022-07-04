package com.common.res.livedata;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 提供观察消息事件
 *
 */
public class MessageEvent extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, @NonNull final MessageObserver observer) {
        super.observe(owner, t -> {
            //过滤空消息
            if (TextUtils.isEmpty(t)) {
                return;
            }
            observer.onMessageChanged(t);
        });
    }

    public interface MessageObserver{
        void onMessageChanged(@NonNull String message);
    }
}
