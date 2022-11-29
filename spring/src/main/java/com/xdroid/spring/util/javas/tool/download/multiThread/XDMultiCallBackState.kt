package com.xdroid.spring.util.javas.tool.download.multiThread

/**
 *   多线程下载的各个回调对象
 */

data class XDMultiCallBackState(
    var threadIndex: Int,//下载的线程index
    var progress: Long = 0,  //下载的进度
    var isComplete:Boolean = false,
    var contentLength: Long = 0//下载的总长度
)