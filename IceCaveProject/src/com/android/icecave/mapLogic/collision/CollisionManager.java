package com.android.icecave.mapLogic.collision;

import java.lang.reflect.Type;
import java.util.HashMap;

public abstract class CollisionManager
{
	@SuppressWarnings("rawtypes")
	protected HashMap<Type, ICollisionInvoker> mCollisionInvokers = 
			new HashMap<Type, ICollisionInvoker>();
	
	/**
	 * Handles a collision with an object.
	 * @param ICollisionable collisionable - Object collisioned with.
	 */
	public abstract void handleCollision(ICollisionable collisionable);
}
