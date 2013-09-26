package com.android.icecave.mapLogic.tiles;

import com.android.icecave.mapLogic.collision.ICollisionable;

import android.graphics.Point;

public abstract class BaseTile implements ITile
{
	Point mLocation;
	
	/**
	 * Get the tile location.
	 * 
	 * @return Location of the tile.
	 */
	@Override
	public Point getLocation(){
		return mLocation;
	}
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param location - Location of the tile.
	 */
	public BaseTile(Point location){
		mLocation = new Point(location);
	}
	
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param x - X location of the tile.
	 * @param y - Y location of the tile.
	 */
	public BaseTile(int x, int y){
		mLocation = new Point(x, y);
	}
}
