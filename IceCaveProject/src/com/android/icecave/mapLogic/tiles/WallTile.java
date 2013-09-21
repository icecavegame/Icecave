package com.android.icecave.mapLogic.tiles;

import android.graphics.Point;

public class WallTile extends BaseTile implements IBlockingTile
{
	/**
	 * Create a new instance of the WallTile object.
	 * @param location - Location to place the tile in.
	 */
	public WallTile(Point location) {
		super(location);
	}
	
	/**
	 * Create a new instance of the WallTile object.
	 * @param x - x location.
	 * @param y - y location.
	 */	
	public WallTile(int x, int y) {
		super(x,y);
	}
}
