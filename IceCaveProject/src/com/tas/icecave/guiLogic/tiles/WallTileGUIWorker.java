package com.tas.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecave.guiLogic.theme.WallTheme;
import com.tas.icecaveLibrary.mapLogic.tiles.WallTile;
import com.tas.icecaveLibrary.utils.Point;

public class WallTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getRandomTileImage(ThemeMap themeMap)
	{
		// Get the boulder theme.
		WallTheme theme = 
				(WallTheme) themeMap.getTheme(WallTile.class);
		
		// Get the tiles positions.
		ArrayList<Point> tilePositions = theme.getTilesPositions();
		
		// Peek the first.
		return tilePositions.get(0);
	}
}
