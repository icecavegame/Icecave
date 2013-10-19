package com.tas.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecave.guiLogic.theme.WallTheme;
import com.tas.icecaveLibrary.mapLogic.tiles.WallTile;
import com.tas.icecaveLibrary.utils.Point;

public class BreakableBoulderTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getTilePointInSprite(ThemeMap map)
	{
		// Get the boulder theme.
		WallTheme theme = 
				(WallTheme) map.getTheme(WallTile.class);
		
		// Get the tiles positions.
		ArrayList<Point> tilePositions = theme.getTilesPositions();
		
		// Peek the first.
		return tilePositions.get(0);
	}
}
