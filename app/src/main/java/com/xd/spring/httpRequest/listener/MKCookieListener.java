package com.xd.spring.httpRequest.listener;

import java.util.ArrayList;

/**
 *   cookie回调
 */
public interface MKCookieListener extends MKDataListener
{
	public void onCookie(ArrayList<String> cookieStrLists);
}
