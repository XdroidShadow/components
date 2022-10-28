package com.xd.spring.httpRequest.listener;

import com.xd.spring.httpRequest.exception.XDHttpErrType;

/**
 *   数据回调类
 */
public interface MKDataListener<T> {

	/**
	 * 请求成功回调事件处理
	 */
	public void onSuccess(T res);

	/**
	 * 请求失败回调事件处理
	 */
	public void onFailure(XDHttpErrType err);

}
