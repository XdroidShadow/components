package com.aispeech.medicalcheck.xdroid.tool.crash

import com.google.gson.Gson
import java.text.SimpleDateFormat

/**
 *   异常对象
 */
class XDCrashBean constructor(
    var crashReason: String,
) {
    //使用时间格式：2021-09-04 07:59:40
    var time: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())

    override fun toString(): String {
        return Gson().toJson(this)
    }
}

