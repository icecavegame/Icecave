package com.tas.icecave.guiLogic.theme;

import java.util.ArrayList;
import java.util.Collections;

import com.tas.icecaveGeneral.utils.Point;

/**
 * Theme for and empty tile.
 * @author Tom
 *
 */
public class EmptyTheme extends BaseObjectTheme implements IShuffelableTheme
{
	/**
	 * Create a new instance of the EmptyTheme object.
	 */
	public EmptyTheme() {
		/**
		 * Empty tile position.
		 */
		final Point EMPTY_1 = new Point(0, 0);
		
		/**
		 * Empty tile position.
		 */
		final Point EMPTY_2 = new Point(0, 1);
		
		// Define empty tile positions
				
		mTileLocations = new ArrayList<Point>();
		mTileLocations.add(EMPTY_1);
		mTileLocations.add(EMPTY_2);
		
		shuffle();
	}

	@Override
	public void shuffle()
	{
		Collections.shuffle(mTileLocations);
	}
}
