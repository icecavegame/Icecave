package com.tas.icecave.guiLogic.theme;

import com.tas.icecaveLibrary.utils.Point;
import java.util.ArrayList;

/**
 * Theme for the boulder tile.
 * @author Sagie
 *
 */
public class BreakableTheme extends BaseObjectTheme
{
	/**
	 * Create a new instance of the boulder theme.
	 */
	public BreakableTheme() {
		// Define breakable tile positions
		final Point BREAKABLE_1 = new Point(2, 1);
		
		mTileLocations = new ArrayList<Point>();
		mTileLocations.add(BREAKABLE_1);
	}
}
