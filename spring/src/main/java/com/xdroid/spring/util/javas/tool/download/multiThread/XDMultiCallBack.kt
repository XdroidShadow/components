package com.xdroid.spring.util.javas.tool.download.multiThread

import com.xdroid.spring.util.javas.tool.download.XDSingleCallBack

class XDMultiCallBack {
    var dataState: HashMap<Int, XDMultiCallBackState>

    constructor(threadCount: Int) {
        dataState = HashMap()
        for (i in 0..threadCount) {
            dataState[i] = XDMultiCallBackState(i)
        }
    }


    fun modify(threadIndex: Int,progress: Long, isComplete: Boolean, contentLength: Long) {
        var state: XDMultiCallBackState? = dataState[threadIndex]
        state?.progress = progress
        state?.isComplete
    }

    fun onSuccess(info: String?, pos: Long, contentLength: Long) {
        TODO("Not yet implemented")
    }

    fun onDownloading(pos: Long, contentLength: Long) {
        TODO("Not yet implemented")
    }


}