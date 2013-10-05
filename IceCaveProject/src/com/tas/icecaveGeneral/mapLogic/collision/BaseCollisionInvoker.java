package com.tas.icecaveGeneral.mapLogic.collision;


import com.tas.icecave.general.IFunction;
import com.tas.icecaveGeneral.utils.Point;

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
