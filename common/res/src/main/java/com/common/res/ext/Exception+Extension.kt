package com.common.res.ext

import java.io.PrintWriter
import java.io.StringWriter

//exception 扩展
fun Throwable.getStackTraceText(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    printStackTrace(pw)
    return sw.toString()
}