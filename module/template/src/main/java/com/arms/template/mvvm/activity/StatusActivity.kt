package com.arms.template.mvvm.activity

import androidx.core.content.ContextCompat
import com.arms.common.action.StatusAction
import com.arms.common.dialog.BaseDialog
import com.arms.common.dialog.MenuDialog
import com.arms.core.base.BaseActivity
import com.arms.res.layout.StatusLayout
import com.arms.res.layout.StatusLayout.OnRetryListener
import com.arms.template.R
import com.arms.template.databinding.TemplateStatusActivityBinding

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/04/17
 * desc   : 加载使用案例
 */
class StatusActivity : BaseActivity<TemplateStatusActivityBinding>(),
    StatusAction {
    private var mStatusLayout: StatusLayout? = null
    override fun getLayoutId(): Int {
        return R.layout.template_status_activity
    }

    override fun initView() {
        mStatusLayout = findViewById(R.id.hl_status_hint)
    }

    override fun initData() {
        MenuDialog.Builder(this) //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
            .setList("加载中", "请求错误", "空数据提示", "自定义提示")
            .setListener { dialog: BaseDialog?, position: Int, `object`: Any? ->
                when (position) {
                    0 -> {
                        showLoading()
                        postDelayed({ showComplete() }, 2500)
                    }
                    1 -> showError(object : OnRetryListener {
                        override fun onRetry(layout: StatusLayout?) {
                            showLoading()
                            postDelayed({ showEmpty() }, 2500)
                        }
                    })
                    2 -> showEmpty()
                    3 -> showLayout(
                        ContextCompat.getDrawable(
                            activity,
                            R.drawable.res_status_order_ic
                        ), "暂无订单", null
                    )
                    else -> {
                    }
                }
            }
            .show()
    }

    override fun getStatusLayout(): StatusLayout {
        return mStatusLayout!!
    }
}