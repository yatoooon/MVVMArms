package com.common.res.utils

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner


object ScanUtil : DefaultLifecycleObserver {

    /**
     * 扫码枪或者仪器，其实是基于键盘输入的，那事件会先分发到获取焦点的界面中的 dispatchKeyEvent(KeyEvent event)
     * 但是没办法统一为应用设置监听，只能在每个Activity作监听。这里可以基类（BaseScannerActivity）处理扫码器的输入事件
     * 也可以通过AccessibilityService（不懂的可以百度，我也没深入了解） 的 onKeyEvent(KeyEvent event) 事件去处理，前提是辅助功能要手动开启。
     * KeyEvent继承了InputEvent，可以通过getDevice获取设备，来加准判断
     */
    fun intercept(lifecycleOwner: LifecycleOwner, event: KeyEvent?, listener: (code: String) -> Unit): Boolean {
        Log.e("--------------keycode", event?.keyCode.toString())
        if (event?.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            val keyCode = event.keyCode
            //因为是条码，按下的数值在0-9之间
            if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                scanResult.append(keyCode - KeyEvent.KEYCODE_0)
                Log.e("--------------识别中的付款码", scanResult.toString() + "")
            }
            if (scanListener == null) {
                scanListener = listener
                lifecycleOwner.lifecycle.addObserver(this)  //使用lifecycle处理与生命周期有关的问题
            }
            handler.removeCallbacks(mScanningFishedRunnable)
            handler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY)

        }
        return false
    }

    //返回扫描结果
    const val MESSAGE_DELAY: Long = 100
    var scanResult = StringBuilder()
    var handler = Handler(Looper.getMainLooper())
    var scanListener: ((code: String) -> Unit)? = null
    val mScanningFishedRunnable = Runnable { performScanSuccess() }
    private fun performScanSuccess() {
        val barcode: String = scanResult.toString()
        if (TextUtils.isEmpty(barcode)) {
            return
        }
        scanListener?.invoke(barcode)
        scanResult.setLength(0)
        scanListener = null
    }


    override fun onDestroy(owner: LifecycleOwner) {
        handler.removeCallbacks(mScanningFishedRunnable)
        if (scanListener != null) {
            scanListener = null
        }
        owner.lifecycle.removeObserver(this)
    }


}
