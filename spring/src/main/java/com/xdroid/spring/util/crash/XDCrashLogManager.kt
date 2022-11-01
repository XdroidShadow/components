package com.aispeech.medicalcheck.xdroid.tool.crash

class XDCrashLogManager {
    companion object {

        @JvmStatic
        fun store(crashReason: String) {
            val bean = XDCrashInfo(crashReason)
            XDRuntimeLog.store(LogType.CRASH, bean.toString())
        }
    }
}