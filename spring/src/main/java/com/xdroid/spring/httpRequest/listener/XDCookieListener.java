package com.xdroid.spring.httpRequest.listener;

import java.util.ArrayList;

/**
 *   cookie回调
 */
public interface XDCookieListener extends XDDataListener
{
	public void onCookie(ArrayList<String> cookieStrLists);
}
