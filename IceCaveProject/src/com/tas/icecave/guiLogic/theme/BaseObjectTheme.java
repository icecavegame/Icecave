package com.tas.icecave.guiLogic.theme;

import java.util.ArrayList;

import com.tas.icecaveGeneral.utils.Point;

/**
 * A basic implementation of the IObjectTheme.
 * @author Tom
 *
 */
public abstract class BaseObjectTheme implements IObjectTheme
{
	/**
	 * The tile locations.
	 */
	protected ArrayList<Point> mTileLocations;
	
	@Override
	public ArrayList<Point> getTilesPositions()
	{
		// Return a random tile location
		return mTileLocations;
	}
}
