package com.android.icecave.mapLogic.tiles;

import android.graphics.Point;

import com.android.icecave.mapLogic.collision.ICollisionable;


/**
 * Basic tile interface.
 * @author Tom
 *
 */
public interface ITile extends ICollisionable
{
	/**
	 * Get the location of the tile.
	 * @return Location of the tile.
	 */
	Point getLocation();
}
