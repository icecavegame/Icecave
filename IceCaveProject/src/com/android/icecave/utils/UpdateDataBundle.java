package com.android.icecave.utils;

public class UpdateDataBundle
{
	private int mNotificationId;
	private Object mData;
	
	public UpdateDataBundle (int notificationId, Object data) {
		mNotificationId = notificationId;
		mData = data;
	}
	
	public int getNotificationId() {
		return mNotificationId;
	}
	
	public Object getData() {
		return mData;
	}
}
