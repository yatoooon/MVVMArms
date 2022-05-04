package com.arms.common.launcher

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.FragmentActivity
import com.arms.common.R
import org.jetbrains.anko.padding

class LauncherActivity : FragmentActivity() {


    val activitys = arrayListOf(
        "LeakLauncherActivity",
        "WXEntryActivity",
        "CrashActivity",
        "RestartActivity",
        "AuthActivity",
        "AssistActivity",
        "PictureSelectorActivity",
        "PictureSelectorWeChatStyleActivity",
        "PictureSelectorCameraEmptyActivity",
        "PictureCustomCameraActivity",
        "PicturePreviewActivity",
        "PictureSelectorPreviewWeChatStyleActivity",
        "PictureVideoPlayActivity",
        "PictureExternalPreviewActivity",
        "PicturePlayAudioActivity",
        "UCropActivity",
        "PictureMultiCuttingActivity",
        "LeakActivity",
        "RequestStoragePermissionActivity",
        "UpdateDialogActivity",
        "PluginSingleTask1ProxyActivity",
        "PluginSingleInstance1ProxyActivity",
        "PluginDefaultProxyActivity"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_activity_launcher)
        val linearLayout = findViewById<LinearLayout>(R.id.root)
        packageManager.getPackageInfo(
            packageName, PackageManager.GET_ACTIVITIES
        ).activities.forEach { activity ->
            if (!outActivityString(activity)) {
                if (activity.name == this::class.java.name
                ) {
                    return@forEach
                }
                val clazz = Class.forName(activity.name)
                val button = Button(this).apply {
                    isAllCaps = false
                    text = clazz.simpleName
                    background = Color.WHITE.toDrawable()
                    padding = 20
                    setOnClickListener {
                        startActivity(Intent(this@LauncherActivity, clazz))
                    }
                }
                linearLayout.addView(button)
            }
        }
    }

    private fun outActivityString(activity: ActivityInfo): Boolean {
        activitys.forEach {
            if (activity.name.contains(it)) return true
        }
        return false
    }
}