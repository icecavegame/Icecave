package com.android.icecave.guiLogic.theme;

import com.android.icecave.utils.Point;

public class BoulderTheme extends BaseObjectTheme
{
	public BoulderTheme() {
		// Define boulder tile positions
		final Point BOULDER_1 = new Point(2, 0);
		final Point BOULDER_2 = new Point(1, 1);
		
		mTileLocations = new Point[] {BOULDER_1, BOULDER_2};
	}
}
