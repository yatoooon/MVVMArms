package com.common.web.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.common.core.base.BaseActivity
import com.common.res.entity.SendEventEntity
import com.common.res.event.WEBFRAME_EVENT
import com.common.res.utils.bindViewClickListener
import com.common.res.utils.eventBusPost
import com.common.web.R
import com.common.web.databinding.WebActivityWebpageBinding
import com.common.web.ui.fragment.WebPageFrgment

/**
 * desc :WebPageActivity
 * author：panyy
 * data：2020/10/27
 */
open class WebPageActivity : BaseActivity<WebActivityWebpageBinding>() {

    private var url: String? = null
    private var isHideToolbar = false
    private var title: String? = null
    private var webPageFrgment: WebPageFrgment? = null
    private var event: SendEventEntity? = null

    override fun getLayoutId(): Int {
        return R.layout.web_activity_webpage
    }

    override fun initData(savedInstanceState: Bundle?) {
        url = this.intent.getStringExtra("url")
        title = this.intent.getStringExtra("title")
        isHideToolbar = this.intent.getBooleanExtra("isHideToolbar", false)
        event = intent.getSerializableExtra("event") as SendEventEntity?
        if (isHideToolbar) {
            binding.layoutToolbar.webframeToolbar.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(title)) {
            binding.layoutToolbar.tvWebframeTitle.visibility = View.VISIBLE
            binding.layoutToolbar.tvWebframeTitle.text = title
        }
        val transaction = supportFragmentManager.beginTransaction()
        webPageFrgment = WebPageFrgment.newInstance(url)
        transaction.add(R.id.fragment_container, webPageFrgment!!)
        transaction.commit()
    }

    fun getWebPageFrgment(): WebPageFrgment? {
        return webPageFrgment
    }

    override fun initViewClick() {
        bindViewClickListener(binding.layoutToolbar.ivWebframeBack) {
            onBackPressed()
        }
    }

    fun setStyle(titleColor: Int, toolbarColor: Int) {
        binding.layoutToolbar.tvWebframeTitle.setTextColor(resources.getColor(titleColor))
        binding.layoutToolbar.tvWebframeRightText.setTextColor(resources.getColor(titleColor))
        binding.layoutToolbar.webframeToolbar.setBackgroundColor(resources.getColor(toolbarColor))
        if (titleColor == R.color.web_white) {
            binding.layoutToolbar.ivWebframeBack.setImageResource(R.drawable.web_ic_webframe_back_white)
            binding.layoutToolbar.ivWebframeMore.setImageResource(R.drawable.web_ic_add_white_24dp)
        } else {
            binding.layoutToolbar.ivWebframeBack.setImageResource(R.drawable.web_ic_webframe_back_black)
            binding.layoutToolbar.ivWebframeMore.setImageResource(R.drawable.web_ic_add_black_24dp)
        }
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
        fun start(context: Context?, url: String?, title: String? = "", isHideToolbar: Boolean = false, event: SendEventEntity? = null) {
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