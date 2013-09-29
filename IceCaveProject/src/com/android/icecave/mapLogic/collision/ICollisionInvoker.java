package com.android.icecave.mapLogic.collision;

import android.graphics.Point;

public interface ICollisionInvoker<return_type>
{
	/**
	 * This function handles the collision of the player with a point.
	 * @param collisionPoint - The point of collision.
	 * @return result.
	 */
	public return_type onCollision(Point collisionPoint);
}
