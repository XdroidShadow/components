package com.xdroid.spring.util.javas.tool.download

/**
 *   下载对象
 */
 class XDDownloadBean {

    var urlPath: String//网络文件路径
    var destinationPath: String//下载路径
    var channel: XDDownloadChannel//下载渠道
    var shouldBreakPointDownload: Boolean//是否需要断点续传
    var breakpoint: String//断点的位置
    var endPoint: String//结束位置
    var threadCount: Int = 1//多线程下载的数量


    @JvmOverloads
    constructor(
        urlPath: String,
        destinationPath: String,
        channel: XDDownloadChannel = XDDownloadChannel.OKHTTP,
        breakpoint: String = "0",//0表示开头
        shouldBreakPointDownload: Boolean = true,
        endPoint: String = "",// ""空白表示结尾
        threadCount: Int = 1
    ) {
        this.urlPath = urlPath
        this.destinationPath = destinationPath
        this.channel = channel
        this.shouldBreakPointDownload = shouldBreakPointDownload
        this.breakpoint = breakpoint
        this.endPoint = endPoint
        this.threadCount = threadCount
    }
}
