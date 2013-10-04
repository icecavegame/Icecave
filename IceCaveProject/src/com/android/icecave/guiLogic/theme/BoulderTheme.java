package com.android.icecave.guiLogic.theme;

import com.android.icecave.utils.Point;

/**
 * Theme for the boulder tile.
 * @author Tom
 *
 */
public class BoulderTheme extends BaseObjectTheme
{
	/**
	 * Create a new instance of the boulder theme.
	 */
	public BoulderTheme() {
		// Define boulder tile positions
		final Point BOULDER_1 = new Point(2, 0);
		final Point BOULDER_2 = new Point(1, 1);
		
		mTileLocations = new Point[] {BOULDER_1, BOULDER_2};
	}
}
