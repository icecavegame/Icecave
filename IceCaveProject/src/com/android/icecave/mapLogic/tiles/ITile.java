package com.android.icecave.mapLogic.tiles;

import android.graphics.Point;

/**
 * Basic tile interface.
 * @author Tom
 *
 */
public interface ITile
{
	/**
	 * Get the location of the current tile on the board.
	 * @return Location of the tile on the board.
	 */
	public Point getLocation();
}
