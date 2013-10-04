package com.android.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.android.icecave.guiLogic.theme.ThemeMap;
import com.android.icecave.guiLogic.theme.WallTheme;
import com.android.icecave.mapLogic.tiles.WallTile;
import com.android.icecave.utils.Point;

public class WallTileGUIWorker extends BaseTileGUIWorker
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
