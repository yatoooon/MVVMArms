package com.common.res.utils

import android.content.Context
import android.os.Environment
import android.os.Looper
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.tencent.bugly.crashreport.CrashReport
import java.io.File
import java.math.BigDecimal

/**
 * desc :Glide缓存工具类
 * author：panyy
 * date：2021/04/22
 */
object CacheUtil {
    /**
     * 清除图片磁盘缓存
     */
    fun clearImageDiskCache(context: Context?) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Thread { Glide.get(context!!).clearDiskCache() }.start()
            } else {
                Glide.get(context!!).clearDiskCache()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 清除图片内存缓存
     */
    fun clearImageMemoryCache(context: Context?) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context!!).clearMemory()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 清除图片所有缓存
     */
    fun clearImageAllCache(context: Context?) {
        clearImageDiskCache(context)
        clearImageMemoryCache(context)
        val cacheDir = Glide.getPhotoCacheDir(context!!)!!.absolutePath
        deleteFolderFile(cacheDir, true)
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    fun deleteFolderFile(filePath: String?, deleteThisPath: Boolean) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                val file = File(filePath)
                if (file.isDirectory) {
                    val files = file.listFiles()
                    for (file1 in files) {
                        deleteFolderFile(file1.absolutePath, true)
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory) {
                        file.delete()
                    } else {
                        if (file.listFiles().size == 0) {
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /**
     * 获取缓存大小
     */
    fun getTotalCacheSize(context: Context): String? {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    /**
     * 清除缓存
     */
    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.externalCacheDir)
        }
    }

    /**
     * 删除文件夹
     */
    private fun deleteDir(dir: File?): Boolean {
        if (dir == null) {
            return false
        }
        if (!dir.isDirectory) {
            return dir.delete()
        }
        val children = dir.list() ?: return false
        for (child in children) {
            deleteDir(File(dir, child))
        }
        return false
    }

    // 获取文件大小
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    private fun getFolderSize(file: File?): Long {
        var size: Long = 0
        try {
            val list = file!!.listFiles() ?: return 0
            for (temp in list) {
                // 如果下面还有文件
                size = if (temp.isDirectory) {
                    size + getFolderSize(temp)
                } else {
                    size + temp.length()
                }
            }
        } catch (e: java.lang.Exception) {
            CrashReport.postCatchedException(e)
        }
        return size
    }

    /**
     * 格式化单位
     */
    fun getFormatSize(size: Double): String? {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            return BigDecimal(kiloByte).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            return BigDecimal(megaByte).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M"
        }
        val teraBytes = gigaByte / 1024
        return if (teraBytes < 1) {
            BigDecimal(gigaByte).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        } else BigDecimal(teraBytes).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }
}