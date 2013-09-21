package com.android.icecave.mapLogic.tiles;

import android.graphics.Point;

public class FlagTile extends BaseTile
{
	/**
	 * Create a new instance of the FlagTile object.
	 * @param location - Location to place the tile in.
	 */
	public FlagTile(Point location) {
		super(location);
	}
	
	/**
	 * Create a new instance of the FlagTile object.
	 * @param x - x location.
	 * @param y - y location.
	 */	
	public FlagTile(int x, int y) {
		super(x,y);
	}
}
