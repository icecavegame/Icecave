package com.android.icecave.mapLogic;

public class MapLogicServiceProvider
{
	private static MapLogicServiceProvider mProvider = new MapLogicServiceProvider();
	
	public static MapLogicServiceProvider getInstance() {
		return mProvider;
	}
}
