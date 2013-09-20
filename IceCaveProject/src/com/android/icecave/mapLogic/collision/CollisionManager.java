package com.android.icecave.mapLogic.collision;

import java.lang.reflect.Type;
import java.util.Dictionary;

public abstract class CollisionManager
{
	protected Dictionary<Type, ICollisionInvoker> mCollisionInvokers;
	
	static {}
	
	protected abstract void HandleCollision();
}
