package com.aispeech.medicalcheck.xdroid.tool.crash

import android.content.Context
import com.xdroid.spring.util.android.log.XDLog
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



class XDRuntimeLog {

    companion object {
        //奔溃日志
        lateinit var LOG_PATH_CRASH: String
        private const val TAG = "XDCarRuntimeLog"
        /**
         *   定时生成日志文件
         */
        @JvmStatic
        fun createLogFiles(context: Context) {
            LOG_PATH_CRASH =
                context.getExternalFilesDir(null)?.path + "/aispeech/xdroid/crash/"
            XDLog.e(TAG, "创建日志文件")
            try {
                val time = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                createLogDir()
                createLogFile("${LOG_PATH_CRASH}crash_${time}.log")
            } catch (e: Exception) {
            }
        }

        /**
         *   定时生成日志文件
         */
        @JvmStatic
        fun createLogFile(tag: String, type: String): Boolean {
            XDLog.e(TAG, "创建日志文件")
            val time = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            createLogDir()
            val isSuccess = when (type) {
                "crash" -> createLogFile("${LOG_PATH_CRASH}${tag}_${type}_${time}.log")
                else -> false
            }
            return isSuccess
        }

        /**
         *   保存在最新的文件中即可
         *   上传就使用非最新的文件
         */
        @JvmStatic
        @Synchronized
        fun store(type: LogType, logJson: String) {
            var fileDir: String = "";
            if (type == LogType.CRASH) fileDir = LOG_PATH_CRASH
            searchLatestFile(fileDir)?.let { f ->
                FileWriter(f, true).use {
                    if (f.length() > 0) {
                        it.append("\n")
                        it.append("\n")
                    }
                    it.append(logJson)
                }
            }
        }

        /**
         *   在文件夹中找到最新的日志文件
         */
        private fun searchLatestFile(dir: String): File? {
            var res: File? = null
            var lastModifyTime: Long = 0
            var latestFileName: String = ""
            File(dir).apply {
                listFiles()?.apply {
                    if (this.isNotEmpty()) latestFileName = this[0].name
                    forEach {
                        XDLog.e(
                            TAG,
                            "查找最新文件: ${it.name} -- 修改时间： ${
                                SimpleDateFormat("yyyyMMdd_HHmmss").format(it.lastModified())
                            }  "
                        )
                        if (it.lastModified() >= lastModifyTime) {
                            lastModifyTime = it.lastModified()
                            latestFileName = it.name
                        }
                    }
                }
                listFiles()?.forEach {
                    if (it.name == latestFileName) {
                        res = it
                    }
                }
            }
            XDLog.e(TAG, "searchLatestFile: 查询到的最新文件： ${res?.name}")
            return res
        }


        /**
         *   查询上传的文件
         */
        @JvmStatic
        fun filterPushToWebFiles(type: LogType): List<File> {
            return when (type) {
                LogType.CRASH -> searchHisFiles(LOG_PATH_CRASH)
            }
        }

        /**
         *   查询上传的文件
         */
        @JvmStatic
        fun queryAllLogs(type: LogType): List<File> {
            return when (type) {
                LogType.CRASH -> File(LOG_PATH_CRASH).listFiles().toList()
            }
        }

        /**
         *   ***************************************************************
         */

        /**
         *   查询非最新的文件
         */
        private fun searchHisFiles(dir: String): List<File> {
            var hisFiles: List<File> = ArrayList()
            var latestFileName = ""
            var lastModifyTime: Long = 0
            File(dir).apply {
                listFiles()?.apply {
                    forEach {
                        XDLog.e(TAG, "查找最新文件: ${it.name} -- 修改时间： ${it.lastModified()}  ")
                        if (it.lastModified() >= lastModifyTime) {
                            lastModifyTime = it.lastModified()
                            latestFileName = it.name
                        }
                    }
                }
                val files = listFiles()
                if (files != null) {
                    hisFiles = files.filter {
                        it.name != latestFileName
                    }
                }
            }
            return hisFiles
        }


        private fun createLogDir() {
            val crashDir = File(LOG_PATH_CRASH)
            if (!crashDir.exists()) {
                crashDir.mkdirs()
            }
        }

        private fun createLogFile(filePath: String): Boolean {
            var isSuccess = false
            try {
                val file = File(filePath)
                XDLog.e(TAG, "createLogFile ，日志文件：${filePath} 是否存在 ${file.exists()}")
                if (!file.exists()) {
                    isSuccess = file.createNewFile()
                    XDLog.e(TAG, "createLogFile: 创建文件 ${filePath}  是否成功：${isSuccess}")

                    //再做一次判断
                    val file2 = File(filePath)
                    XDLog.e(TAG, "createLogFile（第二次） ，日志文件：${filePath} 是否存在 ${file2.exists()}")
                    if (!file2.exists()) {
                        isSuccess = file2.createNewFile()
                        XDLog.e(TAG, "createLogFile: 创建文件 ${filePath}  是否成功：${isSuccess}")
                    }
                }
            } catch (e: Exception) {
            }
            return isSuccess
        }

        @JvmStatic
        public fun readLogsFromFile(path: String?): String {
            path ?: return "日志文件为空"
            var res = ""
            try {
                FileReader(File(path))?.use {
                    res = it.readText()
                }
            } catch (e: Exception) {
                res = "读取日志异常"
            }
            return res
        }

    }


}

/**
 *   日志类型
 */
enum class LogType {
    CRASH
}