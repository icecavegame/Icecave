package com.android.icecave.mapLogic.collision;

import com.android.icecave.utils.Point;

import com.android.icecave.general.IFunction;

public class BaseCollisionInvoker<return_type> implements ICollisionInvoker<return_type> {
	
	IFunction<return_type> mFunction;
	
	public BaseCollisionInvoker(IFunction<return_type> function) {
		mFunction = function;
	}
	
	@Override
	public return_type onCollision(Point collisionPoint)
	{
		return mFunction.invoke(new Point(collisionPoint));
	}
}
