package com.common.web.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.common.core.base.BaseFragment
import com.common.res.config.Constants.H5_URL
import com.common.res.entity.SendEventEntity
import com.common.res.event.WEBFRAME_EVENT
import com.common.res.utils.eventBusObserve
import com.common.web.R
import com.common.web.databinding.WebFragmentWebpageBinding
import com.common.web.jsapi.JsApi
import com.common.web.utils.CookieUtil
import com.common.web.view.BaseDWebView
import com.common.web.view.CommonIndicator
import com.common.web.view.DX5WebView
import com.common.web.view.LoadingView
import com.common.web.view.agentweb.AgentWeb
import com.common.web.view.agentweb.DefaultWebClient
import java.net.MalformedURLException
import java.net.URL
import java.util.*

/**
 * desc :WebPageFrgment
 * author：panyy
 * data：2018/3/14
 */
class WebPageFrgment : BaseFragment<WebFragmentWebpageBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.web_fragment_webpage
    }

    private var jsApi: JsApi? = null
    private var url: String? = null
    private var webView: DX5WebView? = null
    private var mAgentWeb: AgentWeb? = null

    override fun initData(savedInstanceState: Bundle?) {
        url = arguments?.getString("url")
        webView = DX5WebView(activity)
        val lp = FrameLayout.LayoutParams(-2, -2).apply { gravity = Gravity.CENTER }
        val commonIndicator = CommonIndicator(getActivity()).apply {
            addView(LoadingView(activity), lp)
            setBackgroundColor(Color.WHITE)
        }
        val preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.webviewContainer, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .setCustomIndicator(commonIndicator)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .setWebView(webView)
                .createAgentWeb()
                .ready()
        CookieUtil.syncCookie(url!!, activity)
        mAgentWeb = preAgentWeb.get()
        loadUrl()
        jsApi = JsApi()
        jsApi?.init(this, webView)
        webView?.addJavascriptObject(jsApi, null)
    }

    private fun loadUrl() {
        if (url?.startsWith("https://wx.tenpay.com")!!) {
            try {
                val urL = URL(H5_URL)
                val referer: MutableMap<String, String> = HashMap()
                referer["Referer"] = urL.protocol + "://" + urL.host
                mAgentWeb?.urlLoader?.loadUrl(url, referer)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        } else {
            mAgentWeb?.urlLoader?.loadUrl(url)
        }
    }

    fun getJsApi(): JsApi? {
        return jsApi
    }

    fun setJsApi(jsApi: JsApi?) {
        this.jsApi = jsApi
        if (webView != null && this.jsApi != null) {
            this.jsApi?.init(this, webView)
            webView?.removeJavascriptObject(null)
            webView?.addJavascriptObject(this.jsApi, null)
        }
    }

    fun getDWebView(): BaseDWebView? {
        return webView
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        webView?.callHandler("pauseAudio", arrayOf())
        webView?.callHandler("pauseVideo", arrayOf())
        super.onPause()
    }

    override fun onDestroy() {
        try {
            mAgentWeb?.webLifeCycle?.onDestroy()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            super.onDestroy()
        }
    }

    override fun initObserve() {
        eventBusObserve(WEBFRAME_EVENT, this) {
            var entity = it as SendEventEntity
            webView?.callHandler(entity?.event, arrayOf(entity?.args))
        }
    }

    companion object {
        fun newInstance(url: String?): WebPageFrgment {
            return WebPageFrgment().apply {
                arguments = Bundle().apply { putString("url", url) }
            }
        }
    }
}