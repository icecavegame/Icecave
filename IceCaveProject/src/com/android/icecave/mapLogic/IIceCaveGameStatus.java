package com.android.icecave.mapLogic;

import com.android.icecave.utils.Point;

/**
 * Interface for reporting on the current IceCaveGameStatus.
 * @author Tom
 *
 */
public interface IIceCaveGameStatus
{
	/**
	 * Get the current player point.
	 * @return Get the player point.
	 */
	Point getPlayerPoint();
	
	/**
	 * Get indication to whether or not this game has ended. 
	 * @return true if the game has ended.
	 */
	boolean getIsStageEnded();
	
}
