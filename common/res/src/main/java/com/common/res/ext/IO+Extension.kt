package com.common.res.ext

import java.io.Closeable

//io扩展
fun Closeable?.closeQuietly() {
    try {
        this?.close()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
    var closed = false
    try {
        return block(this)
    } catch (e: Exception) {
        e.printStackTrace()
        closed = true
        try {
            this?.close()
        } catch (closeException: Exception) {
            closeException.printStackTrace()
        }
        throw e
    } finally {
        if (!closed) {
            this?.close()
        }
    }
}