package com.aispeech.medicalcheck.xdroid.tool.crash

import android.content.Context
import android.os.Environment
import android.os.Handler
import com.aispeech.medicalcheck.xdroid.tool.crash.XDRuntimeLog.Companion.createLogFiles
import com.xdroid.spring.util.androids.tool.XDLog
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "XDCrashHandler"

/**
 *   全局异常处理
 *   异常日志地址
 *   /storage/emulated/0/Android/data/com.xd.spring/files/aispeech/xdroid/crash/crash_20221101_135734.log
 */
data class XDCrashHandler(var mContext: Context) : UncaughtExceptionHandler {
    private lateinit var mDefaultHandler: UncaughtExceptionHandler

    fun init() {
        createLogFiles(mContext)

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
//        testCrash()
    }

    private fun testCrash() {
        Handler().postDelayed({
//            throw RuntimeException("主动抛出一个运行异常，测试")
        }, 10000)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(t: Thread, e: Throwable) {
        if (!handleExample(e) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理 目的是判断异常是否已经被处理
            mDefaultHandler!!.uncaughtException(t, e)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: Exception) {
                e.printStackTrace()
                XDLog.e(TAG, "uncaughtException：${e.message}")
            }
            restartApp()
        }
    }

    /**
     * 自定义错误处理,收集错误信息 将异常信息保存 发送错误报告等操作均在此完成.
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleExample(ex: Throwable?): Boolean {
        // 如果已经处理过这个Exception,则让系统处理器进行后续关闭处理
        if (ex == null) return false
        XDLog.e(TAG, "程序出现异常-handleExample：${ex.message}")
        saveCrashLog(ex)
        return true
    }

    fun restartApp() {
        /** 关闭App 与下面的restartApp重启App保留一个就行 看你需求  */
        // 如果不关闭程序,会导致程序无法启动,需要完全结束进程才能重新启动
        // android.os.Process.killProcess(android.os.Process.myPid());
        // System.exit(1);
        // 重启应用
//        mContext!!.startActivity(mContext!!.packageManager.getLaunchIntentForPackage(mContext!!.packageName))
        //干掉当前的程序
//        Process.killProcess(Process.myPid())
    }

    private fun saveCrashLog(exception: Throwable) {
        val timeStr = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        //获取错误原因
        val writer: Writer = StringWriter()
        var crashReason = StringBuilder()
        writer.append(timeStr + "\n")
        PrintWriter(writer).use {
            exception.printStackTrace(it)
            crashReason.append(writer.toString())
            var exCause = exception.cause
            while (exCause != null) {
                exCause.printStackTrace(it)
                crashReason.append(writer.toString())
                exCause = exCause.cause
            }
        }

        //使用统一的日志上传工具
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//            XDCrashLogManager.store(crashReason.toString())
            val bean = XDCrashBean(crashReason.toString())
            XDRuntimeLog.store(LogType.CRASH, bean.toString())
        }

    }


}


