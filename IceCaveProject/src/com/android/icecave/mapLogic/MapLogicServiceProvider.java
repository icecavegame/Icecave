package com.android.icecave.mapLogic;

import com.android.icecave.mapLogic.collision.CollisionManager;

public class MapLogicServiceProvider
{
	private static MapLogicServiceProvider mProvider = new MapLogicServiceProvider();
	private CollisionManager mCollisionManager;

	public static MapLogicServiceProvider getInstance()
	{
		return mProvider;
	}

	public CollisionManager getCollisionManager()
	{
		return mCollisionManager;
	}
}
