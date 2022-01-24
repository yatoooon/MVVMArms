package com.arms.export.arouter.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider
//web框架
/**
 * desc :web框架服务接口
 * author：panyy
 * date：2021/04/22
 */
interface IWebService : IProvider {

    /**
     * 打开web页面
     * @param context Context
     * @param url String?
     * @param title String?
     * @param isHideToolbar Boolean
     */
    fun start(context: Context, url: String?, title: String? = "", isHideToolbar: Boolean = false)

    /**
     * 创建WebPageFragment
     *
     * @param url
     */
    fun newWebPageFragment(url: String): Fragment

    /**
     * web框架回调通知H5
     *
     * @param data
     */
    fun onCallBack(data: String?)

}