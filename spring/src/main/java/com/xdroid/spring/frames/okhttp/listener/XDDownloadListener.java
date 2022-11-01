package com.xdroid.spring.frames.okhttp.listener;

import java.io.File;

/**
 *   下载监听 （监听下载进度）
 */
public interface XDDownloadListener extends XDDataListener<File> {
	public void onProgress(int progress);
}
