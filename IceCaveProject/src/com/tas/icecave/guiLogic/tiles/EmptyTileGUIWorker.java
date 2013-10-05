package com.tas.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.tas.icecave.guiLogic.theme.EmptyTheme;
import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecaveLibrary.mapLogic.tiles.EmptyTile;
import com.tas.icecaveLibrary.utils.Point;

public class EmptyTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getTilePointInSprite(ThemeMap map)
	{
		// Get the boulder theme.
		EmptyTheme theme = 
				(EmptyTheme) map.getTheme(EmptyTile.class);
		
		// Get the tiles positions.
		ArrayList<Point> tilePositions = theme.getTilesPositions();
		
		// Peek the first.
		return tilePositions.get(0);
	}
}
