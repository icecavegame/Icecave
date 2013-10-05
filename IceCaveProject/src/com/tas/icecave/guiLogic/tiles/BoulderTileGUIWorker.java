package com.tas.icecave.guiLogic.tiles;

import java.util.ArrayList;

import com.tas.icecave.general.GeneralServiceProvider;
import com.tas.icecave.guiLogic.theme.BoulderTheme;
import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecaveGeneral.mapLogic.tiles.BoulderTile;
import com.tas.icecaveGeneral.utils.Point;

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
