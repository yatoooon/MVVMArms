package com.common.res.launcher

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.common.res.R

class LauncherActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_activity_launcher)
        val linearLayout = findViewById<LinearLayout>(R.id.root)
        packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_ACTIVITIES
        ).activities.forEach { activity ->
            if (activity.name.startsWith("com.common.") && !activity.name.startsWith("com.common.core") && !activity.name.startsWith(
                    "com.common.res"
                )
            ) {
                val clazz = Class.forName(activity.name)
                val button = Button(this).apply {
                    isAllCaps = false
                    text = clazz.simpleName
                    background = Color.WHITE.toDrawable()
                    setPadding(20,20,20,20)
                    setOnClickListener {
                        startActivity(Intent(this@LauncherActivity, clazz))
                    }
                }
                linearLayout.addView(button)
            }
        }
    }


}