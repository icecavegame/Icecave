package com.tas.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.tas.icecave.guiLogic.theme.BoulderTheme;
import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecaveLibrary.general.GeneralServiceProvider;
import com.tas.icecaveLibrary.mapLogic.tiles.BoulderTile;
import com.tas.icecaveLibrary.utils.Point;

public class BoulderTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getRandomTileImage(ThemeMap themeMap)
	{
		// Get the boulder theme.
		BoulderTheme theme = 
				(BoulderTheme) themeMap.getTheme(BoulderTile.class);
		
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
