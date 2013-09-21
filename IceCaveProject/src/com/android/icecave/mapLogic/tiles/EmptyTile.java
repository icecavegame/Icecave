package com.android.icecave.mapLogic.tiles;

import android.graphics.Point;

public class EmptyTile extends BaseTile
{
	/**
	 * Create a new instance of the EmptyTile object.
	 * @param location - Location to place the tile in.
	 */
	public EmptyTile(Point location) {
		super(location);
	}
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param x - x location.
	 * @param y - y location.
	 */	
	public EmptyTile(int x, int y) {
		super(x,y);
	}
}
