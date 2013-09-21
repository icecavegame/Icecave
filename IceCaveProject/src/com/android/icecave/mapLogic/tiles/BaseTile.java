package com.android.icecave.mapLogic.tiles;

import com.android.icecave.mapLogic.collision.ICollisionable;

import android.graphics.Point;

/**
 * Base tile implementation.
 * @author Tom
 *
 */
public abstract class BaseTile implements ITile, ICollisionable
{
	@Override
	public Point getLocation()
	{
		return mLocation;
	}
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param location - Location to place the tile in.
	 */
	public BaseTile(Point location) {
		mLocation = new Point(location);
	}
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param x - x location.
	 * @param y - y location.
	 */
	public BaseTile(int x, int y) {
		mLocation = new Point(x,y);
	}
	
	/**
	 * Location of the tile.
	 */
	protected Point mLocation;
}
