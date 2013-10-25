package com.tas.icecave.guiLogic.tiles;

import com.tas.icecave.guiLogic.theme.BreakableTheme;
import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecaveLibrary.general.GeneralServiceProvider;
import com.tas.icecaveLibrary.mapLogic.tiles.BreakableBoulderTile;
import com.tas.icecaveLibrary.utils.Point;
import java.util.ArrayList;

public class BreakableBoulderTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getRandomTileImage(ThemeMap themeMap)
	{
		// Get the boulder theme.
		BreakableTheme theme = 
				(BreakableTheme) themeMap.getTheme(BreakableBoulderTile.class);
		
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
