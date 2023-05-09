package com.common.export.arouter

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter

//路由工具类
fun Activity.routerNavigation(path: String? = null, bundle: Bundle? = null): Any? {
    return ARouter.getInstance().build(path).with(bundle).withTransition(0, 0).navigation(this)
}

fun Fragment.routerNavigation(path: String? = null, bundle: Bundle? = null): Any? {
    return ARouter.getInstance().build(path).with(bundle).withTransition(0, 0).navigation(requireActivity())
}

fun routerNavigation(path: String? = null, bundle: Bundle? = null): Any? {
    return ARouter.getInstance().build(path).with(bundle).withTransition(0, 0).navigation()
}


inline fun <reified T : IProvider?> routerProvide(path: String?): T? {
    if (TextUtils.isEmpty(path)) {
        return null
    }
    var provider: IProvider? = null
    try {
        provider = ARouter.getInstance()
            .build(path)
            .navigation() as IProvider
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return provider as T?
}

inline fun <reified T : IProvider?> routerProvide(): T? {
    var provider: IProvider? = null
    try {
        provider = ARouter.getInstance().navigation(T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return provider as T?
}