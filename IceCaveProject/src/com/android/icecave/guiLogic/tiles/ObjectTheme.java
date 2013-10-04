package com.android.icecave.guiLogic.tiles;

import com.android.icecave.general.GeneralServiceProvider;

import com.android.icecave.utils.Point;

public abstract class ObjectTheme implements IObjectTheme
{
	protected Point[] mTileLocations;
	
	@Override
	public Point getRandomTilePosition()
	{
		// Return a random tile location
		return mTileLocations[GeneralServiceProvider.getInstance().getRandom().nextInt(mTileLocations.length - 1)];
	}
}
