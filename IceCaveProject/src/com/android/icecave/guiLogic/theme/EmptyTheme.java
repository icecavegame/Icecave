package com.android.icecave.guiLogic.theme;

import com.android.icecave.utils.Point;

/**
 * Theme for and empty tile.
 * @author Tom
 *
 */
public class EmptyTheme extends BaseObjectTheme
{
	/**
	 * Create a new instance of the EmptyTheme object.
	 */
	public EmptyTheme() {
		// Define empty tile positions
		final Point EMPTY_1 = new Point(0, 0);
		final Point EMPTY_2 = new Point(0, 1);
		
		mTileLocations = new Point[] {EMPTY_1, EMPTY_2};
	}
}
