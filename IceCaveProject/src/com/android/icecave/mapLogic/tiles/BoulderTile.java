package com.android.icecave.mapLogic.tiles;

import android.graphics.Point;

public class BoulderTile extends BaseTile implements IBlockingTile
{
	/**
	 * Create a new instance of the BoulderTile object.
	 * @param location - Location to place the tile in.
	 */
	public BoulderTile(Point location) {
		super(location);
	}
	
	/**
	 * Create a new instance of the BoulderTile object.
	 * @param x - x location.
	 * @param y - y location.
	 */	
	public BoulderTile(int x, int y) {
		super(x,y);
	}
}
