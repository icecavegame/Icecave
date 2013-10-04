package com.android.icecave.guiLogic.tiles;

import com.android.icecave.utils.Point;

public class EmptyTheme extends ObjectTheme
{
	public EmptyTheme() {
		// Define empty tile positions
		final Point EMPTY_1 = new Point(0, 0);
		final Point EMPTY_2 = new Point(0, 1);
		
		mTileLocations = new Point[] {EMPTY_1, EMPTY_2};
	}
}
