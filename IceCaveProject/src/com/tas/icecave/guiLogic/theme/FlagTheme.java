package com.tas.icecave.guiLogic.theme;

import java.util.ArrayList;

import com.tas.icecaveGeneral.utils.Point;

public class FlagTheme extends BaseObjectTheme
{
	public FlagTheme() {
		// Define flag tile positions
		final Point FLAG_1 = new Point(1, 0);
		
		mTileLocations = new ArrayList<Point>();
		mTileLocations.add(FLAG_1);
	}
}
