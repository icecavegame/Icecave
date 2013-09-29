package com.android.icecave.general;

import android.graphics.Point;

public interface IFunction<return_type> {
	/**
	 * Invoke the collision function.
	 * @param collisionPoint - Point of the tile the player collisioned with.
	 * @return result.
	 */
	public return_type invoke(Point collisionPoint);
}
