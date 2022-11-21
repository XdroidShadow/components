package com.xdroid.spring.util.javas.tool.download

/**
 *   下载对象
 */
data class XDDownloadBean(
    var urlPath: String,//网络文件路径
    var destinationPath: String,//下载路径
    var channel: XDDownloadChannel = XDDownloadChannel.OKHTTP,//下载渠道
    var isShouldBreakpointDownload: Boolean = true,//是否需要断点续传
    var breakpoint: Long//断点的位置
)

