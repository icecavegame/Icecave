package com.tas.icecave.guiLogic.theme;

import java.util.ArrayList;

import com.tas.icecaveLibrary.utils.Point;

/**
 * Interface to wrap an object theme.
 * @author Tom
 *
 */
public interface IObjectTheme
{
	/**
	 * Get the positions of the object pictures in the tile set.
	 * @return Positions in tile set.
	 */
	public ArrayList<Point> getTilesPositions();
}
