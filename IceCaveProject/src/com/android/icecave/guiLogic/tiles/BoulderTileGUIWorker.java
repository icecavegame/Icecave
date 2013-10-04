package com.android.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.android.icecave.general.GeneralServiceProvider;
import com.android.icecave.guiLogic.theme.BoulderTheme;
import com.android.icecave.guiLogic.theme.ThemeMap;
import com.android.icecave.mapLogic.tiles.BoulderTile;
import com.android.icecave.utils.Point;

public class BoulderTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getTilePointInSprite(ThemeMap map)
	{
		// Get the boulder theme.
		BoulderTheme theme = 
				(BoulderTheme) map.getTheme(BoulderTile.class);
		
		// Get the tiles positions.
		ArrayList<Point> tilePositions = theme.getTilesPositions();
		
		// Get a random tile index.
		int tileIndex = 
				GeneralServiceProvider.getInstance()
				.getRandom()
				.nextInt(tilePositions.size());
		
		return tilePositions.get(tileIndex);
	}
}
