package com.basis.common.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.coder.zzq.smartshow.dialog.LoadingDialog

fun showLoadingDialog(activity: Activity? = null, fragment: Fragment? = null, message: String? = ""): LoadingDialog? {
    return LoadingDialog().middle().message(message).apply {
        if (activity != null && !activity.isFinishing) {
            showInActivity(activity)
        }
        if (fragment != null) {
            showInFragment(fragment)
        }
    }
}

fun dismissLoadingDialog(dialog: LoadingDialog?) {
    dialog?.dismiss()
}





