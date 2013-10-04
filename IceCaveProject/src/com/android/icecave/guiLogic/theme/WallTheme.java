package com.android.icecave.guiLogic.theme;

import java.util.ArrayList;
import java.util.Collections;

import com.android.icecave.utils.Point;

/**
 * The picture theme for a wall tile.
 * @author Tom
 *
 */
public class WallTheme extends BaseObjectTheme implements IShuffelableTheme
{
	/**
	 * Create a new instance of the WallTheme object.
	 */
	public WallTheme() {
		// Define wall tile positions
		final Point WALL_1 = new Point(3, 0);
		final Point WALL_2 = new Point(4, 0);
		final Point WALL_3 = new Point(5, 0);
		final Point WALL_4 = new Point(6, 0);
		
		mTileLocations = new ArrayList<Point>();
		mTileLocations.add(WALL_1);
		mTileLocations.add(WALL_2);
		mTileLocations.add(WALL_3);
		mTileLocations.add(WALL_4);
		
		shuffle();
	}

	@Override
	public void shuffle()
	{
		Collections.shuffle(mTileLocations);
	}
}
