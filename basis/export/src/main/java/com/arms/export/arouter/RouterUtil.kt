package com.arms.export.arouter

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.arms.core.base.BaseApplication
import com.tencent.shadow.dynamic.host.EnterCallback
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication
import java.io.Serializable


/**
 * 解析params放到Postcard中
 */
private fun buildParams(postcard: Postcard, params: Map<String, Any?>?): Postcard {
    return postcard.apply {
        if (params != null && params.isNotEmpty())
            for ((k, v) in params)
                when (v) {
                    is String -> withString(k, v)
                    is Int -> withInt(k, v)
                    is Boolean -> withBoolean(k, v)
                    is Long -> withLong(k, v)
                    is Float -> withFloat(k, v)
                    is Double -> withDouble(k, v)
                    is ByteArray -> withByteArray(k, v)
                    is Bundle -> with(v)
                    is List<*>, is Map<*, *>, is Set<*> -> withObject(k, v)
                    is Parcelable -> withParcelable(k, v)
                    is Serializable -> withSerializable(k, v)
                    else -> withObject(k, v)
                }
    }
}

/**
 * 进阶使用方法
 * @path 路由路径
 * @params 所传参数
 * @activity 当前activity，配合requestCode或callback使用
 * @requestCode 当需要startActivityForResult时
 * @callback 使用NavigationCallback处理跳转结果（局部降级策略）
 * @group 一般不传，默认就是 path 的第一段相同
 * @uri 通过uri跳转时
 * @flag 当需要指定flag时Intent.FLAG_ACTIVITY_CLEAR_TOP
 * @enterAnim 页面进入动画
 * @exitAnim 页面退出动画
 * @compat ActivityOptionsCompat动画
 * @greenChannel 是否使用绿色通道(跳过所有的拦截器)
 */
val FROM_ID_START_ACTIVITY = 1001
val FROM_ID_CALL_SERVICE = 1002
@JvmOverloads
fun routerNavigation(
    path: String,
    params: Map<String, Any?>? = null,
    activity: Activity? = null,
    requestCode: Int = -1,
    callback: NavigationCallback? = null,
    group: String? = null,
    uri: Uri? = null,
    flag: Int? = null,
    enterAnim: Int? = null,
    exitAnim: Int? = null,
    compat: ActivityOptionsCompat? = null,
    greenChannel: Boolean = false,
) {

    if(path.contains("LoginActivity")){
        val pluginManager = InitApplication.getPluginManager()
        //调用activity
        pluginManager.enter(
            BaseApplication.getApp().applicationContext,
            FROM_ID_START_ACTIVITY.toLong(),
            Bundle(),
            object : EnterCallback {
                override fun onShowLoadingView(view: View) {
                }

                override fun onCloseLoadingView() {
                }

                override fun onEnterComplete() {
                }
            })
        //调用service
//        pluginManager.enter(
//            BaseApplication.getApp().applicationContext,
//            FROM_ID_CALL_SERVICE.toLong(),
//            null,
//            null
//        )
        return
    }
    if (path.isNullOrEmpty()) {
        buildParams(ARouter.getInstance().build(uri), params)
    } else {
        val postcard =
            if (group.isNullOrEmpty()) ARouter.getInstance().build(path)
            else ARouter.getInstance().build(path, group)
        buildParams(
            postcard.apply {
                if (flag != null)
                    postcard.withFlags(flag)
                if (enterAnim != null && exitAnim != null)//转场动画(常规方式)
                    postcard.withTransition(enterAnim, exitAnim)
                if (compat != null)// 转场动画(API16+)
                    postcard.withOptionsCompat(compat)
                if (greenChannel)//使用绿色通道(跳过所有的拦截器)
                    postcard.greenChannel()
            }, params
        )
    }.navigation(activity, requestCode, callback)
}