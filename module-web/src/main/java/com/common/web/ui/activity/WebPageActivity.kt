package com.common.web.ui.activity

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.core.base.BaseActivity
import com.common.export.arouter.RouterHub
import com.common.res.entity.SendEventEntity
import com.common.res.event.WEBFRAME_EVENT
import com.common.res.utils.eventBusPost
import com.common.web.R
import com.common.web.databinding.WebActivityWebpageBinding
import com.common.web.ui.fragment.WebPageFrgment

/**
 * desc :WebPageActivity
 * author：panyy
 * data：2020/10/27
 */

@Route(path = RouterHub.PUBLIC_WEBPAGEACTIVITY)
open class WebPageActivity : BaseActivity<WebActivityWebpageBinding>() {

    private var url: String? = null
    private var webPageFrgment: WebPageFrgment? = null
    private var event: SendEventEntity? = null

    override fun getLayoutId(): Int {
        return R.layout.web_activity_webpage
    }

    override fun initData() {
        url = this.intent.getStringExtra("url")
        event = intent.getSerializableExtra("event") as SendEventEntity?
        val transaction = supportFragmentManager.beginTransaction()
        webPageFrgment = WebPageFrgment.newInstance(url)
        transaction.add(R.id.fragment_container, webPageFrgment!!)
        transaction.commit()
    }

    fun getWebPageFrgment(): WebPageFrgment? {
        return webPageFrgment
    }

    override fun initView() {
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
        }
    }

    override fun onBackPressed() {
        if (webPageFrgment != null && webPageFrgment?.getJsApi() != null) {
            if (webPageFrgment?.getJsApi()?.isCanBack!!) {
                super.onBackPressed()
            } else {
                webPageFrgment?.getJsApi()?.delegateBackEventH5()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (event != null) {
            eventBusPost(WEBFRAME_EVENT, event)
        }
        super.onDestroy()
    }

    companion object {
        fun start(
            context: Context?,
            url: String?,
            title: String? = "",
            isHideToolbar: Boolean = false,
            event: SendEventEntity? = null
        ) {
            val intent = Intent(context, WebPageActivity::class.java).apply {
                putExtra("url", url)
                putExtra("title", title)
                putExtra("isHideToolbar", isHideToolbar)
                putExtra("event", event)
            }
            context?.startActivity(intent)
        }
    }
}