package com.common.plugin

import org.gradle.api.Project
import org.gradle.process.ExecResult
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Project.gitCommitHash(): String {
    val result = ByteArrayOutputStream()
    val execResult: ExecResult = project.exec {
        commandLine("git", "rev-parse", "HEAD")
        standardOutput = result
    }
    if (execResult.exitValue == 0) {
        return result.toString().trim()
    } else {
        return "unknown"
    }
}


fun buildTime(): String {
    return SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date())
}
