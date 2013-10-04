package com.android.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.android.icecave.guiLogic.theme.EmptyTheme;
import com.android.icecave.guiLogic.theme.ThemeMap;
import com.android.icecave.mapLogic.tiles.EmptyTile;
import com.android.icecave.utils.Point;

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
