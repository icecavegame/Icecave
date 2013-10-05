package com.tas.icecave.general;

import com.tas.icecaveGeneral.utils.Point;

public interface IFunction<return_type> {
	/**
	 * Invoke the collision function.
	 * @param collisionPoint - Point of the tile the player collisioned with.
	 * @return result.
	 */
	public return_type invoke(Point collisionPoint);
}
