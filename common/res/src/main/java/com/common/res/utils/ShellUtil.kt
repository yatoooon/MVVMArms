package com.common.res.utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException

val COMMAND_SU = "su"
val COMMAND_SH = "sh"
val COMMAND_EXIT = "exit\n"
val COMMAND_LINE_END = "\n"

/**
 * Check root permission
 *
 * @return
 */
fun checkRootPermission(): Boolean = execCommand("echo root", true) == 0


/**
 * Exec shell command
 *
 * @param command
 * *
 * @param isRoot
 * *
 * @return
 */
fun execCommand(command: String, isRoot: Boolean): Int = execCommand(arrayOf(command), isRoot)

/**
 * Exec shell command
 *
 * @param commands
 *
 * @param isRoot
 *
 * @return
 */
fun execCommand(commands: List<String>?, isRoot: Boolean): Int = execCommand(commands?.toTypedArray(), isRoot)

/**
 * Exec shell command
 *
 * @param commands
 *
 * @param isRoot
 *
 * @return
 */
fun execCommand(commands: Array<String>?, isRoot: Boolean): Int {
    var result = -1
    if (commands == null || commands.size == 0) {
        return result
    }

    var process: Process? = null
    val succBR: BufferedReader? = null
    val errorBR: BufferedReader? = null

    var dos: DataOutputStream? = null
    try {
        process = Runtime.getRuntime().exec(if (isRoot) COMMAND_SU else COMMAND_SH)
        dos = DataOutputStream(process!!.outputStream)
        for (command in commands) {
            if (command == null) {
                continue
            }
            dos.write(command.toByteArray())
            dos.writeBytes(COMMAND_LINE_END)
            dos.flush()
        }
        dos.writeBytes(COMMAND_EXIT)
        dos.flush()

        result = process.waitFor()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        dos?.close()
        succBR?.close()
        errorBR?.close()
        process?.destroy()
    }
    return result
}
