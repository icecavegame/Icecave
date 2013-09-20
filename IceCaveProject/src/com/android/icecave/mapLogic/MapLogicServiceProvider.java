package com.android.icecave.mapLogic;

import com.android.icecave.mapLogic.collision.CollisionManager;

public class MapLogicServiceProvider
{
	private static MapLogicServiceProvider mProvider = new MapLogicServiceProvider();
	private CollisionManager mCollisionManager = null;

	/**
	 * Registers a collision manager to the service provider.
	 * @param collisionManager - Collision manager to register.
	 */
	public void registerCollisionManager(CollisionManager collisionManager)
	{
		if(mCollisionManager == null){			
			mCollisionManager = collisionManager;
		}
	}
	
	/**
	 * Get the static instance of the MapLogicServiceProvider.
	 * @return static instance of the service provider.
	 */
	public static MapLogicServiceProvider getInstance()
	{
		return mProvider;
	}

	/**
	 * Get the collision manager.
	 * @return Active collision manager.
	 */
	public CollisionManager getCollisionManager()
	{
		return mCollisionManager;
	}
}
