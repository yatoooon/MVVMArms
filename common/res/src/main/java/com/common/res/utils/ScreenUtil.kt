package com.common.res.utils

import android.app.Dialog
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

/**
 * Create by BASS
 * on 2021/12/21 13:49.
 */

/**
 * 隐藏底部导航栏,在点击屏幕后显示
 */
fun AppCompatActivity.hideNavigationBar1() {
    window.decorView.apply {
        systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
}

/**
 * 隐藏底部导航栏,始终隐藏,触摸屏幕时也不出现
 */
fun AppCompatActivity.hideNavigationBar2() {
    window.attributes.apply {
        systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = this
    }
}

/**
 * 隐藏底部导航栏,始终隐藏,触摸屏幕时也不出现
 */
fun DialogFragment.hideNavigationBar2() {
    dialog?.window?.attributes?.apply {
        systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        dialog!!.window!!.attributes = this
    }
}

/**
 * 隐藏底部导航栏,始终隐藏,触摸屏幕时也不出现
 */
fun Dialog.hideNavigationBar2() {
    window?.attributes?.apply {
        systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window!!.attributes = this
    }
}


/**
 * 隐藏状态栏
 */
fun AppCompatActivity.hideStatusBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

/**
 * 保持屏幕不息屏
 */
fun AppCompatActivity.keepScreenOn() {
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun AppCompatActivity.cancelScreenOn(){
    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}