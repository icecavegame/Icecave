package com.android.icecave.guiLogic.tiles;

import com.android.icecave.general.GeneralServiceProvider;
import com.android.icecave.guiLogic.theme.ThemeMap;
import com.android.icecave.utils.Point;

public class EmptyTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getTilePointInSprite(ThemeMap map)
	{
		return map.getEmptyTheme().getTilesPositions()[GeneralServiceProvider.getInstance()
				.getRandom()
				.nextInt(map.getEmptyTheme().getTilesPositions().length)];
	}
}
