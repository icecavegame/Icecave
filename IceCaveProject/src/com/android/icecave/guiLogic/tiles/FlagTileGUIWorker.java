package com.android.icecave.guiLogic.tiles;

import com.android.icecave.general.GeneralServiceProvider;
import com.android.icecave.guiLogic.theme.ThemeMap;
import com.android.icecave.utils.Point;

public class FlagTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getTilePointInSprite(ThemeMap map)
	{
		return map.getFlagTheme().getTilesPositions()[GeneralServiceProvider.getInstance()
				.getRandom()
				.nextInt(map.getFlagTheme().getTilesPositions().length)];
	}
}
