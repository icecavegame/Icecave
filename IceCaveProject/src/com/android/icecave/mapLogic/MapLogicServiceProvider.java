package com.android.icecave.mapLogic;

import com.android.icecave.mapLogic.collision.CollisionManager;
import com.android.icecave.mapLogic.tiles.validators.TileValidatorFactory;

public class MapLogicServiceProvider
{
	private static MapLogicServiceProvider mProvider = new MapLogicServiceProvider();
	private CollisionManager mCollisionManager = null;
	private TileValidatorFactory mTileValidatorFactory = null;

	/**
	 * Create a new instance of the MapLogicServerProvider.
	 */
	private MapLogicServiceProvider(){
		mTileValidatorFactory = new TileValidatorFactory();
	}
	
	/**
	 * Registers a collision manager to the service provider.
	 * @param collisionManager - Collision manager to register.
	 */
	public void registerCollisionManager(CollisionManager collisionManager)
	{
			mCollisionManager = collisionManager;
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
	
	/**
	 * Get the tile validtor factory object.
	 * @return Tile validator factory.
	 */
	public TileValidatorFactory getTileValidatorFactory(){
		return mTileValidatorFactory;
	}
}
